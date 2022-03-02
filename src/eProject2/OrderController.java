/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Binh An
 */
public class OrderController implements Initializable {
    private String mem = null;
    private FXMLLoader loader;

    private OrderClass editOrder = null;
    
    @FXML
    private TableView<OrderClass> tvOrders;
    
    @FXML
    private TableColumn<OrderClass, Integer> tcID;

    @FXML
    private TableColumn<OrderClass, String> tcKH;

    @FXML
    private TableColumn<OrderClass, String> tcNV;

    @FXML
    private TableColumn<OrderClass, String> tcTime;

    @FXML
    private TableColumn<OrderClass, Integer> tcTongTien;

    @FXML
    private Button xoa;

    @FXML
    private Button Sua;
    
    @FXML
    private Button Details;
    
    @FXML
    private Button btnReset;
    
    @FXML
    private Button btnOkClick;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnPrint;
    
    @FXML
    private Label lbMember;
    
    @FXML
    private TextArea TenKH;


    @FXML
    private TextField lbsp;

    @FXML
    private Label txtmsg;
    
    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeManager(mem);
    }
    
    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }
    
    @FXML
    void btnDetailsClick(ActionEvent event) throws IOException {
//        NavigatorOrder.getInstance().goToOrderDetails();
        
        OrderClass orderDetails = tvOrders.getSelectionModel().getSelectedItem();

        if (orderDetails == null) { //no teacher is selected
            selectOrderWarning();
        } else { //a book is selected
            Navigator.getInstance().goToDetails(orderDetails, this.mem);
        }
    }    


    @FXML
    void btnXoaClick(ActionEvent event) {
        OrderClass selectedOrder = tvOrders.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) { //no book is selected
            selectOrderWarning();
        } else { //a ... is selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete the selected order?");
            alert.setTitle("Deleting a order");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.OK) {
                OrderClass deleteBook = tvOrders.getSelectionModel().getSelectedItem();
                int orderID = deleteBook.getOrderID();
                boolean result2 = OrderClass.deleteOrderDetails(orderID);
                boolean result = OrderClass.delete(deleteBook);

                if (result) {
                    tvOrders.getItems().remove(deleteBook); //update TableView
                    System.out.println("A order is deleted");
                    
                } else {
                    System.err.println("No order is deleted");
                }
            }
        }

    }

    @FXML
    void btnOkClick(ActionEvent event) throws SQLException {
        if(editOrder == null){
            if(TenKH.getText().equals("")){
                txtmsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                txtmsg.setText("Amount not null!");
            }else{
                String rl = TenKH.getText();
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
                    OrderClass insertOrder = extractOrderFromFields();
                    insertOrder = OrderClass.insert(insertOrder);
                    tvOrders.getItems().add(insertOrder);
                    String msg = "New book inserted with id: " + insertOrder.getOrderID();
                    txtmsg.setText(msg);
                    allOrder();
                    resetFields();
                }else{
                    txtmsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                    txtmsg.setText("Customer information must not have special symbols!");
                }
            }
            
        }else{
            
            if(TenKH.getText().equals("")){
                txtmsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                txtmsg.setText("Amount not null!");
            }else{
                String rl = TenKH.getText();
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
                    OrderClass updateOrder = extractOrderFromFields();
                    updateOrder.setOrderID(this.editOrder.getOrderID());
                    boolean result = OrderClass.update(updateOrder);
                    int id = updateOrder.getOrderID();
                    if(result){
                        allOrder();
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Update successful");
                        alert.setHeaderText("Update order successful with id: "+ id);
                        alert.showAndWait();
                        resetFields();
                    }
                }else{
                    txtmsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                    txtmsg.setText("Customer information must not have special symbols!");
                }
            }     
        }
    }

    @FXML
    void btnSuaClick(ActionEvent event) {
         OrderClass selectedOrder = tvOrders.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) { //no book is selected
            selectOrderWarning();
        } else { //a book is selected
//              OrderController ctrl = loader.getController();
//        ctrl.initialize(editOrder);
            TenKH.setText(selectedOrder.getCustomerInformation());
            editOrder = selectedOrder;
        }


    }
    
    
    @FXML
    void btnResetClick(ActionEvent event) {
        resetFields();
    }

