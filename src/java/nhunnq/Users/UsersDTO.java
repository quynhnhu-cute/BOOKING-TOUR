/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.Users;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 *
 * @author USER
 */
public class UsersDTO implements Serializable{
    
    //vi cai response là name va id
    @SerializedName(value = "id")
    private String facebookID;
    private String username, password;
    @SerializedName(value = "name")
    private String fullname;
    private int roleId;
    private String rolename;

    

    
    public UsersDTO(String fullname, String rolename) {
        this.fullname = fullname;
        this.rolename = rolename;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    
    

    public UsersDTO() {
    }

    public UsersDTO(String username, String password, String fullname, String rolename) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.rolename = rolename;
    }

    public UsersDTO(String username, String password, String fullname, int roleId) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.roleId = roleId;
    }
    public UsersDTO(String username, String facebookID, String fullname, int roleId, String password) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.roleId = roleId;
        this.facebookID = facebookID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }
    
}
