/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Admin
 */
public class SearchController implements Initializable {
    private String mem = null;
    private String productname1 = null;
    private int dem = 0;
    @FXML
    private AnchorPane anchorpane2;

    @FXML
    private FlowPane homeFlowPane2;
    
    @FXML
    private Pane titlePane;
    
//    @FXML
//    private Label btnHome;
//    
//    @FXML
//    private Label lbMember;
//    
//    @FXML
//    private Hyperlink btnLogout;
//
//    @FXML
//    void btnHomeClick(MouseEvent event) throws IOException {
//        Navigator.getInstance().goToHome_Customer(mem);
//    }
//    @FXML
//    void btnBackClick(ActionEvent event) throws IOException {
//        Navigator.getInstance().goToHome_Customer(mem);
//    }
//    @FXML
//    void btnLogoutClick(ActionEvent event) throws IOException {
//        Navigator.getInstance().goToLogin();
//    }
    
    @FXML
    private Label lbMember;


    @FXML
    private Button btnBack;
    
        @FXML
    private Hyperlink btnLogout2;
        
        
    @FXML
    void btnLogout2Click(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome_Customer(mem);
    }


    @FXML
    void btnABCClick(MouseEvent event) throws IOException {
        Navigator.getInstance().goToHome_Customer(mem);
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
    
    
    public void ShowSearch(String pName){
        homeFlowPane2.getStyleClass().add("homeFlowPane");

//        Pane titlePane = new Pane();
        Label title = new Label();
//        Label lbMember = new Label();
//        Hyperlink logout = new Hyperlink();
//        logout.setText("logout");
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
//        titlePane.setPrefSize(homeFlowPane2.getPrefWidth(), 100);
        title.setTextFill(javafx.scene.paint.Color.TOMATO);
        title.getStyleClass().add("title2");
        
//        lbMember.getStyleClass().add("lbmember");
//        lbMember.setText("Phiên đăng nhập: " + mem);
//        Button back = new Button();
//        back.setText("< Back");
//        back.getStyleClass().add("btnback");
//        back.setOnAction((ActionEvent event) -> {
//            try {
//                Navigator.getInstance().goToHome_Customer(mem);
//            } catch (IOException ex) {
////                        Logger.getLogger(FXML1Controller.class.getName()).log(Level.SEVERE, null);
//                System.out.println(ex);
//            }
//        });
        
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
////            try {
//                System.out.println(search.getText());
////                abc(search.getText());
////            } catch (IOException ex) {
////                System.out.println(ex);
////            }
//        });
        
        
        
//        titlePane.getChildren().add(lbMember);
//        titlePane.getChildren().add(logout);
//        titlePane.getChildren().add(back);
//        titlePane.getChildren().add(lbsearch);
//        titlePane.getChildren().add(search);
//        titlePane.getChildren().add(btnsearch);
        

        ObservableList<Product> productlist = SearchProduct(pName);
//        FlowPane productFP = new FlowPane();
        this.dem = productlist.size();
        title.setText(this.dem+" kết quả tìm kiếm theo: \""+productname1+"\"");
        titlePane.getChildren().add(title);
//        anchorpane2.getChildren().add(titlePane);
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

            homeFlowPane2.getChildren().add(productArea);
            FlowPane.setMargin(productArea, new Insets(25));
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    public void initialize(String pName, String member){
        this.mem = member;
        this.productname1 = pName;
        lbMember.setText(member);
        
        ShowSearch(pName);
    }
}
