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
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ProductDetailsController implements Initializable {

    @FXML
    private Label dtName;

    @FXML
    private Label dtType;

    @FXML
    private Label dtPrice;

    @FXML
    private Label dtImg;

    @FXML
    private Label dtDetails;

    @FXML
    private Button btnBack;
    


    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
//        Navigator.getInstance().goToProduct_Index();
    }
    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void initialize(Product productDetails){
        dtName.setText(productDetails.getPName());
        int i = productDetails.getPtId();
//        dtType.setText(i);
//        dtPrice.setInt(productDetails.getPrice());
        dtDetails.setText(productDetails.getDetails());
        dtImg.setText(productDetails.getImage());
    }
}
