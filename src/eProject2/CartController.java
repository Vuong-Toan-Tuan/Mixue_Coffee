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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class CartController implements Initializable {
    
    private String mem = null;
    private int Quantity = 1;
    
    @FXML
    private TableView<Cart> tvCart;

    @FXML
    private TableColumn<Cart, String> tcNameProduct;

    @FXML
    private TableColumn<Cart, ImageView> tcImage;

    @FXML
    private TableColumn<Cart, Integer> tcPrice;

    @FXML
    private TableColumn<Cart, Integer> tcQuantity;
    
    @FXML
    private TableColumn<Cart, Button> tcUpdate;

    @FXML
    private TableColumn<Cart, Button> tcDelete;

    @FXML
    private Button btnBuy;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnTru;

    @FXML
    private Button btnCong;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Label lbMember;

    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    private Label lbSoSP;

    @FXML
    private Label lbSoTien;
    
    @FXML
    private Label btnHome;
    
    @FXML
    private Button btnBack;
    
    @FXML
    void btnBackClick(ActionEvent event) throws IOException{
        Navigator.getInstance().goToHome_Customer(mem);
    }
    
    @FXML
    void btnHomeClick(MouseEvent event) throws IOException{
        Navigator.getInstance().goToHome_Customer(mem);
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
    void btnBuyClick(ActionEvent event) throws SQLException {
                    ObservableList<Member> member = findMember(mem);
                    int memberID = member.get(0).getId();
                    String Fullname = member.get(0).getFullname();
                    String Address = member.get(0).getAddress();
                    String Phone = member.get(0).getPhone();


                    OrderClass insertOrder = new OrderClass();
                    insertOrder.setCustomerInformation(Fullname+"\n"+Address+"\n"+Phone);
                    insertOrder.setMemberName("");
                    insertOrder.setTimeOrder(String.valueOf(java.time.LocalDateTime.now()));
                    insertOrder.setTotalPrice(Integer.parseInt("0"));

                    insertOrder = OrderClass.insert(insertOrder);
                    int orderid = insertOrder.getOrderID();
                    
                    ObservableList<Cart> cartlist = Cart.selectAllByMemberID(memberID);
                    for(int i = 0; i < cartlist.size(); i++){
                        OrderDetailsClass insertOrder2 = new OrderDetailsClass();
                        insertOrder2.setOrderID(orderid);
                        int pID = cartlist.get(i).getProductID();
                        insertOrder2.setProductID(pID);
                        insertOrder2.setQuantity(cartlist.get(i).getQuantity());
                        Product p = findProduct(pID);
                        insertOrder2.setPriceProduct(p.getPrice());
                        insertOrder2.setPriceQuantity(p.getPrice()*cartlist.get(i).getQuantity());
                        
                        insertOrder2 = OrderDetailsClass.insert(insertOrder2);
                    }
                    int a = findPriceQuantity(orderid);
                    boolean result = OrderDetailsController.UpdateTotalPrice(a, orderid);
                    if(result){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Successful");
                        alert.setHeaderText("Buy Successful!\nThe amount you need to pay is "+a+" VND");
                        alert.showAndWait();
                    }
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
    }

    public int findPriceQuantity(int orderID){
        int totalPriceQuantity = 0;
        String sql = "SELECT priceQuantity FROM order_details WHERE orderID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                totalPriceQuantity += (rs.getInt("priceQuantity"));
            
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return totalPriceQuantity;
    }
    
    @FXML
    void btnCongClick(ActionEvent event) {
        this.Quantity += 1;
        txtQuantity.setText(String.valueOf(this.Quantity));
    }

    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    @FXML
    void btnTruClick(ActionEvent event) {
        if(this.Quantity <= 1){
            
        }else{
            this.Quantity -= 1;
            txtQuantity.setText(String.valueOf(this.Quantity));
        }
    }


    @FXML
    void btnUpdateClick(ActionEvent event) {
//        ObservableList<Member> members = findMember(this.mem);
        Cart selectCart = tvCart.getSelectionModel().getSelectedItem();
        int Soluong = Integer.parseInt(txtQuantity.getText());
        int updatecart = selectCart.getCartID();
        boolean result = Cart.update(Soluong, updatecart);
        if(result){
            ShowCart(this.mem);
            this.Quantity = 1;
            txtQuantity.setText("1");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Successful!");
            alert.setHeaderText("Update cart successful!");
            alert.showAndWait();           
        }else{
            System.out.println("Error");
        }       
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
    public void initialize(String member){
        this.mem = member;
        lbMember.setText(member);
        txtQuantity.setText("1");
        
        ShowCart(member);
        
    }
    
    
    
    public static Product findProduct(int productID) {
//        ObservableList<Product> products = FXCollections.observableArrayList();
        Product products = new Product();
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
                imgv.setFitWidth(140);
                imgv.setFitHeight(165);
                p.setCover(imgv);
                
                products = p;
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return products;
    }
    
//    public int UpdateTotalPrice(Cart selectCart){
//        int IDPro = selectCart.getProductID();
//        Product pro = findProduct(IDPro);
//        int price1 = pro.getPrice();
//        int quantity1 = selectCart.getQuantity();
//        int abc = price1 * quantity1;
//        return abc;
//    }
    
    public void ShowCart(String member){
        ObservableList<Member> members = findMember(member);
        int memberID = members.get(0).getId();
        int soSP = 0;
        int totalPrice = 0;
        int a = 0;
        ObservableList<Cart> cartlist = Cart.selectAllByMemberID(memberID);
        ObservableList<Cart> cartlistAll = FXCollections.observableArrayList();
        
        
        for(int i = 0; i < cartlist.size(); i++){
            int pID = cartlist.get(i).getProductID();
            soSP += cartlist.get(i).getQuantity();

            Product p = findProduct(pID);
            cartlist.get(i).setNameProduct(p.getPName());
            cartlist.get(i).setImage(p.getImage());
            cartlist.get(i).setCover(p.getCover());
            cartlist.get(i).setPrice(p.getPrice());
            totalPrice += p.getPrice() * cartlist.get(i).getQuantity();

            cartlistAll.add(cartlist.get(i));
            
//            ---------------
            
            cartlist.get(i).getUpdate().setOnAction((ActionEvent e) -> {
                //                int abc = i;
                Cart selectCart = tvCart.getSelectionModel().getSelectedItem();
                if(selectCart == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Hãy chọn 1 sản phẩm");
                    alert.setHeaderText("Chưa có sản phẩm nào được chọn!");
                    alert.showAndWait();
                }else{
                    txtQuantity.setText(String.valueOf(selectCart.getQuantity()));
                    int Soluong = Integer.parseInt(txtQuantity.getText());
                    this.Quantity = Soluong;
                }
            });
            cartlist.get(i).getUpdate().getStyleClass().add("btnupdate");

            cartlist.get(i).getDelete().setOnAction((ActionEvent e) -> {
                //                int abc = i;
                Cart selectCart = tvCart.getSelectionModel().getSelectedItem();
                if(selectCart == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Hãy chọn 1 sản phẩm");
                    alert.setHeaderText("Chưa có sản phẩm nào được chọn!");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Are you surt you want to delete the selected ?");
                    alert.setTitle("Deleting a ");
                    Optional<ButtonType> confirmationResponse
                            = alert.showAndWait();
                    if(confirmationResponse.get() == ButtonType.OK){
                        
                        boolean result = Cart.delete(selectCart);
                        if(result){
                            tvCart.getItems().remove(selectCart);
                            System.out.println("Sản phẩm trong giỏ hàng đã được xóa");
//                            UpdateTotalPrice();
//                            ObservableList<Cart> cartlistUpdate = Cart.selectAllByMemberID(memberID);
//                            for(int i = 0; i < cartlistUpdate.size(); i++){
//                              int id = cartlistUpdate.get(i).getProductID();
//                            }
                        }else{
                            System.out.println("Lỗi! Sản phẩm trong giỏ hàng không xóa");
                        }
                    }
                }
            });
            cartlist.get(i).getDelete().getStyleClass().add("btndelete");
        }

        tvCart.setItems(cartlistAll);
        tcNameProduct.setCellValueFactory((cart) -> {
            return cart.getValue().getNameProductProperty();
        });
        tcImage.setCellValueFactory((cart) -> {
            return cart.getValue().getCoverProperty();
        });
        tcPrice.setCellValueFactory((cart) -> {
            return cart.getValue().getPriceProperty();
        });
        tcQuantity.setCellValueFactory((cart) -> {
            return cart.getValue().getQuantityProperty();
        });
        tcUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        tcDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        
        lbSoSP.setText("Tổng tiền "+String.valueOf(soSP)+" sản phẩm");
        lbSoTien.setText(String.valueOf(totalPrice));
    }
    
}
