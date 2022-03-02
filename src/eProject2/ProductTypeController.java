/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author admin
 */
public class ProductTypeController implements Initializable{
    private String mem = null;
    private ProductType editProductType = null;
    
    @FXML
    private Label lbMessages;
    
    @FXML
    private TableView<ProductType> tvProductType;

    @FXML
    private TableColumn<ProductType, Integer> tcProductTypeID;

    @FXML
    private TableColumn<ProductType, String> tcProductTypeName;

    @FXML
    private TextField txtProductTypeName;

    @FXML
    private Button btnInsertPT;

    @FXML
    private Button btnUpdatePT;

    @FXML
    private Button btnDeletePT;

    @FXML
    private Button btnResetPT;
    
    @FXML
    private Button btnBack;
     
    @FXML
    private Label lbMember;
    
    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;
    
    @FXML
    void btnSearchClick(ActionEvent event) {
        String ptName = txtSearch.getText();
        ObservableList<ProductType> ptList = ProductType.SearchTypeProduct(ptName);
        tvProductType.setItems(ptList);
        tcProductTypeName.setCellValueFactory((productType) -> {
            return productType.getValue().getPtNameProperty(); 
        });
        
//        tcProductTypeID.setCellValueFactory((ptID) -> {
//            return ptID.getValue().getPtIdProperty(); 
//        });
    }

    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeManager(mem);
    }
    
    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    @FXML
    void btnDeleteClickPT(ActionEvent event) {
        ProductType selectedProductType = tvProductType.getSelectionModel().getSelectedItem();

        if (selectedProductType == null) { //no book is selected
            selectProductTypeWarning();
        } else { //a product type is selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Bạn có chắc chắn muốn xóa loại sản phẩm đã chọn không?");
            alert.setTitle("Xóa loại sản phẩm");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.OK) {
                ProductType deleteProductType = tvProductType.getSelectionModel().getSelectedItem();
                
                int IDProType = deleteProductType.getPtId();
                ObservableList<Product> productlist = Product.selectAllProByProTypeID(IDProType);
                for(int i = 0; i < productlist.size(); i++){
                    int IDPro = productlist.get(i).getPId();
                    boolean result1 = Product.deleteOrderDetailsByProID(IDPro);
                    boolean result2 = Cart.deleteCartByProID(IDPro);
                    if(result1 && result2){
                        boolean result = Product.delete(productlist.get(i));
                        if (result) {
//                            tvProduct.getItems().remove(deleteProduct); //update TableView
                            System.out.println("Sản phẩm đã bị xóa");
                        } else {
                            System.err.println("Không có sản phẩm nào bị xóa");
                        }
                    }else{
                        System.out.println("Sản phẩm trong order hoặc trong cart không bị xóa");
                    }
                }
                
                boolean result = ProductType.delete(deleteProductType);
                
                if (result) {
                    tvProductType.getItems().remove(deleteProductType); //update TableView
                    lbMessages.setText("Loại sản phẩm đã bị xóa");
                } else {
                    lbMessages.setText("Không có loại sản phẩm nào bị xóa");
                }
            }
        }
    }

    @FXML
    void btnInsertClickPT(ActionEvent event) throws SQLException {
        if(editProductType == null){
            if(txtProductTypeName.getText().equals("")){
                lbMessages.setText("Loại sản phẩm không được trống");
            } else{
                String rl = txtProductTypeName.getText();
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
                    ProductType insertProductType = extractProductTypeFromFields();
            
                    insertProductType = ProductType.insert(insertProductType);
                    allProductType();
                    lbMessages.setText("Loại sản phẩm mới được thêm với mã: " +insertProductType.getPtId());
                    txtProductTypeName.clear();

                }else{
                    lbMessages.setText("Loại sản phẩm không chứa ký tự đặc biệt!");
                }
            }   
        }else{
            
            if(txtProductTypeName.getText().equals("")){
                lbMessages.setText("Loại sản phẩm không được bỏ trống");
            } else{
                String rl = txtProductTypeName.getText();
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
                    ProductType updateProductType = extractProductTypeFromFields();
                    updateProductType.setPtId(this.editProductType.getPtId());
                    boolean result = ProductType.update(updateProductType);
                    int id = updateProductType.getPtId();
                    allProductType();
                    lbMessages.setText("Loại sản phẩm mới được sửa với mã: " + id);
                    txtProductTypeName.clear();

                }else{
                    lbMessages.setText("Loại sản phẩm không chứa ký tự đặc biệt!");
                }
            }
        }
    }

    @FXML
    void btnResetClickPT(ActionEvent event) {
        txtProductTypeName.setText("");
        this.editProductType = null;
        allProductType();
        txtSearch.setText("");
    }

    @FXML
    void btnUpdateClickPT(ActionEvent event) {
        ProductType selectedProductType = tvProductType.getSelectionModel().getSelectedItem();

        if (selectedProductType == null) { //no book is selected
            selectProductTypeWarning();
        } else {
            String msg = "";
            if (selectedProductType == null) { //insert a new book
                lbMessages.setText("Tạo một loại sản phẩm mới");
            } else { //update an existing book
                lbMessages.setText("Cập nhật loại sản phẩm hiện có");
                txtProductTypeName.setText(selectedProductType.getPtName());
                this.editProductType = selectedProductType;
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        allProductType();
    }
    public void initialize(String member){
        lbMember.setText(member);
        this.mem = member;
    }
    
    private void allProductType(){
        ObservableList<ProductType> ptList = ProductType.selectAll();
        
        tvProductType.setItems(ptList);
        
        tcProductTypeName.setCellValueFactory((productType) -> {
            return productType.getValue().getPtNameProperty(); 
        });
        
        tcProductTypeID.setCellValueFactory((ptID) -> {
            return ptID.getValue().getPtIdProperty(); 
        });
    }
    
    private void selectProductTypeWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Vui lòng chọn một loại sản phẩm");
        alert.setHeaderText("Một loại sản phẩm phải được chọn cho hoạt động");
        alert.showAndWait();
    }
    
    private ProductType extractProductTypeFromFields() {
        ProductType producttype = new ProductType();
        producttype.setPtName(txtProductTypeName.getText());
        return producttype;
    }
    
}
