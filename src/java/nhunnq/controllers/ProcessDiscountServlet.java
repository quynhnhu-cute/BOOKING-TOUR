/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhunnq.DiscountCode.DiscountCodeDAO;
import nhunnq.DiscountWithUsers.DiscountWithUsersDAO;
import nhunnq.carts.TourCart;

/**
 *
 * @author USER
 */
@WebServlet(name = "ProcessDiscountServlet", urlPatterns = {"/ProcessDiscountServlet"})
public class ProcessDiscountServlet extends HttpServlet {

    private static final String SHOW_CART = "viewCart.jsp";
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ProcessDiscountServlet.class);
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
        String discountCode = request.getParameter("txtDisocuntCode");
         HttpSession session = request.getSession(false);
        if(discountCode == null){
            discountCode = (String)session.getAttribute("DISCOUNTCODE");
        }
        String url = SHOW_CART;
        DiscountWithUsersDAO discountUsers = new DiscountWithUsersDAO();
        DiscountCodeDAO dao = new DiscountCodeDAO();
        try {
            if (discountCode != null) {
                discountCode = discountCode.trim().toUpperCase();
               
                String userId = (String) session.getAttribute("FULLNAME");
                if (userId != null) {
                    try {
                        int percentage = dao.getDiscountCodePercentage(discountCode);
                        if (percentage > 0) {
                            boolean exist = discountUsers.getDiscountCodeByUser(discountCode, userId);
                            if (!exist) {
                               
                                TourCart tourCart = (TourCart) session.getAttribute("TOUR_CART");
                                tourCart.calculatePrice(percentage);
                                session.setAttribute("TOUR_CART", tourCart);
                                session.setAttribute("DISCOUNTCODE", discountCode);
                               
                            } else {
                                request.setAttribute("ALREADYUSED", "This code is already used!");
                            }

                        } else {
                            request.setAttribute("NOCODE", "There is no suitable discount code");
                        }

                    } catch (SQLException ex) {
                       LOGGER.fatal("ProcessDiscountServlet_SQLException: " + ex.getMessage());
                    } catch (NamingException ex) {
                        LOGGER.fatal("ProcessDiscountServlet_NamingException: " + ex.getMessage());
                    }
                } 
            }

        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
