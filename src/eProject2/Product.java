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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author admin
 */
public class Product {
    private ObjectProperty<Integer> pID;
    private StringProperty pName;
    private ObjectProperty<Integer> ptID;
    private ObjectProperty<Integer> price;
    private StringProperty details;
    private StringProperty image;
    public ObjectProperty<ImageView> cover;

    public Product() {
        pID = new SimpleObjectProperty<>(null);
        pName = new SimpleStringProperty();
        ptID = new SimpleObjectProperty<>(0);
        price = new SimpleObjectProperty<>();
        details = new SimpleStringProperty();
        image = new SimpleStringProperty();
        cover = new SimpleObjectProperty<>();
    }
    
    public ImageView getCover() {
        return cover.get();
    }
    
    public Integer getPId() {
        return pID.get();
    }

    public String getPName() {
        return pName.get();
    }
    
    public Integer getPtId() {
        return ptID.get();
    }
    
    public Integer getPrice() {
        return price.get();
    }
    
    public String getDetails() {
        return details.get();
    }
    
    public String getImage() {
        return image.get();
    }
    
    public void setPId(int Pid) {
        this.pID.set(Pid);
    }

    public void setPName(String Pname) {
        this.pName.set(Pname);
    }
    
    public void setPtId(int PTid) {
        this.ptID.set(PTid);
    }
    
    public void setPrice(int price) {
        this.price.set(price);
    }
    
    public void setDetails(String details) {
        this.details.set(details);
    }
    
    public void setImage(String img) {
        this.image.set(img);
    }
    
    public void setCover(ImageView value) {
        this.cover.set(value);
    }
    
    public ObjectProperty<Integer> getPIdProperty() {
        return this.pID;
    }

    public StringProperty getPNameProperty() {
        return this.pName;
    }
    
    public ObjectProperty<Integer> getPtIdProperty() {
        return this.ptID;
    }
    
    public ObjectProperty<Integer> getPriceProperty() {
        return this.price;
    }
    
    public StringProperty getDetailsProperty() {
        return this.details;
    }
    
    public StringProperty getImageProperty() {
        return this.image;
    }
    
    public ObjectProperty<ImageView> getCoverProperty() {
        return this.cover;
    }
    
    public static ObservableList<Product> SearchProduct(String pName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
//        String sql = "SELECT * FROM product WHERE name LIKE '%em%'";
        try (
//                Connection conn = DbService.getConnection();
//                PreparedStatement stmt = conn.prepareStatement(sql);
//                ) {
//                stmt.setString(1, pName);
//                ResultSet rs = stmt.executeQuery();
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM product WHERE name LIKE '%"+pName+"%'");) {
            while (rs.next()) {
                Product p = new Product();
                p.setPId(rs.getInt("productID"));
                p.setPName(rs.getString("name"));
                p.setPtId(rs.getInt("typeID"));
                p.setPrice(rs.getInt("price"));
                p.setDetails(rs.getString("details"));
                p.setImage(rs.getString("img"));
                
                ImageView imgv = new ImageView(new Image("eProject2/image/" + rs.getString("img")));
                imgv.setFitWidth(160);
                imgv.setFitHeight(185);
                p.setCover(imgv);
                
                products.add(p);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return products;
    }
    
    public static ObservableList<Product> selectAll() {
        ObservableList<Product> products 
            = FXCollections.observableArrayList();
        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM product");) {
            while (rs.next()) {
                Product p = new Product();
                p.setPId(rs.getInt("productID")); //"id" is column name in table ProductType
                p.setPName(rs.getString("name")); //"title" is column name in table ProductType
                p.setPtId(rs.getInt("typeID"));
                p.setPrice(rs.getInt("price"));
                p.setDetails(rs.getString("details"));
                p.setImage(rs.getString("img"));
                
                ImageView imgv = new ImageView(new Image("eProject2/image/" + rs.getString("img")));
                imgv.setFitWidth(170);
                imgv.setFitHeight(185);
                p.setCover(imgv);
                
                products.add(p);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return products;
    }

    public static boolean delete(Product deleteProduct) {
        String sql = "DELETE FROM product WHERE productID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setInt(1, deleteProduct.getPId());
                        
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
    
    public static ObservableList<Product> selectAllProByProTypeID(int id) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product WHERE typeID = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setPId(rs.getInt("productID"));
                p.setPName(rs.getString("name"));
                p.setPtId(rs.getInt("typeID"));
                p.setPrice(rs.getInt("price"));
                p.setDetails(rs.getString("details"));
                p.setImage(rs.getString("img"));
                
                ImageView imgv = new ImageView(new Image("eProject2/image/" + rs.getString("img")));
                imgv.setFitWidth(160);
                imgv.setFitHeight(185);
                p.setCover(imgv);
                
                products.add(p);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return products;
    }
    
    public static boolean deleteOrderDetailsByProID(int id){
        String sql = "DELETE FROM order_details WHERE productID = ?";
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
    
    public static Product insert(Product newProduct) throws SQLException {
        String sql = "INSERT into product (name, typeID, price, details, img) "
                + "VALUES (?, ?, ?, ?, ?)";
        ResultSet key = null;
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = 
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            
            stmt.setString(1, newProduct.getPName());
            stmt.setInt(2, newProduct.getPtId());
            stmt.setInt(3, newProduct.getPrice());
            stmt.setString(4, newProduct.getDetails());
            stmt.setString(5, newProduct.getImage());
            
            int rowInserted = stmt.executeUpdate();
            
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newProduct.setPId(newKey);
                return newProduct;
            } else {
                System.err.println("Không có sản phẩm nào được thêm vào");
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
    
    public static boolean update(Product updateProduct) {
        String sql = "UPDATE product SET " +
                "name = ?, " +
                "typeID = ?, " +
                "price = ?, " +
                "details = ?, " +
                "img = ? " +
                "WHERE productID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setString(1, updateProduct.getPName());
            stmt.setInt(2, updateProduct.getPtId());
            stmt.setInt(3, updateProduct.getPrice());
            stmt.setString(4, updateProduct.getDetails());
            stmt.setString(5, updateProduct.getImage());
            stmt.setInt(6, updateProduct.getPId());
                        
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
