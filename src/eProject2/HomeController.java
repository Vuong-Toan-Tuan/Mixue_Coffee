/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.io.IOException;
import javafx.geometry.Insets;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeController implements Initializable {

    private String mem = null;
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private FlowPane homeFlowPane;


    @FXML
    private Button btnCart;
    
    @FXML
    private Label lbMember;

    @FXML
    private Hyperlink btnLogout;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;
    
//    @FXML
//    private Label btnHome;

    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    @FXML
    void btnSearchClick(ActionEvent event) throws IOException {
        String pName = txtSearch.getText();
        Navigator.getInstance().goToSearch_Product(pName, mem);
    }

    @FXML
    void btnCartClick(MouseEvent event) throws IOException {
        Navigator.getInstance().goToCART(mem);
    }


    @FXML
    void btnHomeClick(MouseEvent event) {
        
    }
    
    public void ShowaAllProduct() {

        homeFlowPane.getStyleClass().add("homeFlowPane");

//        Pane titlePane = new Pane();
//        Label title = new Label();
//        Label lbMember = new Label();
//        Hyperlink logout = new Hyperlink();
//        logout.setText("Đăng xuất");
//
//        logout.setOnAction((ActionEvent event) -> {
//            try {
//                Navigator.getInstance().goToLogin();
//            } catch (IOException ex) {
////                        Logger.getLogger(FXML1Controller.class.getName()).log(Level.SEVERE, null);
//                System.out.println(ex);
//            }
//        });
//        logout.getStyleClass().add("logout");
//        titlePane.setPrefSize(homeFlowPane.getPrefWidth(), 100);
//        title.setTextFill(javafx.scene.paint.Color.TOMATO);
//        title.getStyleClass().add("title");
//        title.setText("Mixue Coffee");
//        lbMember.getStyleClass().add("lbmember");
////        lbMember.setStyle("-fx-font-weight: bold;");
//        lbMember.setText("Phiên đăng nhập: " + mem);
////        title.setStyle("-fx-font-weight: bold;");
//        
//        Label lbsearch = new Label();
//        lbsearch.setText("Search");
//        lbsearch.getStyleClass().add("lbsearch");
//        TextField search = new TextField();
//        search.setPrefSize(450, 35);
//        search.getStyleClass().add("search");
//        search.setPromptText("search...");
//        Button btnsearch = new Button();
//        btnsearch.setText("OK");
//        btnsearch.getStyleClass().add("btnsearch");
//        btnsearch.setOnAction((ActionEvent event) -> {
//            try {
//                String pName = search.getText();
//                System.out.println("User search: "+pName);
//                Navigator.getInstance().goToSearch_Product(pName, mem);
////                abc(search.getText());
//            } catch (IOException ex) {
//                System.out.println(ex);
//            }
//        });
//        
////        Button btnCart = new Button();
////        btnCart.setText("Cart");
////        btnCart.getStyleClass().add("btnCart");
////        btnCart.setOnAction((ActionEvent event) -> {
////            try {
//////                String pName = search.getText();
//////                System.out.println(pName);
////                Navigator.getInstance().goToCART(mem);
//////                abc(search.getText());
////            } catch (IOException ex) {
////                System.out.println(ex);
////            }
////        });
//        
////        Pane navbarHome = new Pane();
////        navbarHome.setPrefSize(100, homeFlowPane.getPrefHeight()-21);
////        navbarHome.getStyleClass().add("navbarHome");
////        navbarHome.getChildren().add(btnCart);
//        
//        titlePane.getChildren().add(title);
//        titlePane.getChildren().add(lbMember);
//        titlePane.getChildren().add(logout);
//        titlePane.getChildren().add(lbsearch);
//        titlePane.getChildren().add(search);
//        titlePane.getChildren().add(btnsearch);
////        titlePane.getChildren().add(btnCart);
//        anchorpane.getChildren().add(titlePane);
//        anchorpane.getChildren().add(navbarHome);

        ObservableList<Product> productlist = Product.selectAll();
//        FlowPane productFP = new FlowPane();
        for (int i = 0; i < productlist.size(); i++) {
            Pane productArea = new Pane();
//            productArea.setPrefSize(200, 180);
            Label name = new Label();
            ImageView iv = new ImageView();
            Button button = new Button();
            productArea.getStyleClass().add("pane");
            name.getStyleClass().add("nameproduct");
            button.setText("Details");
            int productID = productlist.get(i).getPId();

            button.setOnAction((ActionEvent event) -> {
                try {
                    Navigator.getInstance().goToHOME_DETAILS_PRODUCT(productID, mem);
                } catch (IOException ex) {
//                        Logger.getLogger(FXML1Controller.class.getName()).log(Level.SEVERE, null);
                    System.out.println(ex);
                }
            });
            iv.setFitWidth(150);
            iv.setFitHeight(200);
            iv.setLayoutX(20);
            iv.setLayoutY(15);
            name.setLayoutX(21);
            name.setLayoutY(192);
            button.setLayoutX(22);
            button.setLayoutY(220);

            name.setText(productlist.get(i).getPName());
            iv = productlist.get(i).getCover();

            productArea.getChildren().add(name); 
            productArea.getChildren().add(iv);
            productArea.getChildren().add(button);

            homeFlowPane.getChildren().add(productArea);
            FlowPane.setMargin(productArea, new Insets(25));
        }
//        homeFlowPane.getChildren().add(productFP);

    }

    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    }

    public void initialize(String member) {
        this.mem = member;
        lbMember.setText(member);
        ShowaAllProduct();
    }

}
