/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.DiscountCode;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import nhunnq.utilities.DBUtilities;

/**
 *
 * @author USER
 */
public class DiscountCodeDAO implements Serializable {

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
    
    public int getDiscountCodePercentage(String discountCode)
            throws SQLException, NamingException{
        int percentage = -1;
        Date today = new Date(System.currentTimeMillis());
        String sql = "Select Percentage from DiscountCode where DiscountCode = ? and ExpiryDate >= ?";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setString(1, discountCode);
                ps.setDate(2, today);
                rs = ps.executeQuery();
                if(rs.next()){
                    percentage = rs.getInt("Percentage");
                }
            }
        } finally {
            closeConnection();
        }
        return percentage;
    }
}
