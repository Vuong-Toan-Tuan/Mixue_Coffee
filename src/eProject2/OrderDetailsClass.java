/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Binh An
 */
public class OrderDetailsClass {
    
    private ObjectProperty<Integer> ID;
    private ObjectProperty<Integer> orderID;
    private ObjectProperty<Integer> productID;    
    private ObjectProperty<Integer> quantity;
    private ObjectProperty<Double> priceProduct;
    private ObjectProperty<Double> priceQuantity;
    
    public OrderDetailsClass(){
        ID = new SimpleObjectProperty<>(null);
        orderID = new SimpleObjectProperty<>(null);
        productID = new SimpleObjectProperty<>(null);
        quantity = new SimpleObjectProperty<>(null);
        priceProduct = new SimpleObjectProperty<>(null);
        priceQuantity = new SimpleObjectProperty<>(null);
    }
    
    public Integer getID(){
        return ID.get();
    }
    
    public Integer getOrderID(){
        return orderID.get();
    }
    
    public Integer getProductID(){
        return productID.get();
    }
    
    public Integer getQuantity(){
        return quantity.get();
    }
    
    public double getPriceProduct(){
        return priceProduct.get();
    }
    
    public double getPriceQuantity(){
        return priceQuantity.get();
    }
    
    public void setID(int ID){
        this.ID.set(ID);
    }
    
    public void setOrderID(int orderID){
       this.orderID.set(orderID);
    }
    
    public void setProductID(int ProducID){
        this.productID.set(ProducID);
    }
    
    public void setQuantity(int quantity){
        this.quantity.set(quantity);
    }
    
    public void setPriceProduct(double PriceProduct){
        this.priceProduct.set(PriceProduct);
    }
    
    public void setPriceQuantity(double PriceQuantity){
        this.priceQuantity.set(PriceQuantity);
    }
    
    public ObjectProperty<Integer> getIDProperty(){
        return this.ID;
    }
    
    public ObjectProperty<Integer> getOrderIDProperty(){
        return this.orderID;
    }
    
    public ObjectProperty<Integer> getProductIDProperty(){
        return this.productID;
    }
    
    public ObjectProperty<Integer> getQuantityProperty(){
        return this.quantity;
    }
    
    public ObjectProperty<Double> getPriceProductProperty(){
        return this.priceProduct;
    }
    
    public ObjectProperty<Double> getPriceQuantityProperty(){
        return this.priceQuantity;
    }
    
    public static ObservableList<OrderDetailsClass> selectByOrderID(int orderID) {
        ObservableList<OrderDetailsClass> ordersDetails = FXCollections.observableArrayList();
        String sql = "SELECT * FROM `order_details` WHERE orderID = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
                stmt.setInt(1, orderID);
                ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderDetailsClass o = new OrderDetailsClass();
                o.setID(rs.getInt("ID"));
                o.setOrderID(rs.getInt("orderID"));
                o.setProductID(rs.getInt("productID"));
                o.setQuantity(rs.getInt("quantity"));
                o.setPriceProduct(rs.getDouble("priceProduct"));
                o.setPriceQuantity(rs.getDouble("priceQuantity"));

                ordersDetails.add(o);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return ordersDetails;
    }
    
    public static String findProductNameByProductID(int productID){
        String a = null;
        String sql = "SELECT * FROM product WHERE productID = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, productID);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                a = rs.getString("name");
                
            }

        } catch (Exception e) {
            System.err.println(e);
        }
        return a;
    }
    
    public static boolean delete(OrderDetailsClass deleteOrderDetails){
        String sql = "DELETE FROM `order_details` WHERE ID = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, deleteOrderDetails.getID());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No order deleted");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    
    public static OrderDetailsClass insert(OrderDetailsClass newOrderDetails) throws SQLException  {
        String sql = "INSERT into `order_details` (orderID, productID, quantity, priceProduct, priceQuantity) "
                + "VALUES (?, ?, ?, ?, ?)";
        ResultSet key = null;
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = 
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            
            stmt.setInt(1, newOrderDetails.getOrderID());
            stmt.setInt(2, newOrderDetails.getProductID());
            stmt.setInt(3, newOrderDetails.getQuantity());
            stmt.setDouble(4, newOrderDetails.getPriceProduct());
             stmt.setDouble(5, newOrderDetails.getPriceQuantity());
            
            int rowInserted = stmt.executeUpdate();
            
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newOrderDetails.setOrderID(newKey);
                return newOrderDetails;
            } else {
                System.err.println("No order details inserted");
                return null;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
            if (key != null) {
                key.close();
            }
        }
    }
    

    
}
