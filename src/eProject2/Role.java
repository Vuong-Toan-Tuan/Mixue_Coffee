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
import javafx.scene.control.Alert;

/**
 *
 * @author Admin
 */
public class Role {
    private ObjectProperty<Integer> id;
    private StringProperty rolename;
    
    public Role(){
        id = new SimpleObjectProperty<>(null);
        rolename = new SimpleStringProperty();
    }
    
    public Integer getId(){
        return id.get();
    }
    public String getRolename(){
        return rolename.get();
    }
    public void setId(int id){
        this.id.set(id);
    }
    public void setRolename(String rolename){
        this.rolename.set(rolename);
    }
    
    public ObjectProperty<Integer> getIdProperty(){
        return this.id;
    }
    public StringProperty getRolenameProperty(){
        return this.rolename;
    }
    
    public static ObservableList<Role> selectAll(){
        ObservableList<Role> roles = FXCollections.observableArrayList();
        
        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM role");) {
//            logger.info("Database connected!");
            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt("roleID"));
                r.setRolename(rs.getString("rolename")); 
                
                roles.add(r);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return roles;
    }
    
    public static boolean delete(Role deleteRole) {
        String sql = "DELETE FROM role WHERE roleID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setInt(1, deleteRole.getId());
                        
            int rowDeleted = stmt.executeUpdate();
            
            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No role deleted");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Không thể xóa role vì có thành viên thuộc role này");
                alert.showAndWait();
                return false;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    public static Role insert(Role newRole) throws SQLException {
        String sql = "INSERT into role (rolename) "
                + "VALUES (?)";
        ResultSet key = null;
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = 
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            
            stmt.setString(1, newRole.getRolename());
            
            int rowInserted = stmt.executeUpdate();
            
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newRole.setId(newKey);
                return newRole;
            } else {
                System.err.println("No role inserted");
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
    
    public static boolean update(Role updateRole) {
        String sql = "UPDATE role SET " 
                + "rolename = ? " 
                + "WHERE roleID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setString(1, updateRole.getRolename());
            stmt.setInt(2, updateRole.getId());
                        
            int rowUpdated = stmt.executeUpdate();
            
            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No role updated");
                return false;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
}

