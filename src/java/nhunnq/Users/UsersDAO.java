/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.Users;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import nhunnq.utilities.DBUtilities;

/**
 *
 * @author USER
 */
public class UsersDAO implements Serializable{
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private void closeConnection()
            throws SQLException, NamingException{
        if(conn != null){
            conn.close();
        }
        if(ps != null){
            ps.close();
        }
        if(rs != null){
            rs.close();
        }
    }
    
    public UsersDTO checkLogin(String username, String password)
            throws SQLException, NamingException{
        UsersDTO user = null;
        String sql = "SELECT R.RoleName, Fullname\n" +
                     " FROM dbo.Role R , dbo.Users U\n" +
                     " WHERE U.RoleId = R.RoleId "
                    + "AND Username = ? And Password = ? AND StatusId = 1";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if(rs.next()){
                    String role = rs.getString("RoleName");
                    String fullname = rs.getString("Fullname");
                   
                    user = new UsersDTO(username, password, fullname, role);
                }
            }
        } finally {
            closeConnection();
        }
        return user;
    }
    
    public boolean register(String fullname, String facebookID, String username)
            throws SQLException, NamingException{
        String sql = "Insert into Users(FullName,RoleId,StatusId,FaceBookId, Username) values (?,?,?,?,?)";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                
                ps.setString(1, fullname);
                ps.setInt(2, 2);
                ps.setInt(3, 1);
                ps.setString(4, facebookID);
                ps.setString(5, username);
                
                int row = ps.executeUpdate();
                if(row > 0){
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public UsersDTO checkLogin(String facebookId)
        throws NamingException, SQLException{
        UsersDTO user = null;
        String sql = "Select UserName, FullName, FacebookId, RoleId from Users where FacebookID = ? and StatusId = 1";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setString(1, facebookId);
                rs = ps.executeQuery();
                if(rs.next()){
                    String username = rs.getString("UserName");
                    String fullName = rs.getString("FullName");
                    String faceId = rs.getString("FacebookId");
                    int roleId = rs.getInt("RoleId");
                    user = new UsersDTO();
                    user.setFacebookID(faceId);
                    user.setFullname(fullName);
                    user.setRoleId(roleId);
                    user.setUsername(username);
                    
                     
                }
            }
        } finally {
            closeConnection();
        }
        return user;
    }
    
    public int countUser() throws SQLException, NamingException{
        String sql = "Select Count(UserName) as NoOfUsers from Users WHERE Username IS NOT NULL";
        try {
            conn = DBUtilities.makeConnection();
            ps = conn.prepareStatement(sql);
            if(conn != null){
                rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getInt("NoOfUsers");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }
}
