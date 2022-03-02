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
public class HomeManagerController implements Initializable {

    private String mem = null;
     @FXML
    private Button btnHome;

    @FXML
    private Button btnProductType;

    @FXML
    private Button btnProduct;

    @FXML
    private Button btnOrderDetails;

    @FXML
    private Button btnOrder;

    @FXML
    private Button btnMember;

    @FXML
    private Button btnRole;
    
    @FXML
    private Label lbMember;
    
    @FXML
    private Hyperlink btnLogout;

    @FXML
    void btnHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeManager();
    }

    @FXML
    void btnMemberClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToMember_Index(mem);
    }

    @FXML
    void btnOrderClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrder(this.mem);
    }

    @FXML
    void btnOrderDetailsClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderDetails();
    }

    @FXML
    void btnProductTypeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProduct_Type(mem);
    }

    @FXML
    void btnProductcClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProduct_Index(mem);
    }

    @FXML
    void btnRoleClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToRole(mem);
    }
    
    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        lbMember.setText("a");
    }    
    public void initialize(String member){
        lbMember.setText(member);
        this.mem = member;
    }
    
}
