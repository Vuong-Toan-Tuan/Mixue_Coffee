/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.awt.Desktop;
import java.io.File;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ProductController implements Initializable {
    private String mem = null;
    private Product editProduct = null;
    
    @FXML
    private Label lbMessages;
    
    @FXML
    private GridPane GridPane;
    
    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtPrice;

    @FXML
    private ComboBox<String> cbbProductType;

    @FXML
    private ImageView txtImage;

    @FXML
    private Button btnBrowse;

    @FXML
    private TextArea txtDetails;

    @FXML
    private Button btnInsertP;

    @FXML
    private Button btnUpdateP;

    @FXML
    private Button btnDeleteP;

    @FXML
    private Button btnResetP;

    @FXML
    private Button btnDetails;

    @FXML
    private TableView<Product> tvProduct;

    @FXML
    private TableColumn<Product, String> tcProductName;
    
    @FXML
    private TableColumn<Product, Integer> tcProductType;

    @FXML
    private TableColumn<Product, Integer> tcPrice;

    @FXML
    private TableColumn<Product, String> tcImage;
    
    @FXML
    private TableColumn<Product, String> tcDetails;
    
     @FXML
    private Button btnBack;
     
    @FXML
    private Label lbMember;

    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    private TextField TfSearch;
        
    @FXML
    void btnSearchClick(ActionEvent event) {
        String a = TfSearch.getText();
        ObservableList<Product> products = Product.SearchProduct(a);
        tvProduct.setItems(products);
        
        tcProductName.setCellValueFactory((product) -> {
            return product.getValue().getPNameProperty(); 
        });
        
        tcProductType.setCellValueFactory((productType) -> {
            return productType.getValue().getPtIdProperty(); 
        });
        
        tcPrice.setCellValueFactory((price) -> {
            return price.getValue().getPriceProperty(); 
        });
        
        tcDetails.setCellValueFactory((details) -> {
            return details.getValue().getDetailsProperty(); 
        });
        
        tcImage.setCellValueFactory((img) -> {
            return img.getValue().getImageProperty(); 
        });
    }        

    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeManager(mem);
    }
    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }

    private Stage stage;  
    private File file;
    private FileChooser fileChooser;
    private final Desktop deskTop = Desktop.getDesktop();
    private Image images;
    
    @FXML
    void btnBrowseClick(ActionEvent event) {
        stage = (Stage) GridPane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage); 
        if(file != null){
            System.out.println(""+file.getAbsolutePath());
            images = new Image(file.getAbsoluteFile().toURI().toString(), txtImage.getFitWidth(), txtImage.getFitHeight(), true, true);
            txtImage.setImage(images);
            txtImage.setPreserveRatio(true);
        }
    }

    @FXML
    void btnDeletePClick(ActionEvent event) {
        Product selectedProduct = tvProduct.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) { //no book is selected
            selectProductWarning();
        } else { //a book is selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Bạn có chắc chắn muốn xóa sản phẩm đã chọn không?");
            alert.setTitle("Xóa sản phẩm");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.OK) {
                Product deleteProduct = tvProduct.getSelectionModel().getSelectedItem();
                
                int IDPro = deleteProduct.getPId();
                // delete order where productID = IDPro
                boolean result1 = Product.deleteOrderDetailsByProID(IDPro);
                boolean result2 = Cart.deleteCartByProID(IDPro);
                if(result1 && result2){
                    boolean result = Product.delete(deleteProduct);
                    if (result) {
                        tvProduct.getItems().remove(deleteProduct); //update TableView
                        System.out.println("Sản phẩm đã bị xóa");
                    } else {
                        System.err.println("Không có sản phẩm nào bị xóa");
                    }
                }else{
                    System.out.println("Sản phẩm trong order hoặc trong cart không bị xóa");
                }
                
                
            }
        }
    }

    @FXML
    void btnDetailsClick(ActionEvent event) throws IOException {
        Product productDetails = tvProduct.getSelectionModel().getSelectedItem();
        if (productDetails == null){
            selectProductWarning();
        }else{
            Navigator.getInstance().goToProduct_Details(productDetails);
        }
    }

    @FXML
    void btnInsertPClick(ActionEvent event) throws SQLException {
        String typename = cbbProductType.getSelectionModel().getSelectedItem();
        int typeid = findIDbyProductTypeName(typename);
        
        if(editProduct == null){
            if(txtProductName.getText().equals("") && txtPrice.getText().equals("") && txtDetails.getText().equals("") && txtImage.getImage() == null){
                lbMessages.setText("All fields cannot be empty!");
            } else if (txtProductName.getText().equals("")){
                lbMessages.setText("ProductName cannot be empty!");
            } else if(txtPrice.getText().equals("")){
                lbMessages.setText("Price cannot be empty!");
            } else if(txtDetails.getText().equals("")){
                lbMessages.setText("Ingredients cannot be empty!");
            } else if(txtImage.getImage() == null){
                lbMessages.setText("Image cannot be empty!");
            } else {
                String rl = txtProductName.getText();
                
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
                    Product insertProduct = extracProductFromFields(typeid);
                    insertProduct = Product.insert(insertProduct);

                    int id = insertProduct.getPId();
                    allProduct();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Successful!");
                    alert.setHeaderText("New product inserted with id: " + id);
                    alert.showAndWait();
                    resetForm();

                }else{
                    lbMessages.setText("ProductName must not have special symbols!");
                }
                
                
            }
        }else{
            Product updateProduct = extracProductFromFields(typeid);
            updateProduct.setPId(this.editProduct.getPId());
            boolean result = Product.update(updateProduct);
            if(result){
                int id = updateProduct.getPId();

                allProduct();
                resetForm();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Successful!");
                alert.setHeaderText("Update product successful with id: "+id);
                alert.showAndWait();
            }else{
                System.out.println("Error!");
            }
        }
    }

    @FXML
    void btnResetPClick(ActionEvent event) {
        resetForm();
    }

    @FXML
    void btnUpdatePClick(ActionEvent event) {
        Product selectedProduct = tvProduct.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) { //no book is selected
            selectProductWarning();
        } else {
            String msg = "";
            if (selectedProduct == null) { //insert a new book
               System.out.println("Tạo một sản phẩm mới");
            } else { //update an existing book
                System.out.println("Cập nhật sản phẩm hiện có");
                txtProductName.setText(selectedProduct.getPName());
                txtPrice.setText(String.valueOf(selectedProduct.getPrice()));
//                cbbProductType.getSelectionModel().select(selectedProduct.getPtId());
                
//                String url = "eProject2/image/"+selectedProduct.getImage();
////            System.out.println(url);
//                images = new Image(url, txtImage.getFitWidth(), txtImage.getFitHeight(), true, true);
//                txtImage.setImage(images);
                txtDetails.setText(selectedProduct.getDetails());
                
                this.editProduct = selectedProduct;
            }

//            lbMessage.setText(msg);
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        allProduct();
        cbbProductType.setItems(CBproductType);
        cbbProductType.getSelectionModel().selectFirst();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All files", "*.*"),
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
            new FileChooser.ExtensionFilter("Text File", "*.txt"));
    }
    public void initialize(String member){
        lbMember.setText(member);
        this.mem = member;
    }
    
    private void allProduct(){
        ObservableList<Product> pList = Product.selectAll();
        
        tvProduct.setItems(pList);
        
        tcProductName.setCellValueFactory((product) -> {
            return product.getValue().getPNameProperty(); 
        });
        
        tcProductType.setCellValueFactory((productType) -> {
            return productType.getValue().getPtIdProperty(); 
        });
        
        tcPrice.setCellValueFactory((price) -> {
            return price.getValue().getPriceProperty(); 
        });
        
        tcDetails.setCellValueFactory((details) -> {
            return details.getValue().getDetailsProperty(); 
        });
        
        tcImage.setCellValueFactory((img) -> {
            return img.getValue().getImageProperty(); 
        });
    }
    
    ObservableList<String> CBproductType = findProductType();
    
    public static ObservableList<String> findProductType(){
        ObservableList<String> role = FXCollections.observableArrayList();
        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM producttype");) {
            while (rs.next()) {
//                
                role.add(rs.getString("typename"));
//                role.set(rs.getInt("roleID", rs.getString("rolename")));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return role;    
    }
    
    public Integer findIDbyProductTypeName(String typename){
        int a = 0;
        String sql = "SELECT * FROM producttype WHERE typename = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setString(1, typename);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                a = (rs.getInt("typeID"));
            
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return a;
    }
    
    private void selectProductWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Vui lòng chọn một sản phẩm");
        alert.setHeaderText("Một sản phẩm phải được chọn cho hoạt động");
        alert.showAndWait();
    } 
    
    private Product extracProductFromFields(int typeid) {
       Product product = new Product();
       product.setPName(txtProductName.getText());
       product.setPtId(typeid);
       product.setPrice(Integer.parseInt(txtPrice.getText()));
       product.setDetails(txtDetails.getText());
       String img = file.getName();
       product.setImage(img);
       
       return product;
    }
    public void resetForm(){
        txtProductName.setText("");
        txtPrice.setText("");
        cbbProductType.getSelectionModel().selectFirst();
        txtImage.setImage(null);
        txtDetails.setText("");
        this.editProduct = null;
    }
}
