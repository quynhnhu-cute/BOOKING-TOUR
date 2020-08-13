/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhunnq.Booking.BookingDAO;
import nhunnq.Booking.BookingDTO;
import nhunnq.BookingDetails.BookingDetailsDAO;
import nhunnq.BookingDetails.BookingDetailsDTO;
import nhunnq.DiscountWithUsers.DiscountWithUsersDAO;
import nhunnq.DiscountWithUsers.DiscountWithUsersDTO;
import nhunnq.Tours.ToursDAO;
import nhunnq.beans.TourBean;
import nhunnq.carts.TourCart;

/**
 *
 * @author USER
 */
@WebServlet(name = "BookingTourServlet", urlPatterns = {"/BookingTourServlet"})
public class BookingTourServlet extends HttpServlet {
    
    private static final String USER_PAGE = "user.jsp";
    private static final String CART_PAGE = "viewCart.jsp";
    private static final String ERROR_PAGE = "error.html";
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(BookingTourServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("USERNAME");
        if(username == null){
            username = (String) session.getAttribute("FULLNAME");
        }
        
        String url = ERROR_PAGE;
        BookingDAO bookings = new BookingDAO();
        BookingDetailsDAO bookingDetails = new BookingDetailsDAO();
        DiscountWithUsersDAO disUsers = new DiscountWithUsersDAO();
        ToursDAO tours = new ToursDAO();
        boolean testCondition = true;
        try {
            if (username == null) {
                request.setAttribute("NOACCOUNT", "You need to login to use this function");
                url = CART_PAGE;
            } else {
                TourCart cart = (TourCart) session.getAttribute("TOUR_CART");
                for (TourBean bean : cart.getItems().keySet()) {
                    if (cart.getItems().get(bean) > tours.getQuotaByTourId(bean.getTourId())) {
                        testCondition = false;
                    }
                }
                if (testCondition) {
                    boolean insertDetails = false;
                    boolean insertDiscount = false;
                    
                    double totalMoney = Double.parseDouble(request.getParameter("price").trim());
                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    BookingDTO booking = new BookingDTO(currentTime, totalMoney, username, 4);
                    boolean insertBooking = bookings.insertBooking(booking);
                    int bookingId = bookings.getBookingIdByTime(currentTime);
                    boolean updateQuota = false;
                    for (TourBean tourBean : cart.getItems().keySet()) {
                        BookingDetailsDTO detail = new BookingDetailsDTO(tourBean.getTourId(), cart.getItems().get(tourBean), bookingId);
                        insertDetails = bookingDetails.insertBookingDetails(detail);
                        int  quanity = tours.getQuotaByTourId(tourBean.getTourId()) - cart.getItems().get(tourBean);
                        updateQuota = tours.updateQuota(quanity, tourBean.getTourId());
                    }
                   
                    String discountCode = (String) session.getAttribute("DISCOUNTCODE");
                    if (discountCode != null) {
                        DiscountWithUsersDTO dto = new DiscountWithUsersDTO(username, discountCode);
                        insertDiscount = disUsers.insertDiscountWithUsers(dto);
                    }
                    if (insertBooking == true && insertDetails == true && updateQuota == true) {
                        session.removeAttribute("TOUR_CART");
                        session.removeAttribute("DISCOUNTCODE");
                        url = USER_PAGE;                        
                    }else{
                        url = ERROR_PAGE;
                    }
                } else {
                    request.setAttribute("NOT_ENOUGH_TOUR", "There is not enougth tour to book!");
                    url = CART_PAGE;
                }
                
            }
        } catch (SQLException ex) {
            LOGGER.fatal("BookingTourServlet_SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.fatal("BookingTourServlet_NamingException: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
