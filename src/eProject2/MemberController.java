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
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MemberController implements Initializable {
    private String mem = null;
    
    private Member editMember = null;
    
    @FXML
    private Pane Pane;
    
    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private ImageView txtImg;

    @FXML
    private ComboBox<String> txtRole;

    @FXML
    private ComboBox<String> txtGender;

    @FXML
    private ComboBox<String> txtShift;

    @FXML
    private TextField txtIDCard;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDetails;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Member> tvMembers;

    @FXML
    private TableColumn<Member, String> tcUsername;

    @FXML
    private TableColumn<Member, String> tcFullname;

    @FXML
    private TableColumn<Member, String> tcIDCard;

    @FXML
    private TableColumn<Member, String> tcGender;

    @FXML
    private TableColumn<Member, String> tcPhone;

    @FXML
    private TableColumn<Member, String> tcEmail;

    @FXML
    private TableColumn<Member, String> tcAddress;

    @FXML
    private TableColumn<Member, Integer> tcRole;

    @FXML
    private TableColumn<Member, String> tcImg;

    @FXML
    private TableColumn<Member, String> tcShift;
    
    
    
    @FXML
    private TextField txtFullname;

    @FXML
    private TextArea  txtAddress;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPasswordConfirm;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Label lbMember;
    
    @FXML
    private Label lbMsg;
    
    private Stage stage;  
    private File file;
    private FileChooser fileChooser;
    private final Desktop deskTop = Desktop.getDesktop();
    private Image images;

    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    private TextField tfsearchMember;

    @FXML
    private Button btnSearch;
    
    @FXML
    void btnSearchClick(ActionEvent event) {
        String a = tfsearchMember.getText();
        ObservableList<Member> members = Member.SearchMeber(a);
        tvMembers.setItems(members);
        
        tcUsername.setCellValueFactory((member) -> {
            return member.getValue().getUsernameProperty(); 
        });
        
        tcFullname.setCellValueFactory((fullname) -> {
            return fullname.getValue().getFullnameProperty(); 
        });
        
        tcIDCard.setCellValueFactory((cmt) -> {
            return cmt.getValue().getIdCardProperty(); 
        });
        
        tcGender.setCellValueFactory((gender) -> {
            return gender.getValue().getGenderProperty(); 
        });
        
        tcPhone.setCellValueFactory((sdt) -> {
            return sdt.getValue().getPhoneProperty(); 
        });
        
        tcEmail.setCellValueFactory((email) -> {
            return email.getValue().getEmailProperty(); 
        });
        
        tcAddress.setCellValueFactory((dc) -> {
            return dc.getValue().getAddressProperty(); 
        });
        
        tcRole.setCellValueFactory((role) -> {
            return role.getValue().getRoleIdProperty(); 
        });
        
        tcImg.setCellValueFactory((img) -> {
            return img.getValue().getImgProperty(); 
        });
        
        tcShift.setCellValueFactory((shift) -> {
            return shift.getValue().getShiftProperty(); 
        });
    }
    
    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }
    
    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeManager(mem);
    }
    
    ObservableList<String> gender = FXCollections.observableArrayList("Mr ", "Ms ");
    ObservableList<String> shift = FXCollections.observableArrayList("Full time", "Part time");
    ObservableList<String> roles = findRole();
    public static ObservableList<String> findRole(){
        ObservableList<String> role = FXCollections.observableArrayList();
        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM role");) {
            while (rs.next()) {
//                
                role.add(rs.getString("rolename"));
//                role.set(rs.getInt("roleID", rs.getString("rolename")));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return role;    
    }
    public Integer findIDbyRoleName(String rolename){
        int a = 0;
        String sql = "SELECT * FROM role WHERE rolename = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setString(1, rolename);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                a = (rs.getInt("roleID"));
            
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return a;
    }
    public String findRoleNamebyID(int roleid){
        String b = null;
        String sql = "SELECT * FROM role WHERE roleID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setInt(1, roleid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                b = (rs.getString("rolename"));
            
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return b;
    }
    public String findMemberAvailable(String username){
        String name = null;
        String sql = "SELECT * FROM member WHERE username = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                name = (rs.getString("username"));
            
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            logger.error(e);
        }
        return name;
    }
    
    private Member extractMemberFromFields(int roleid){
        String ps1 = txtPassword.getText();
        String ps2 = txtPasswordConfirm.getText();
        Member member = new Member();
        
        String username = txtUserName.getText();
        String Strgoc = "1234567890qwertyuiopasdfghjklzxcvbnm";
        String out1 = "";
        for(int i = 0; i < username.length(); i++){
            for(int x = 0; x < Strgoc.length(); x++){
                if(username.charAt(i) == Strgoc.charAt(x)){
                    out1 += username.charAt(i);
                }
            }
        }
        if ((ps1.equals(ps2)) && (username.equals(out1))){
            
            member.setIdCard(txtIDCard.getText());
            member.setUsername(txtUserName.getText());
            member.setFullname(txtFullname.getText());
            member.setPassword(txtPassword.getText());
            member.setGender(txtGender.getSelectionModel().getSelectedItem().toString());
            member.setPhone(txtPhone.getText());
            member.setEmail(txtEmail.getText());
            member.setAddress(txtAddress.getText());

            member.setRoleId(roleid);
            String img = file.getName();
            member.setImg(img);
            member.setShift(txtShift.getSelectionModel().getSelectedItem().toString());

//            return member;
        }else{
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Error");
//            alert.setHeaderText("UserName contains no special characters Password and PasswordConfirm must be the same");
//            alert.showAndWait();
            lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
            lbMsg.setText("Tên tài khoản không chứa ký tự! Mật khẩu và mật khẩu xác nhận phải giống nhau!");
        }
        return member;
    }
    
    @FXML
    void btnDeleteClick(ActionEvent event) {
        Member selectedmember = tvMembers.getSelectionModel().getSelectedItem();
        if(selectedmember == null){
            selectMemberWarning();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Bạn có chắc muốn xóa thành viên đã chọn ?");
            alert.setTitle("Xóa ");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if(confirmationResponse.get() == ButtonType.OK){
                boolean result = Member.delete(selectedmember);
                if(result){
                    tvMembers.getItems().remove(selectedmember);
                    System.out.println("A member is deleted");
                }else{
                    System.out.println("No member is deleted");
                }
            }
        }
    }

    @FXML
    void btnDetailsClick(ActionEvent event) throws IOException {
        Member memberDetails = tvMembers.getSelectionModel().getSelectedItem();
        if (memberDetails == null){
            selectMemberWarning();
        }else{
            Navigator.getInstance().goToMember_Details(memberDetails, mem);
        }
    }

    @FXML
    void btnInsertClick(ActionEvent event) throws SQLException {
        String tim = txtRole.getSelectionModel().getSelectedItem().toString();
        int abc = findIDbyRoleName(tim);
        if(editMember == null){
            if(txtUserName.getText().equals("") || txtFullname.getText().equals("") || txtEmail.getText().equals("") || txtPhone.getText().equals("") || txtAddress.getText().equals("") || txtIDCard.getText().equals("") || txtImg.getImage() == null){
                lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                lbMsg.setText("Không được để trống các trường!");
            }else{
                String CMT = txtIDCard.getText();
                String phone = txtPhone.getText();
                String Strgoc = "0123456789";
                String out = "";
                String out2 = "";
//                    for(int i = 0; i < phone.length(); i++){
//                        for(int x = 0; x < Strgoc.length(); x++){
//                            if((phone.charAt(i) == Strgoc.charAt(x)) && (CMT.charAt(i) == Strgoc.charAt(x))){
//                                out += phone.charAt(i);
//                                out2 += CMT.charAt(i);
//                            }
//                        }
//                    }
                    for(int i = 0; i < phone.length(); i++){
                        for(int x = 0; x < Strgoc.length(); x++){
                            if(phone.charAt(i) == Strgoc.charAt(x)){
                                out += phone.charAt(i);
                            }
                        }
                    }
                    for(int i = 0; i < CMT.length(); i++){
                        for(int x = 0; x < Strgoc.length(); x++){
                            if(CMT.charAt(i) == Strgoc.charAt(x)){
                                out2 += CMT.charAt(i);
                            }
                        }
                    }
                if(phone.equals(out) && CMT.equals(out2)){
                    // thực hiện câu function tìm username theo username nhập 
                    String a = txtUserName.getText();
                    String b = findMemberAvailable(a);
                    if(b != null){
                        lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                        lbMsg.setText("Tên tài khoản: \""+a+"\" đã có người sử dụng mời bạn nhập tên khác");
                    }else if(b == null){
                        Member insertMember = extractMemberFromFields(abc);
                        insertMember = Member.insert(insertMember);

                        int id = insertMember.getId();
                        InsertSuccessfull(id);
                        allmember();
                        reserForm();
                    }

                }else{
                    lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                    lbMsg.setText("SĐT và CMT phải là chữ số!");
                }  
            }
        }else {
            if(txtUserName.getText().equals("") || txtFullname.getText().equals("") || txtEmail.getText().equals("") || txtPhone.getText().equals("") || txtAddress.getText().equals("") || txtIDCard.getText().equals("") || txtImg.getImage() == null){
                lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                lbMsg.setText("Không được để trống các trường!");
            }else{
                String CMT = txtIDCard.getText();
                String phone = txtPhone.getText();
                String Strgoc = "0123456789";
                String out = "";
                String out2 = "";
                    for(int i = 0; i < phone.length(); i++){
                        for(int x = 0; x < Strgoc.length(); x++){
                            if(phone.charAt(i) == Strgoc.charAt(x)){
                                out += phone.charAt(i);
                            }
                        }
                    }
                    for(int i = 0; i < CMT.length(); i++){
                        for(int x = 0; x < Strgoc.length(); x++){
                            if(CMT.charAt(i) == Strgoc.charAt(x)){
                                out2 += CMT.charAt(i);
                            }
                        }
                    }
                if(phone.equals(out) && CMT.equals(out2)){
                    Member updateMember = extractMemberFromFields(abc);
                    updateMember.setId(this.editMember.getId());
                    boolean result = Member.update(updateMember);
                    int id = updateMember.getId();
                    if(result){
                        UpdateSuccessfull(id);
                        allmember();
                        reserForm();
                    }
                }else{
                    lbMsg.setTextFill(javafx.scene.paint.Color.TOMATO);
                    lbMsg.setText("SĐT và CMT phải là chữ số!");
                }

            }
        }
    }

    @FXML
    void btnResetClick(ActionEvent event) {
        reserForm();
    }

    public void testUpdate(){
        Member selectedMember = tvMembers.getSelectionModel().getSelectedItem();
        
        if(selectedMember == null){
            selectMemberWarning();
        }else{
            txtUserName.setText(selectedMember.getUsername());
            txtFullname.setText(selectedMember.getFullname());
            txtPassword.setText(selectedMember.getPassword());
            txtPasswordConfirm.setText(selectedMember.getPassword());
//            int roleid = selectedMember.getRoleId();
//            String rolename = findRoleNamebyID(roleid);
//            selectedMember.setText(rolename);
//            if("Mr".equals(selectedMember.getGender())){
//                txtGender.setValue("Mr");
//            }
//            if("Ms".equals(selectedMember.getGender())){
//                txtGender.setSelectionModel(selectedMember.getGender());
//            }
            txtEmail.setText(selectedMember.getEmail());
            txtPhone.setText(selectedMember.getPhone());
            txtAddress.setText(selectedMember.getAddress());
            txtIDCard.setText(selectedMember.getIdCard());
            
//            String url = "eProject2/image/"+selectedMember.getImg();
////            System.out.println(url);
//            images = new Image(url, txtImg.getFitWidth(), txtImg.getFitHeight(), true, true);
//            txtImg.setImage(images);
            

            this.editMember = selectedMember;
        }
    }
    
    @FXML
    void btnUpdateClick(ActionEvent event) {
        testUpdate();
    }
    


    @FXML
    private Button btnBrowse;
    
    @FXML
    void btnBrowseClick(ActionEvent event) {
        stage = (Stage) Pane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage); 
        if(file != null){
            System.out.println(""+file.getAbsolutePath());
            images = new Image(file.getAbsoluteFile().toURI().toString(), txtImg.getFitWidth(), txtImg.getFitHeight(), true, true);
            txtImg.setImage(images);
            txtImg.setPreserveRatio(true);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    
    public void allmember(){
        ObservableList<Member> memberlist = Member.selectAll();
        
        tvMembers.setItems(memberlist);
        tcUsername.setCellValueFactory((member) -> {
            return member.getValue().getUsernameProperty();
        });
        tcFullname.setCellValueFactory((member) -> {
            return member.getValue().getFullnameProperty();
        });
        tcIDCard.setCellValueFactory((member) -> {
            return member.getValue().getIdCardProperty();
        });
        tcGender.setCellValueFactory((member) -> {
            return member.getValue().getGenderProperty();
        });
        tcPhone.setCellValueFactory((member) -> {
            return member.getValue().getPhoneProperty();
        });
        tcEmail.setCellValueFactory((member) -> {
            return member.getValue().getEmailProperty();
        });
        tcAddress.setCellValueFactory((member) -> {
            return member.getValue().getAddressProperty();
        });
        tcRole.setCellValueFactory((member) -> {
            return member.getValue().getRoleIdProperty();
        });
        tcImg.setCellValueFactory((member) -> {
            return member.getValue().getImgProperty();
        });
        tcShift.setCellValueFactory((member) -> {
            return member.getValue().getShiftProperty();
        });
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtGender.setItems(gender);
        txtShift.setItems(shift);
        txtRole.setItems(roles);
        
        txtRole.getSelectionModel().selectFirst();
        txtGender.getSelectionModel().selectFirst();
        txtShift.getSelectionModel().selectFirst();
        

        allmember();
        
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
    
    private void selectMemberWarning(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Hãy chọn 1 thành viên");
        alert.setHeaderText("Một thành viên phải được chọn cho hoạt động");
        alert.showAndWait();
    }
    public void InsertSuccessfull(int id){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thành công!");
        alert.setHeaderText("Thành viên mới đã được thêm với id:: "+id);
        alert.showAndWait();
    }
    public void UpdateSuccessfull(int id){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thành công!");
        alert.setHeaderText("Cập nhật thành công thông tin thành viên với id: "+id);
        alert.showAndWait();
    }
    public void reserForm(){
        lbMsg.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtPasswordConfirm.setText("");
        txtRole.getSelectionModel().selectFirst();
        txtFullname.setText("");
        txtGender.getSelectionModel().selectFirst();
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtIDCard.setText("");
        txtShift.getSelectionModel().selectFirst();
        txtImg.setImage(null);
        this.editMember = null;
        allmember();
    }
}
