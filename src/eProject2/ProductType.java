/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.io.IOException;
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
 * @author admin
 */
public class ProductType {
    private ObjectProperty<Integer> ptID;
    private StringProperty ptName;

    public ProductType() {
        ptID = new SimpleObjectProperty<>(null);
        ptName = new SimpleStringProperty();
    }
    
    public Integer getPtId() {
        return ptID.get();
    }

    public String getPtName() {
        return ptName.get();
    }
    
    public void setPtId(int PTid) {
        this.ptID.set(PTid);
    }

    public void setPtName(String PTname) {
        this.ptName.set(PTname);
    }
    
    public ObjectProperty<Integer> getPtIdProperty() {
        return this.ptID;
    } 

    public StringProperty getPtNameProperty() {
        return this.ptName;
    }
    
    public static ObservableList<ProductType> SearchTypeProduct(String ptName) {
        ObservableList<ProductType> producttype = FXCollections.observableArrayList();
//        String sql = "SELECT * FROM product WHERE name LIKE '%em%'";
        try (
//                Connection conn = DbService.getConnection();
//                PreparedStatement stmt = conn.prepareStatement(sql);
//                ) {
//                stmt.setString(1, pName);
//                ResultSet rs = stmt.executeQuery();
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM producttype WHERE typename LIKE '%"+ptName+"%'");) {
            while (rs.next()) {
                ProductType pt = new ProductType();
                pt.setPtId(rs.getInt("typeID"));
                pt.setPtName(rs.getString("typename"));
                
                producttype.add(pt);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return producttype;
    }
    
    public static ObservableList<ProductType> selectAll() {
        ObservableList<ProductType> productTypes 
            = FXCollections.observableArrayList();
        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM producttype");) {
            while (rs.next()) {
                ProductType pt = new ProductType();
                pt.setPtId(rs.getInt("typeID")); //"id" is column name in table ProductType
                pt.setPtName(rs.getString("typename")); //"title" is column name in table ProductType
                
                productTypes.add(pt);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return productTypes;
    }
    
    public static boolean delete(ProductType deleteProductType) {
        String sql = "DELETE FROM producttype WHERE typeID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setInt(1, deleteProductType.getPtId());
                        
            int rowDeleted = stmt.executeUpdate();
            
            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("Không có loại sản phẩm nào bị xóa");
                return false;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    public static ProductType insert(ProductType newProductType) throws SQLException {
        String sql = "INSERT into producttype (typename) "
                + "VALUES (?)";
        ResultSet key = null;
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = 
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            
            stmt.setString(1, newProductType.getPtName());
            
            int rowInserted = stmt.executeUpdate();
            
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newProductType.setPtId(newKey);
                return newProductType;
            } else {
                System.err.println("Không có loại sản phẩm nào được thêm vào");
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
    
    public static boolean update(ProductType updateProductType) {
        String sql = "UPDATE producttype SET " +
                "typename = ? " +
                "WHERE typeID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setString(1, updateProductType.getPtName());
            stmt.setInt(2, updateProductType.getPtId());
            
                        
            int rowUpdated = stmt.executeUpdate();
            
            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("Không có loại sản phẩm nào được cập nhật");
                return false;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
   
}
