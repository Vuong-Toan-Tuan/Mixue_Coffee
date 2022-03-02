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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeDetailsProductController implements Initializable {
    private int productID = 0;
    private String mem = null;
    private int Quantity = 1;
    private int Price = 0;
    @FXML
    private Pane homePane;
    
    @FXML
    private ImageView lbImg;

    @FXML
    private Label lbName;

    @FXML
    private Label lbDetails;

    @FXML
    private Label lbPrice;

    @FXML
    private Button btnBack;

    @FXML
    private Label lbMember;

    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    private Button btnAdd;
    
    private Image images;
    
    @FXML
    private TextField txtQuantity;

    @FXML
    private Button btnTru;

    @FXML
    private Button btnCong;
    
    @FXML
    private Label lbMsg;
    
    @FXML
    void btnCongClick(ActionEvent event) {
        this.Quantity += 1;
        txtQuantity.setText(String.valueOf(this.Quantity));
    }
    
    @FXML
    void btnTruClick(ActionEvent event) {
        if(this.Quantity <= 1){
            
        }else{
            this.Quantity -= 1;
            txtQuantity.setText(String.valueOf(this.Quantity));
        }
    }
    
    public static ObservableList<Product> findProduct(int productID) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product WHERE productID = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
                stmt.setInt(1, productID);
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

    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
       Navigator.getInstance().goToHome_Customer(mem);
    }

    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }
    
    public ObservableList<Member> findMember(String member){
        ObservableList<Member> members = FXCollections.observableArrayList();
        String sql = "SELECT * FROM member WHERE username = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
                stmt.setString(1, member);
                ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Member m = new Member();
                m.setId(rs.getInt("memberID"));
                m.setIdCard(rs.getString("IDcard")); 
                m.setUsername(rs.getString("username"));
                m.setFullname(rs.getString("fullname"));
                m.setPassword(rs.getString("password"));
                m.setGender(rs.getString("gender"));
                m.setPhone(rs.getString("phone"));
                m.setEmail(rs.getString("email"));
                m.setAddress(rs.getString("address"));
                m.setRoleId(rs.getInt("roleID"));
                m.setImg(rs.getString("img"));
                m.setShift(rs.getString("shift"));
                members.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return members;
    }
    
    
    
    @FXML
    void btnAddClick(ActionEvent event) throws SQLException {
        if(txtQuantity.getText().equals("")){
            lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
            lbMsg.setText("Số lượng không được để trống!");
        }else{
            if(txtQuantity.getText().equals("0")){
                lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                lbMsg.setText("Số lượng không được bằng 0!");
            }else{
                String Strnhap = txtQuantity.getText();
                String Strgoc = "0123456789";
                String out = "";
                for(int i = 0; i < Strnhap.length(); i++){
                    for(int x = 0; x < Strgoc.length(); x++){
                        if(Strnhap.charAt(i) == Strgoc.charAt(x)){
                            out += Strnhap.charAt(i);
                        }
                    }
                }
                
                if(Strnhap.equals(out)){
                    ObservableList<Member> member = findMember(mem);
                    Cart insertCart = new Cart();
                    insertCart.setMemberID(member.get(0).getId());
                    insertCart.setProductID(this.productID);
                    insertCart.setQuantity(Integer.parseInt(txtQuantity.getText()));

                    insertCart = Cart.insert(insertCart);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Thành công!");
                    alert.setHeaderText("Thêm sản phẩm vào giỏ hàng thành công!");
                    alert.showAndWait();
                    lbMsg.setText("");
                    
//                    ObservableList<Member> member = findMember(mem);
//                    String Fullname = member.get(0).getFullname();
//                    String Address = member.get(0).getAddress();
//                    String Phone = member.get(0).getPhone();
//
//
//                    OrderClass insertOrder = new OrderClass();
//                    insertOrder.setCustomerInformation(Fullname+"\n"+Address+"\n"+Phone);
//                    insertOrder.setMemberName("");
//                    insertOrder.setTimeOrder(String.valueOf(java.time.LocalDateTime.now()));
//                    insertOrder.setTotalPrice(Integer.parseInt("0"));
//
//
//                    insertOrder = OrderClass.insert(insertOrder);
//
//                    int orderid = insertOrder.getOrderID();
//
//                    OrderDetailsClass insertOrder2 = new OrderDetailsClass();
//                    insertOrder2.setOrderID(orderid);
//                    insertOrder2.setProductID(this.productID);
//                    insertOrder2.setQuantity(Integer.parseInt(txtQuantity.getText()));
//                    insertOrder2.setPriceProduct(this.Price);
//                    insertOrder2.setPriceQuantity(this.Price * Integer.parseInt(txtQuantity.getText()));
//
//                    insertOrder2 = OrderDetailsClass.insert(insertOrder2);
//
//                    int a = findPriceQuantity(orderid);
//                    boolean result = OrderDetailsController.UpdateTotalPrice(a, orderid);
//                    if(result){
//                        Alert alert = new Alert(Alert.AlertType.WARNING);
//                        alert.setTitle("Successful");
//                        alert.setHeaderText("Buy Successful!\nThe amount you need to pay is "+a+" VND");
//                        alert.showAndWait();
//                        lbMsg.setText("");
//                    }
                    
                }else{
                    lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                    lbMsg.setText("Số lượng phải là số và không âm!");
                }
            }
            
        }
        
        
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void initialize(int productID, String member){
        this.productID = productID;
        this.mem = member;
        lbMember.setText(member);
        txtQuantity.setText("1");
        ObservableList<Product> productlist = findProduct(productID);
        String name = productlist.get(0).getPName();
        String details = productlist.get(0).getDetails();
        String price = String.valueOf(productlist.get(0).getPrice());
        this.Price = productlist.get(0).getPrice();
//        lbImg.setFitWidth(150);
//        lbImg.setFitHeight(200);

        String url = "file:/C:/Users/Admin/Documents/NetBeansProjects/project2_java/src/eProject2/image/"+productlist.get(0).getImage();
//        System.out.println(url);
        images = new Image(url, lbImg.getFitWidth(), lbImg.getFitHeight(), true, true);
        lbImg.setImage(images);
        
//        lbImg = productlist.get(0).getCover();
        
//        homePane.getChildren().add(lbImg);
        
        lbName.setText(name);
        lbDetails.setText(details);
        lbPrice.setText(price);
    }
}
