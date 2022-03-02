/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeRoleController implements Initializable {

    @FXML
    private Label lbMember;
    
    @FXML
    private Button btnOrder;
       
    @FXML
    private Hyperlink btnLogout;

    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    @FXML
    void btnOrderClick(ActionEvent event) throws IOException {
//        Navigator.getInstance().goToOrder();
    }

    @FXML
    void btnMemberClick(ActionEvent event) {

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
