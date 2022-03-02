/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Admin
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lbMsg;

    @FXML
    private ComboBox<String> cbRole;

//    ObservableList<String> roles = FXCollections.observableArrayList("Quản lý", "Nhân viên");
    ObservableList<String> roles = findRole();
    public static ObservableList<String> findRole(){
        ObservableList<String> role = FXCollections.observableArrayList();
        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM role");) {
            while (rs.next()) {
//                
                role.add(rs.getString("rolename"));
//                role.set(rs.getInt("roleID", rs.getString("rolename")));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return role;    
    }
    public Integer findIDbyRoleName(String rolename){
        int a = 0;
        String sql = "SELECT * FROM role WHERE rolename = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setString(1, rolename);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                a = (rs.getInt("roleID"));
            
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return a;
    }
    
//    private void btnLoginClick(java.awt.event.KeyEvent evt){
//        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            logIn();
//        }
//    }
    
    @FXML
    void btnLoginClick(ActionEvent event) {
        logIn();
    }
//    btnLoginClick.addKeyListener(new java.awt.event.KeyAdapter() {
//        public void keyPressed(java.awt.event.KeyEvent evt) {
//            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
//               System.out.print("Your function call or code can go here");
//            }
//        }
//    });  
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbRole.setItems(roles);
        
        cbRole.getSelectionModel().selectFirst();
        
    }
    
    private void logIn(){
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String abc = cbRole.getSelectionModel().getSelectedItem().toString();
        int id = findIDbyRoleName(abc);

        if(("".equals(username))||("".equals(password))){
            lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
            lbMsg.setText("UserName and Password not null");
        }else{
            String member = username;
            String sql = "SELECT username, `password` FROM member WHERE username = ? and `password` = ? and roleID = ?";
            try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setInt(3, id);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                    lbMsg.setText("Tài khoản hoặc mật khẩu không đúng!");
                }else{
                    lbMsg.setText("");                    
                    if(id == 1){
                        Navigator.getInstance().goToHomeManager(member);
                    }
//                    else if(id == 2){
//                        Navigator.getInstance().goToHomeStaff();
//                    }
                    else{
                        Navigator.getInstance().goToHome_Customer(member);
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
    //            logger.error(e);
            }
        }
    }
    
}
