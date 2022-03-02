/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.awt.PageAttributes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class RoleController implements Initializable {
    
    private String mem = null;
    private Role editRole = null;
    
    @FXML
    private TableView<Role> tvRoles;

//    @FXML
//    private TableColumn<Role, Integer> tcID;

    @FXML
    private TableColumn<Role, String> tcRoleName;

    @FXML
    private TextField txtRole;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;
    
    @FXML
    private Label lbMessage;
    
    @FXML
    private Label lbMember;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnReset;

    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }
    
    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeManager(mem);
    }
    
    @FXML
    void btnResetClick(ActionEvent event) {
        resetform();
    }

    @FXML
    void btnDeleteClick(ActionEvent event) {
        Role selectedRole = tvRoles.getSelectionModel().getSelectedItem();
        if (selectedRole == null){
            selectRoleWarning();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you surt you want to delete the selected role?");
            alert.setTitle("Deleting a role");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if(confirmationResponse.get() == ButtonType.OK){
                boolean result = Role.delete(selectedRole); // xóa trong database
                if(result){
                    tvRoles.getItems().remove(selectedRole); //xóa trên UI
                    System.out.println("A role is deleted");
                }else{
                    System.out.println("No role is deleted");
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Không thể xóa role vì có thành viên thuộc role này");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void btnInsertClick(ActionEvent event) throws SQLException {
        if (editRole == null){
            if(txtRole.getText().equals("")){
                lbMessage.setTextFill(javafx.scene.paint.Color.TOMATO);
                lbMessage.setText("Role Name not null");
            }else{
                String rl = txtRole.getText();
                int out = rl.length();
        
                for(int i = 0; i < rl.length(); i++){
                    char character = rl.charAt(i); // This gives the character 'a'
                    int ascii = (int) character; // ascii is now 97.
                    if((ascii < 32) || ((ascii > 32) && (ascii <= 47)) || ((ascii >= 58) && (ascii <= 64)) || ((ascii >= 91) && (ascii <= 96)) || (ascii == 127) || ((ascii >= 123)&&(ascii <=191)) || ((ascii >= 196)&&(ascii <=199)) || (ascii == 203) || (ascii == 206) || (ascii == 207) || (ascii == 209) ||((ascii >= 214)&&(ascii <=216)) || (ascii == 219) || (ascii == 220) || (ascii == 222) || (ascii == 223) ||((ascii >= 228)&&(ascii <=231)) || (ascii == 235) ||((ascii >= 238)&&(ascii <=241)) ||((ascii >= 246)&&(ascii <=248)) || (ascii == 251) || (ascii == 252) || (ascii == 254) || (ascii == 255) )
                    {
                        out--;
                    }

                }
                if(rl.length() == out){
                    Role insertRole = extractRoleFromFields();
                    insertRole = Role.insert(insertRole);

                    allRole();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Successful");
                    alert.setHeaderText("New role inserted with id: "+insertRole.getId());
                    alert.showAndWait();
                }else{
                    lbMessage.setTextFill(javafx.scene.paint.Color.TOMATO);
                    lbMessage.setText("Role Name không chứa ký tự đặc biệt!");
                }
            }
        }else{
            
            if(txtRole.getText().equals("")){
                lbMessage.setTextFill(javafx.scene.paint.Color.TOMATO);
                lbMessage.setText("Role Name not null");
            }else{
                String rl = txtRole.getText();
                int out = rl.length();
        
                for(int i = 0; i < rl.length(); i++){
                    char character = rl.charAt(i); // This gives the character 'a'
                    int ascii = (int) character; // ascii is now 97.
                    if((ascii < 32) || ((ascii > 32) && (ascii <= 47)) || ((ascii >= 58) && (ascii <= 64)) || ((ascii >= 91) && (ascii <= 96)) || (ascii == 127) || ((ascii >= 123)&&(ascii <=191)) || ((ascii >= 196)&&(ascii <=199)) || (ascii == 203) || (ascii == 206) || (ascii == 207) || (ascii == 209) ||((ascii >= 214)&&(ascii <=216)) || (ascii == 219) || (ascii == 220) || (ascii == 222) || (ascii == 223) ||((ascii >= 228)&&(ascii <=231)) || (ascii == 235) ||((ascii >= 238)&&(ascii <=241)) ||((ascii >= 246)&&(ascii <=248)) || (ascii == 251) || (ascii == 252) || (ascii == 254) || (ascii == 255) )
                    {
                        out--;
                    }
                }
                if(rl.length() == out){
                    Role updateRole = extractRoleFromFields();
                    updateRole.setId(this.editRole.getId());
                    boolean result = Role.update(updateRole);
                    if (result) {
                        allRole();
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Successful");
                        alert.setHeaderText("Update role successful with id: "+updateRole.getId());
                        alert.showAndWait();
                        
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Erros");
                        alert.setHeaderText("Erros update Unsuccessfull");
                        alert.showAndWait();
                    }
                }else{
                    lbMessage.setTextFill(javafx.scene.paint.Color.TOMATO);
                    lbMessage.setText("Role Name không chứa ký tự đặc biệt!");
                }
            }
            
            
        }
        
    }

    @FXML
    void btnUpdateClick(ActionEvent event) {
        Role selectedRole = tvRoles.getSelectionModel().getSelectedItem();
        
        if(selectedRole == null){
            selectRoleWarning();
        }else{
            txtRole.setText(selectedRole.getRolename());
            this.editRole = selectedRole;
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allRole();
    }
    
    public void initialize(String member) {
        lbMember.setText(member);
        this.mem = member;
    }
    public void allRole(){
        ObservableList<Role> rolelist = Role.selectAll();
        
        tvRoles.setItems(rolelist);
        
//        tcID.setCellValueFactory((role) -> {
//            return role.getValue().getIdProperty();
//        });
        tcRoleName.setCellValueFactory((role) -> {
            return role.getValue().getRolenameProperty();
        });
    }
    
    private void resetform(){
        txtRole.setText("");
        this.editRole = null;
    }
    
    private void selectRoleWarning(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Please select a role");
        alert.setHeaderText("A role must be selected for the operation");
        alert.showAndWait();
    }
    private Role extractRoleFromFields(){
    
        Role role = new Role();
        role.setRolename(txtRole.getText());
        return role;
    }
}