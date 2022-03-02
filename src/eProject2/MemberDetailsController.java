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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MemberDetailsController implements Initializable {
    private String mem = null;
    @FXML
    private Label dtUsername;

    @FXML
    private Label dtFullname;

    @FXML
    private Label dtIDcard;

    @FXML
    private Label dtGender;

    @FXML
    private Label dtPhone;

    @FXML
    private Label dtEmail;

    @FXML
    private Label dtAddress;

    @FXML
    private Label dtRole;

    @FXML
    private ImageView dtImg;

    @FXML
    private Label dtShift;
    
    @FXML
    private Button btnBack;
    
    private Image images;
    
    @FXML
    private Label lbMember;

    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLogin();
    }
    
    @FXML
    void btnBackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToMember_Index(mem);
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    public void initialize(Member memberDetails, String member){
        lbMember.setText(member);
        this.mem = member;
        
        dtUsername.setText(memberDetails.getUsername());
        dtFullname.setText(memberDetails.getFullname());
        dtIDcard.setText(memberDetails.getIdCard());
        dtGender.setText(memberDetails.getGender());
        dtPhone.setText(memberDetails.getPhone());
        dtEmail.setText(memberDetails.getEmail());
        dtAddress.setText(memberDetails.getAddress());
        int id = memberDetails.getRoleId();
        String a = findRoleNamebyID(id);
        dtRole.setText(a);
        
        System.out.println(memberDetails.getImg());
        String url = "file:/C:/Users/Admin/Documents/NetBeansProjects/project2_java/src/eProject2/image/"+memberDetails.getImg();
        System.out.println(url);
        images = new Image(url, dtImg.getFitWidth(), dtImg.getFitHeight(), true, true);
        dtImg.setImage(images);
        dtShift.setText(memberDetails.getShift());
    }
    
}
