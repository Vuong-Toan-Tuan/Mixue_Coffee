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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Binh An
 */
public class OrderDetailsController implements Initializable {
    private String mem = null;
    private int orderID = 0;
    private int Amount = 1;
    @FXML
    private ComboBox<String> cbProduct;

    @FXML
    private TextField txtAmount;

    @FXML
    private Button btnAdd;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnReset;
    
    @FXML
    private TableView<OrderDetailsClass> tvOrderDetails;

    @FXML
    private TableColumn<OrderDetailsClass, Integer> tcOrderID;

    @FXML
    private TableColumn<OrderDetailsClass, Integer> tcProductID;

    @FXML
    private TableColumn<OrderDetailsClass, Integer> tcQuantity;

    @FXML
    private TableColumn<OrderDetailsClass, Double> tcPriceProduct;

    @FXML
    private TableColumn<OrderDetailsClass, Double> tcPriceQuantity;
    
    @FXML
    private Label lbTest;
    
    @FXML
    private Label lbMember;
    
    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    private Label lbMsg;
    
    @FXML
    private Button btnTru;

    @FXML
    private Button btnCong;
    
    @FXML
    void btnCongClick(ActionEvent event) {
        this.Amount += 1;
        txtAmount.setText(String.valueOf(this.Amount));
    }
    
    @FXML
    void btnTruClick(ActionEvent event) {
        if(this.Amount <= 1){
            
        }else{
            this.Amount -= 1;
            txtAmount.setText(String.valueOf(this.Amount));
        }
    }
    
    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrder(this.mem);
    }
    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }
    
    @FXML
    void btnResetClick(ActionEvent event) {
        resetForm();
    }
    
    @FXML
    void btnXoaClick(ActionEvent event) {
        OrderDetailsClass selectedOrder = tvOrderDetails.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) { //no book is selected
//            selectOrderWarning();
        } else { //a ... is selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete the selected order?");
            alert.setTitle("Deleting a order");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.OK) {
                OrderDetailsClass deleteBook = tvOrderDetails.getSelectionModel().getSelectedItem();
                boolean result = OrderDetailsClass.delete(deleteBook);

                if (result) {
                    tvOrderDetails.getItems().remove(deleteBook); //update TableView
                    System.out.println("A order_details is deleted");
                    int a = findPriceQuantity(this.orderID);
                    boolean result1 = OrderDetailsController.UpdateTotalPrice(a, this.orderID);
                    if(result1){           
                    }
                } else {
                    System.err.println("No order_details is deleted");
                }
            }
    }
    }

    ObservableList<String> Products = findProduct();
    public static ObservableList<String> findProduct(){
        ObservableList<String> products = FXCollections.observableArrayList();
        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM product");) {
            while (rs.next()) {
//                
                products.add(rs.getString("name"));
//                role.set(rs.getInt("roleID", rs.getString("rolename")));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return products;    
    }
    public Integer findIDbyProductName(String productname){
        int a = 0;
        String sql = "SELECT * FROM product WHERE name = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setString(1, productname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                a = (rs.getInt("productID"));
            
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return a;
    }
    public double findPriceProByOrderID(int productID){
        int b = 0;
        String sql = "SELECT * FROM product WHERE productID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                b = (rs.getInt("price"));
            
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return b;
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
    
    public static boolean UpdateTotalPrice(int totalprice, int orderID){
        String sql = "UPDATE `order` SET " +
                    "totalprice = ? " +
                    "where orderID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setInt(1, totalprice);
            stmt.setInt(2, orderID);

                        
            int rowUpdated = stmt.executeUpdate();
            
            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No order updated");
                return false;
            }   
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    @FXML
    void btnAddClick(ActionEvent event) throws SQLException {
        if(txtAmount.getText().equals("")){
            lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
            lbMsg.setText("Amount not null!");
        }else{
            String amount = txtAmount.getText();
            String Strgoc = "0123456789";
            String out = "";
                for(int i = 0; i < amount.length(); i++){
                    for(int x = 0; x < Strgoc.length(); x++){
                        if((amount.charAt(i) == Strgoc.charAt(x))){
                            out += amount.charAt(i);
                        }
                    }
                }
            if(amount.equals(out)){
                String timproduct = cbProduct.getSelectionModel().getSelectedItem();
                int productID = findIDbyProductName(timproduct);
                double price = findPriceProByOrderID(productID);

                OrderDetailsClass insertProduct = extractOrderFromFields(this.orderID, productID, price);
                insertProduct = OrderDetailsClass.insert(insertProduct);
                allOrderDetails();
                int a = findPriceQuantity(this.orderID);
                boolean result = OrderDetailsController.UpdateTotalPrice(a, this.orderID);
                if(result){

                }
                resetForm();
            }else{
                    lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                    lbMsg.setText("Quantity must be number and cannot be negative!");
            }
        }
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
          
    }
    public void initialize(OrderClass orderDetails, String member){
        cbProduct.setItems(Products);
        cbProduct.getSelectionModel().selectFirst();
        txtAmount.setText("1");
        lbTest.setText(String.valueOf(orderDetails.getOrderID()));
        this.orderID = orderDetails.getOrderID();
        allOrderDetails();
        lbMember.setText(member);
        this.mem = member;
    }
    public void allOrderDetails(){
        ObservableList<OrderDetailsClass> orderDetailsList = OrderDetailsClass.selectByOrderID(this.orderID);
        
        tvOrderDetails.setItems(orderDetailsList);
        tcOrderID.setCellValueFactory((o) -> {
            return o.getValue().getOrderIDProperty();
        });
        tcProductID.setCellValueFactory((o) -> {
            return o.getValue().getProductIDProperty();
        });
        tcQuantity.setCellValueFactory((o) -> {
            return o.getValue().getQuantityProperty();
        });
        tcPriceProduct.setCellValueFactory((o) -> {
            return o.getValue().getPriceProductProperty();
        });
        tcPriceQuantity.setCellValueFactory((o) -> {
            return o.getValue().getPriceQuantityProperty();
        });        
    }
    
    private OrderDetailsClass extractOrderFromFields(int orderID, int productID, double pricePro) {
        OrderDetailsClass orderDetails = new OrderDetailsClass();
        orderDetails.setOrderID(orderID);
        orderDetails.setProductID(productID);
        int soluong = Integer.parseInt(txtAmount.getText());
        orderDetails.setQuantity(soluong);
        orderDetails.setPriceProduct(pricePro);
        orderDetails.setPriceQuantity(soluong * pricePro);
        return orderDetails;
    }
    public void resetForm(){
        cbProduct.getSelectionModel().selectFirst();
        txtAmount.setText("1");
        this.Amount = 1;
    }
}
