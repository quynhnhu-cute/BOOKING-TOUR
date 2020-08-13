/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.BookingDetails;

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
public class BookingDetailsDAO implements Serializable{
     private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private void closeConnection()
            throws SQLException, NamingException{
        if(rs != null){
            rs.close();
        }
        if(ps != null){
            ps.close();
        }
        if(conn != null){
            conn.close();
        }
    }
    
    public boolean insertBookingDetails(BookingDetailsDTO detail)
            throws SQLException, NamingException{
        String sql = "Insert into BookingDetails(TourId, Quanity, BookingId) values (?,?,?)";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setInt(1, detail.getTourId());
                ps.setInt(2, detail.getQuanity());
                ps.setInt(3, detail.getBookingId());               
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
