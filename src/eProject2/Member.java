/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class Member {
    private ObjectProperty<Integer> id;
    private StringProperty idcard;
    private StringProperty username;
    private StringProperty fullname;
    private StringProperty password;
    private StringProperty gender;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty address;
    private ObjectProperty<Integer> roleID;
    private StringProperty img;
    private StringProperty shift;
    
    public Member(){
        id = new SimpleObjectProperty<>(null);
        idcard = new SimpleStringProperty();
        username = new SimpleStringProperty();
        fullname = new SimpleStringProperty();
        password = new SimpleStringProperty();
        gender = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        email = new SimpleStringProperty();
        address = new SimpleStringProperty();
        roleID = new SimpleObjectProperty<>(0);
        img = new SimpleStringProperty();
        shift = new SimpleStringProperty();
    }
    
    public Integer getId() {
        return id.get();
    }
    public String getIdCard() {
        return idcard.get();
    }
    public String getUsername() {
        return username.get();
    }
    public String getFullname() {
        return fullname.get();
    }
    public String getPassword() {
        return password.get();
    }
    public String getGender() {
        return gender.get();
    }
    public String getPhone() {
        return phone.get();
    }
    public String getEmail() {
        return email.get();
    }
    public String getAddress() {
        return address.get();
    }
    public Integer getRoleId() {
        return roleID.get();
    }
    public String getImg() {
        return img.get();
    }
    public String getShift() {
        return shift.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    public void setIdCard(String idcard){
        this.idcard.set(idcard);
    }
    public void setUsername(String username){
        this.username.set(username);
    }
    public void setFullname(String fullname){
        this.fullname.set(fullname);
    }
    public void setPassword(String password){
        this.password.set(password);
    }
    public void setGender(String gender){
        this.gender.set(gender);
    }
    public void setPhone(String phone){
        this.phone.set(phone);
    }
    public void setEmail(String email){
        this.email.set(email);
    }
    public void setAddress(String address){
        this.address.set(address);
    }
    public void setRoleId(int roleid){
        this.roleID.set(roleid);
    }
    public void setImg(String img){
        this.img.set(img);
    }
    public void setShift(String shift){
        this.shift.set(shift);
    }
    
    public ObjectProperty<Integer> getIdProperty(){
        return this.id;
    }
    public StringProperty getIdCardProperty(){
        return this.idcard;
    }
    public StringProperty getUsernameProperty(){
        return this.username;
    }
    public StringProperty getFullnameProperty(){
        return this.fullname;
    }
    public StringProperty getPasswordProperty(){
        return this.password;
    }
    public StringProperty getGenderProperty(){
        return this.gender;
    }
    public StringProperty getPhoneProperty(){
        return this.phone;
    }
    public StringProperty getEmailProperty(){
        return this.email;
    }
    public StringProperty getAddressProperty(){
        return this.address;
    }
    public ObjectProperty<Integer> getRoleIdProperty(){
        return this.roleID;
    }
    public StringProperty getImgProperty(){
        return this.img;
    }
    public StringProperty getShiftProperty(){
        return this.shift;
    }
    
    public static ObservableList<Member> SearchMeber(String mName) {
        ObservableList<Member> members = FXCollections.observableArrayList();
//        String sql = "SELECT * FROM product WHERE name LIKE '%em%'";
        try (
//                Connection conn = DbService.getConnection();
//                PreparedStatement stmt = conn.prepareStatement(sql);
//                ) {
//                stmt.setString(1, pName);
//                ResultSet rs = stmt.executeQuery();
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM member WHERE fullname LIKE '%"+mName+"%'");) {
            while (rs.next()) {
                Member p = new Member();
                p.setId(rs.getInt("memberID"));
                p.setIdCard(rs.getString("IDcard"));
                p.setUsername(rs.getString("username"));
                p.setFullname(rs.getString("fullname"));
                p.setPassword(rs.getString("password"));
                p.setGender(rs.getString("gender"));
                p.setEmail(rs.getString("email"));
                p.setAddress(rs.getString("address"));
                p.setRoleId(rs.getInt("roleID"));
                p.setImg(rs.getString("img"));
                p.setShift(rs.getString("shift"));
                p.setPhone(rs.getString("phone"));
                
                
                members.add(p);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return members;
    }
    
    public static ObservableList<Member> selectAll() {
        ObservableList<Member> members = FXCollections.observableArrayList();
        
        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM member");) {
//            logger.info("Database connected!");
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
//            logger.error(e);
        }
        return members;
    }
    
    public static boolean delete(Member deleteMember){
        String sql = "DELETE FROM member WHERE memberID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setInt(1, deleteMember.getId());
                        
            int rowDeleted = stmt.executeUpdate();
            
            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No member deleted");
                return false;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    public static Member insert(Member newMember) throws SQLException{
        String sql = "INSERT into member (IDcard, username, fullname, `password`, gender, phone, email, address, roleID, img, shift)"
                + "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet key = null;
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = 
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            
            stmt.setString(1, newMember.getIdCard());
            stmt.setString(2, newMember.getUsername());
            stmt.setString(3, newMember.getFullname());
            stmt.setString(4, newMember.getPassword());
            stmt.setString(5, newMember.getGender());
            stmt.setString(6, newMember.getPhone());
            stmt.setString(7, newMember.getEmail());
            stmt.setString(8, newMember.getAddress());
            stmt.setInt(9, newMember.getRoleId());
            stmt.setString(10, newMember.getImg());
            stmt.setString(11, newMember.getShift());
            
            int rowInserted = stmt.executeUpdate();
            
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newMember.setId(newKey);
                return newMember;
            } else {
                System.err.println("No member inserted");
                return null;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
            if (key != null) {
                key.close();
            }
        }
    }
    
    public static boolean update(Member updateMember) {
        String sql = "UPDATE member SET " 
                + "IDcard = ?, "
                + "username = ?, "
                + "fullname = ?, "
                + "password = ?, "
                + "gender = ?, "
                + "phone = ?, "
                + "email = ?, "
                + "address = ?, "
                + "roleID = ?, "
                + "img = ?, "
                + "shift = ? "
                + "WHERE memberID = ?";
        try (
            Connection conn = DbService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            
            stmt.setString(1, updateMember.getIdCard());
            stmt.setString(2, updateMember.getUsername());
            stmt.setString(3, updateMember.getFullname());
            stmt.setString(4, updateMember.getPassword());
            stmt.setString(5, updateMember.getGender());
            stmt.setString(6, updateMember.getPhone());
            stmt.setString(7, updateMember.getEmail());
            stmt.setString(8, updateMember.getAddress());
            stmt.setInt(9, updateMember.getRoleId());
            stmt.setString(10, updateMember.getImg());
            stmt.setString(11, updateMember.getShift());
            stmt.setInt(12, updateMember.getId());
            
            int rowUpdated = stmt.executeUpdate();
            
            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No member updated");
                return false;
            }
            
            
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
}
