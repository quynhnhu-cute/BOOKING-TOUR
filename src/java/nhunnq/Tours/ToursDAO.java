/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.Tours;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import nhunnq.utilities.DBUtilities;

/**
 *
 * @author USER
 */
public class ToursDAO implements Serializable{
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public ToursDAO() {
    }

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
    
    public String formatPrice(double price){
        DecimalFormat formater = new DecimalFormat("### ### ###");
        return formater.format(price);
    }
    
    public List<ToursDTO> getTourList(Date today, int pageNumber)
            throws SQLException, NamingException{
        List<ToursDTO> tourList = new ArrayList<>();
        String sql = "Select TourId, TourName, DateFrom, DateTo, Price, DateImport, Quota,ImageLink, Place from Tours where StatusId = 1 and DateFrom > ?"
                + " ORDER BY DateFrom\n" +
                  " OFFSET 5 * (?-1)ROWS\n" +
                  " FETCH NEXT 5 ROWS ONLY";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setDate(1, today);
                ps.setInt(2, pageNumber);
                rs = ps.executeQuery();
                while(rs.next()){
                    int tourId = rs.getInt("TourId");
                    String tourName = rs.getString("TourName");
                    Date dateFrom = rs.getDate("DateFrom");
                    Date dateTo = rs.getDate("DateTo");
                    double price = rs.getDouble("Price");
                    Date importDate = rs.getDate("DateImport");
                    int quota = rs.getInt("Quota");
                    String image = rs.getString("ImageLink");
                    String place = rs.getString("Place");
                    String priceShowing = formatPrice(price);
                    ToursDTO tour = new ToursDTO(tourId, quota, tourName, image, place, dateFrom, dateTo, importDate, price, priceShowing);
                    tourList.add(tour);
                }
            }
        } finally {
            closeConnection();
        }
        return tourList;
    }
    
    public int countNumberOfList(Date today)
            throws NamingException, SQLException{
        int row = 0;
        String sql = "Select count(TourId) as NoOfTour from Tours where StatusId = 1 and DateFrom > ?";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setDate(1, today);
                
                rs = ps.executeQuery();
                if(rs.next()){
                    row = rs.getInt("NoOfTour");
                }
            }
        } finally {
            closeConnection();
        }
        return row;
    }
    
    public int countSearchList(String place, Date dateFrom, Date dateTo, double priceTo, double priceFrom, Date today)
            throws SQLException, NamingException{
        int row = 0;
        String sql = "SELECT DISTINCT COUNT(TourId) AS NoOfTours FROM dbo.Tours where StatusId = 1 and DateFrom > ?" +
                        " AND DateFrom >= ?" +
                        " AND DateTo <= ?" +
                        " AND Price >= ?" +
                        " AND Price <= ?" +
                        " AND Place LIKE ?";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setDate(1, today);
                ps.setDate(2, dateFrom);
                ps.setDate(3, dateTo);
                ps.setDouble(4, priceFrom);
                ps.setDouble(5, priceTo);
                ps.setString(6, "%" + place + "%");
                rs = ps.executeQuery();
                if(rs.next()){
                    row = rs.getInt("NoOfTours");
                }
            }
        } finally {
            closeConnection();
        }
        return row;
    }
      
    public List<ToursDTO> getSearchList(String place, Date dateFrom, Date dateTo, double priceTo, double priceFrom, int pageNumber, Date today)
            throws SQLException, NamingException{
        List<ToursDTO> tourList = new ArrayList<>();
        String sql = "Select TourId, TourName, DateFrom, DateTo, Price, DateImport, Quota, ImageLink, Place from Tours where StatusId = 1"
                        + " and DateFrom > ?" +
                        " AND DateFrom >= ?" +
                        " AND DateTo <= ?" +
                        " AND Price >= ?" +
                        " AND Price <= ?" +
                        " AND Place LIKE ?" +
                        " ORDER BY TourId" +
                        " OFFSET 5 *(?-1)ROWS" +
                        " FETCH NEXT 5 ROWS ONLY";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setDate(1, today);
                ps.setDate(2, dateFrom);
                ps.setDate(3, dateTo);
                ps.setDouble(4, priceFrom);
                ps.setDouble(5, priceTo);
                ps.setString(6, "%" + place + "%");
                ps.setInt(7, pageNumber);
                rs = ps.executeQuery();
                while(rs.next()){
                    int tourId = rs.getInt("TourId");
                    String tourName = rs.getString("TourName");
                    Date fromDate = rs.getDate("DateFrom");
                    Date toDate = rs.getDate("DateTo");
                    double price = rs.getDouble("Price");
                    Date importDate = rs.getDate("DateImport");
                    int quota = rs.getInt("Quota");
                    String image = rs.getString("ImageLink");
                    String places = rs.getString("Place");
                    String priceShowing = formatPrice(price);
                    ToursDTO tour = new ToursDTO(tourId, quota, tourName, image, places, fromDate, toDate, importDate, price, priceShowing);
                    tourList.add(tour);
                }
            }
        } finally {
            closeConnection();
        }
        return tourList;
    }
      
    public boolean insertTours(ToursDTO tour)
            throws SQLException, NamingException{
        String sql = "Insert Tours(TourName, DateFrom, DateTo, Price, ImageLink, DateImport, Quota, Place, StatusId) values(?,?,?,?,?,?,?,?,1)";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setString(1, tour.getTourName());
                ps.setDate(2, tour.getDateFrom());
                ps.setDate(3, tour.getDateTo());
                ps.setDouble(4, tour.getPrice());
                ps.setString(5, tour.getImageLink());
                ps.setDate(6, tour.getImportDate());
                ps.setInt(7, tour.getQuota());
                ps.setString(8, tour.getPlace());
                int rowAffect = ps.executeUpdate();
                if(rowAffect > 0){
                    return true;
                }
            }
            return false;
        } finally {
            closeConnection();
        }
    }
    
    public int getQuotaByTourId(int tourId)
            throws SQLException, NamingException{
        String sql = "Select Quota from Tours where TourId = ?";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setInt(1, tourId);
                rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getInt("Quota");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }
    
    public boolean updateQuota(int quanity, int tourId)
            throws SQLException, NamingException{
        String sql = "Update Tours set Quota = ? where TourId = ?";
        try {
            conn = DBUtilities.makeConnection();
            if(conn != null){
                ps = conn.prepareStatement(sql);
                ps.setInt(1, quanity);
                ps.setInt(2, tourId);
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
