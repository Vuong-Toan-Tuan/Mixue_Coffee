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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Binh An
 */
public class OrderClass {

    private ObjectProperty<Integer> orderID;
    private StringProperty customer_information;
    private StringProperty memberName;
    private StringProperty timeorder;
    private ObjectProperty<Integer> totalprice;

    public OrderClass() {
        orderID = new SimpleObjectProperty<>(null);
        customer_information = new SimpleStringProperty();
        memberName = new SimpleStringProperty();
        timeorder = new SimpleStringProperty();
        totalprice = new SimpleObjectProperty<>(0);
    }

    public Integer getOrderID() {
        return orderID.get();
    }

    public String getCustomerInformation() {
        return customer_information.get();
    }

    public String getMemberName() {
        return memberName.get();
    }

    public String getTimeOrder() {
        return timeorder.get();
    }

    public Integer getTotalPrice() {
        return totalprice.get();
    }

    public void setOrderID(int orderID) {
        this.orderID.set(orderID);
    }

    public void setCustomerInformation(String customer_information) {
        this.customer_information.set(customer_information);
    }

    public void setMemberName(String memberName) {
        this.memberName.set(memberName);
    }

    public void setTimeOrder(String TimeOrder) {
        this.timeorder.set(TimeOrder);
    }

    public void setTotalPrice(int TotalPrice) {
        this.totalprice.set(TotalPrice);
    }

    public ObjectProperty<Integer> getOrderIdProperty() {
        return this.orderID;
    }

    public StringProperty getCustomerInformationProperty() {
        return this.customer_information;
    }

    public StringProperty getMemberNameProperty() {
        return this.memberName;
    }

    public StringProperty getTimeOrderProperty() {
        return this.timeorder;
    }

    public ObjectProperty<Integer> getTotalPriceProperty() {
        return this.totalprice;
    }

    public static ObservableList<OrderClass> selectAll() {
        ObservableList<OrderClass> orders = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `order`");) {

            while (rs.next()) {
                OrderClass o = new OrderClass();
                o.setOrderID(rs.getInt("orderID"));
                o.setCustomerInformation(rs.getString("customer_information"));
                o.setMemberName(rs.getString("memberName"));
                o.setTimeOrder(rs.getString("timeorder"));
                o.setTotalPrice(rs.getInt("totalprice"));

                orders.add(o);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

        return orders;
    }

    public static boolean delete(OrderClass deleteOrder) {
        String sql = "DELETE FROM `order` WHERE orderID = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, deleteOrder.getOrderID());

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
    
    public static boolean deleteOrderDetails(int orderID){
        String sql = "DELETE FROM order_details WHERE orderID = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, orderID);

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No order_details deleted");
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    public static OrderClass insert(OrderClass newOrder) throws SQLException  {
        String sql = "INSERT into `order` (customer_information, memberName, `timeorder`, totalprice) "
                + "VALUES (?, ?, ?, ?)";
        ResultSet key = null;
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = 
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            
            stmt.setString(1, newOrder.getCustomerInformation());
            stmt.setString(2, newOrder.getMemberName());
            stmt.setString(3, newOrder.getTimeOrder());
            stmt.setInt(4, newOrder.getTotalPrice());
            
            int rowInserted = stmt.executeUpdate();
            
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newOrder.setOrderID(newKey);
                return newOrder;
            } else {
                System.err.println("No order inserted");
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
    
    public static boolean update(OrderClass updateOrder) {
        String sql = "UPDATE `order` SET " +
                "customer_information = ? " +
//                "author = ?, " +
//                "page = ? " +
                "WHERE orderID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setString(1, updateOrder.getCustomerInformation());
            stmt.setInt(2, updateOrder.getOrderID());
//            stmt.setString(2, updateBook.getAuthor());
//            stmt.setInt(3, updateBook.getPages());
//            stmt.setInt(4, updateBook.getId());
                        
            int rowUpdated = stmt.executeUpdate();
            
            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No book updated");
                return false;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    

}
