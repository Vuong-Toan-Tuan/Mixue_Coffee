/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class Navigator {
    public static final String MEMBER_DETAILS = "MemberDetails.fxml";
    public static final String MEMBER_INDEX = "Member.fxml";
    public static final String HOME_MANAGER = "HomeManager.fxml";
    public static final String LOGIN = "Login.fxml";
    public static final String HOME_STAFF = "HomeStaff.fxml";
    public static final String ROLE = "Role.fxml";
    public static final String ORDER = "Order.fxml";
    public static final String ORDER_DETAILS = "OrderDetails.fxml";
    public static final String PRODUCT_DETAILS = "ProductDetails.fxml";
    public static final String PRODUCT_INDEX = "Product.fxml";
    public static final String PRODUCT_TYPE = "ProductType.fxml";
    public static final String HOME_DETAILS_PRODUCT = "HomeDetailsProduct.fxml";
    public static final String HOME_CUSTOMER = "Home.fxml";
    public static final String SEARCH_PRODUCT = "Search.fxml";
    public static final String CART = "Cart.fxml";
    
    private FXMLLoader loader;
    private Stage stage = null;
    
    private static Navigator nav = null;
    
    private Navigator(){    
    }
    public static Navigator getInstance(){
        if (nav == null){
            nav = new Navigator();
        }
        return nav;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public Stage getStage() {
        return stage;
    }
    private void goTo(String fxml) throws IOException {
        this.loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
//        loader.setResources(Translator.getResource());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
    }
    public void goToMember_Details(Member memberDetails, String member) throws IOException{
        this.goTo(MEMBER_DETAILS);
        MemberDetailsController ctrl = loader.getController();
        ctrl.initialize(memberDetails, member);
    }
    public void goToMember_Index(String member) throws IOException{
        this.goTo(MEMBER_INDEX);
        MemberController ctrl = loader.getController();
        ctrl.initialize(member);
    }
    public void goToHomeManager(String member) throws IOException{
        this.goTo(HOME_MANAGER);
        HomeManagerController ctrl = loader.getController();
        ctrl.initialize(member);
    }
    
    public void goToHomeManager() throws IOException{
        this.goTo(HOME_MANAGER);
    }
    
    public void goToLogin() throws IOException{
        this.goTo(LOGIN);
    }
    
    public void goToHomeStaff() throws IOException{
        this.goTo(HOME_STAFF);
    }
    
     public void goToRole(String member) throws IOException{
        this.goTo(ROLE);
        RoleController ctrl = loader.getController();
        ctrl.initialize(member);
    }
     
     public void goToOrder(String member) throws IOException{
        this.goTo(ORDER);
        OrderController ctrl = loader.getController();
        ctrl.initialize(member);
    }
    
    public void goToDetails(OrderClass orderDetails, String member) throws IOException {
        this.goTo(ORDER_DETAILS);
        OrderDetailsController ctrl = loader.getController();
        ctrl.initialize(orderDetails, member);
    }

    public void goToOrderDetails() throws IOException{
        this.goTo(ORDER_DETAILS);
     }
    
    public void goToProduct_Details(Product productDetails) throws IOException{
        this.goTo(PRODUCT_DETAILS);
//        ProductDetailsController ctrl = loader.getController();
//        ctrl.initialize(productDetails);
    }
    public void goToProduct_Index(String member) throws IOException{
        this.goTo(PRODUCT_INDEX);
        ProductController ctrl = loader.getController();
        ctrl.initialize(member);
    }
    
    public void goToProduct_Type(String member) throws IOException{
        this.goTo(PRODUCT_TYPE);
        ProductTypeController ctrl = loader.getController();
        ctrl.initialize(member);
    }
    public void goToHOME_DETAILS_PRODUCT(int productID, String member) throws IOException{
        this.goTo(HOME_DETAILS_PRODUCT);
        HomeDetailsProductController ctrl = loader.getController();
        ctrl.initialize(productID, member);
    }
    public void goToHome_Customer(String member) throws IOException{
        this.goTo(HOME_CUSTOMER);
        HomeController ctrl = loader.getController();
        ctrl.initialize(member);
    }
    public void goToSearch_Product(String pName, String member) throws IOException{
        this.goTo(SEARCH_PRODUCT);
        SearchController ctrl = loader.getController();
        ctrl.initialize(pName, member);
    }
    public void goToCART(String member) throws IOException{
        this.goTo(CART);
        CartController ctrl = loader.getController();
        ctrl.initialize(member);
    }
}
