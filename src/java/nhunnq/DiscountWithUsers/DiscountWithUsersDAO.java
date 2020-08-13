/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.DiscountWithUsers;

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
public class DiscountWithUsersDAO implements Serializable{
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    private void closeConnection()
            throws SQLException, NamingException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if(conn != null){
            ps.close();
        }
    }
    
    public boolean getDiscountCodeByUser(String discountCode, String userId)
            throws SQLException, NamingException{
        String sql = "Select DiscountId from DiscountWithUsers where DiscountId = ? and UserId = ?";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setString(1, discountCode);
                ps.setString(2, userId);
                rs= ps.executeQuery();
                if(rs.next()){
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public boolean insertDiscountWithUsers(DiscountWithUsersDTO disUser)
            throws SQLException, NamingException{
        String sql = "Insert into DiscountWithUsers(UserId,DiscountId) values (?,?)";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setString(1, disUser.getUserId());
                ps.setString(2, disUser.getDiscountCode());              
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
}
