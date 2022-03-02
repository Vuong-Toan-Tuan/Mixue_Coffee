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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Admin
 */
public class Cart {
    private ObjectProperty<Integer> cartID;
    private ObjectProperty<Integer> memberID;
    private ObjectProperty<Integer> productID;
    private ObjectProperty<Integer> quantity;
    private StringProperty nameproduct;
    private StringProperty image;
    public ObjectProperty<ImageView> cover;
    private ObjectProperty<Integer> price;
    private Button delete;
    private Button update;
    
    public Cart(){
        cartID = new SimpleObjectProperty<>(null);
        memberID = new SimpleObjectProperty<>(null);
        productID = new SimpleObjectProperty<>(null);
        quantity = new SimpleObjectProperty<>(null);
        nameproduct = new SimpleStringProperty();
        image = new SimpleStringProperty();
        cover = new SimpleObjectProperty<>();
        price = new SimpleObjectProperty<>(null);
        update = new Button("Update");
        delete = new Button("Delete");
        
//        delete.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Please select a member");
//            alert.setHeaderText("A member must be selected for the operation");
//            alert.showAndWait();
//            
//        }
//        });
    }
    
    public Integer getCartID(){
        return cartID.get();
    }
    public Integer getMemberID(){
        return memberID.get();
    }
    public Integer getProductID(){
        return productID.get();
    }
    public Integer getQuantity(){
        return quantity.get();
    }
    public String getNameProduct(){
        return nameproduct.get();
    }
    public String getImage() {
        return image.get();
    }
    public ImageView getCover() {
        return cover.get();
    }
    public Integer getPrice(){
        return price.get();
    }
    public Button getDelete(){
        return delete;
    }
    public Button getUpdate(){
        return update;
    }
    
    public void setCartID(int cartID){
        this.cartID.set(cartID);
    }
    public void setMemberID(int memberID){
        this.memberID.set(memberID);
    }
    public void setProductID(int productID){
        this.productID.set(productID);
    }
    public void setQuantity(int quantity){
        this.quantity.set(quantity);
    }
    public void setNameProduct(String namePro){
        this.nameproduct.set(namePro);
    }
    public void setImage(String img) {
        this.image.set(img);
    }
    public void setCover(ImageView value) {
        this.cover.set(value);
    }
    public void setPrice(int price){
        this.price.set(price);
    }
    public void setDelete(Button delete){
        this.delete = delete;
    }
    public void setUpdate(Button update){
        this.update = update;
    }
    
    public ObjectProperty<Integer> getCartIDProperty(){
        return this.cartID;
    }
    public ObjectProperty<Integer> getMemberIDProperty(){
        return this.memberID;
    }
    public ObjectProperty<Integer> getProductIDProperty(){
        return this.productID;
    }
    public ObjectProperty<Integer> getQuantityProperty(){
        return this.quantity;
    }
    public StringProperty getNameProductProperty(){
        return this.nameproduct;
    }
    public StringProperty getImageProperty() {
        return this.image;
    }
    public ObjectProperty<ImageView> getCoverProperty() {
        return this.cover;
    }
    public ObjectProperty<Integer> getPriceProperty(){
        return this.price;
    }
    public Button getDeleteProperty(){
        return this.delete;
    }
    public Button getUpdateProperty(){
        return this.update;
    }
    
    public static ObservableList<Cart> selectAllByMemberID(int memID) {
        ObservableList<Cart> carts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM cart WHERE memberID = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
                stmt.setInt(1, memID);
                ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cart p = new Cart();
                p.setCartID(rs.getInt("cartID"));
                p.setMemberID(rs.getInt("memberID"));
                p.setProductID(rs.getInt("productID"));
                p.setQuantity(rs.getInt("quantity"));

//                ImageView imgv = new ImageView(new Image("eProject2/image/" + rs.getString("img")));
//                imgv.setFitWidth(170);
//                imgv.setFitHeight(185);
//                p.setCover(imgv);
                
                carts.add(p);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return carts;
    }
    
    public static boolean deleteCartByProID(int id){
        String sql = "DELETE FROM cart WHERE productID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setInt(1, id);
                        
            int rowDeleted = stmt.executeUpdate();
            
            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("Không có sản phẩm nào bị xóa");
                return false;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    public static boolean delete(Cart deleteCart){
        String sql = "DELETE FROM cart WHERE cartID = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, deleteCart.getCartID());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No cart deleted");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    public static Cart insert(Cart newCart) throws SQLException  {
        String sql = "INSERT into cart (memberID, productID, quantity) "
                + "VALUES (?, ?, ?)";
        ResultSet key = null;
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = 
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            
            stmt.setInt(1, newCart.getMemberID());
            stmt.setInt(2, newCart.getProductID());
            stmt.setInt(3, newCart.getQuantity());
            
            int rowInserted = stmt.executeUpdate();
            
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newCart.setCartID(newKey);
                return newCart;
            } else {
                System.err.println("No cart inserted");
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
    
    public static boolean update(int quantity, int cartID) {
        String sql = "UPDATE cart SET " +
                "quantity = ? " +
                "WHERE cartID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartID);
                        
            int rowUpdated = stmt.executeUpdate();
            
            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("Không có sản phẩm nào được cập nhật");
                return false;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
}
