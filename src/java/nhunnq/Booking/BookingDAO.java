/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.Booking;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import nhunnq.utilities.DBUtilities;

/**
 *
 * @author USER
 */
public class BookingDAO implements Serializable{
    
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
    
    public boolean insertBooking(BookingDTO booking)
            throws SQLException, NamingException{
        String sql = "Insert into Booking(DateOrder, TotalMoney, StatusID, UserId) values (?,?,?,?)";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setTimestamp(1, booking.getOrderDate());
                ps.setDouble(2, booking.getTotalMoney());
                ps.setInt(3, booking.isStatus());
                ps.setString(4, booking.getUserId());
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
    
    public int getBookingIdByTime(Timestamp time)
            throws SQLException, NamingException{
        String sql = "Select BookingId from Booking where DateOrder = ?";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setTimestamp(1, time);
                rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getInt("BookingId");
                }              
            }
        } finally {
            closeConnection();
        }
        return 0;
    }
    
    
}