@FXML
    void btnPrintClick(ActionEvent event) throws IOException {

        OrderClass orderDetails = tvOrders.getSelectionModel().getSelectedItem();
        
        if (orderDetails == null) { 
            selectOrderWarning();
        } else {
            try {
                ObservableList<OrderDetailsClass> orderDetailsList = OrderDetailsClass.selectByOrderID(orderDetails.getOrderID());
                File f = new File("C:/Users/Admin/Documents/NetBeansProjects/project2_java/src/Printf.txt");
                f.delete();
                FileWriter fw = new FileWriter(f);
                fw.write("\t\t MIXUE COFFEE \n\n");
                fw.write("\t\t  HOA DON BAN HANG\n");
                fw.write("");
                fw.write("Ten KH     : " + orderDetails.getCustomerInformation()+"\n");
                fw.write("Ngay ban   : " + orderDetails.getTimeOrder()+"\n");
                fw.write("NVBH       : " + orderDetails.getMemberName()+"\n");
                fw.write("--------------------------------------------\n");
                fw.write("Mat hang              SL    Don gia    Tong tien \n");
                for(int i = 0; i < orderDetailsList.size(); i++){
                    int productid = orderDetailsList.get(i).getProductID();
                    String product = OrderDetailsClass.findProductNameByProductID(productid);
                    int a = orderDetailsList.get(i).getQuantity();
                    double b = orderDetailsList.get(i).getPriceProduct();
                    double c = orderDetailsList.get(i).getPriceQuantity();

                    
                    fw.write(product +"         " + a + "    " + b + "    " + c + "\n");
                   
                }
                  fw.write("--------------------------------------------\n");
                  fw.write("Tong hoa don :                       " + orderDetails.getTotalPrice() +"(VND)\n\n\n");
                  fw.write("   Cam on va hen gap lai quy khach!!!");
//               
                
                fw.close();
            } catch (IOException ex) {
                System.out.println(" looix" + ex);
            }
         //Mở file txt
            Runtime run = Runtime.getRuntime();
            try {
                run.exec("notepad C:/Users/Admin/Documents/NetBeansProjects/project2_java/src/Printf.txt");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allOrder();  
    }
    
    public void initialize(String member){
        lbMember.setText(member);
        this.mem = member;
    }
    
    public void allOrder(){
        ObservableList<OrderClass> olist = OrderClass.selectAll();
        tvOrders.setItems(olist);

        tcID.setCellValueFactory((OrderClass) -> {
            return OrderClass.getValue().getOrderIdProperty();
        });

        tcKH.setCellValueFactory((OrderClass) -> {
            return OrderClass.getValue().getCustomerInformationProperty();
        });

        tcNV.setCellValueFactory((OrderClass) -> {
            return OrderClass.getValue().getMemberNameProperty();
        });

        tcTime.setCellValueFactory((OrderClass) -> {
            return OrderClass.getValue().getTimeOrderProperty();
        });

        tcTongTien.setCellValueFactory((OrderClass) -> {
            return OrderClass.getValue().getTotalPriceProperty();
        });
    }
    
//    public void initialize(OrderClass editOrder) {
//        this.editOrder = editOrder;
//        String msg = "";
//        if (this.editOrder == null) { //insert a new 
//            msg = "Create a new order";
//        } else { //update an existing book
//            msg = "Update an existing book";
//            TenKH.setText(editOrder.getCustomerInformation());
////            txtAuthor.setText(editBook.getAuthor());
////            txtPages.setText(
////                    Integer.toString(editBook.getPages())
////            );
//        }
//
//        txtmsg.setText(msg);
//    }

    private void selectOrderWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Please select a order");
        alert.setHeaderText("A order must be selected for the operation");
        alert.showAndWait();
    }

    private OrderClass extractOrderFromFields() {
        OrderClass order = new OrderClass();
        order.setCustomerInformation(TenKH.getText());
        order.setMemberName(this.mem);
        order.setTimeOrder(String.valueOf(java.time.LocalDateTime.now()));
        order.setTotalPrice(
                Integer.parseInt("0")
        );

        return order;
    }

    public void resetFields(){
        TenKH.setText("");
        txtmsg.setText("");
        editOrder = null;
    }
   
}