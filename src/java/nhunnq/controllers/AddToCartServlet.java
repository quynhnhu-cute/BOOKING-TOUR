/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhunnq.beans.TourBean;
import nhunnq.carts.TourCart;

/**
 *
 * @author USER
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private static final String HOME_PAGE = "user.jsp";

    private static final String LOGIN_PAGE = "login.jsp";

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
        PrintWriter out = response.getWriter();
        String url = HOME_PAGE;
        int tourId = Integer.parseInt(request.getParameter("tourId").trim());
        String tourName = request.getParameter("tourName").trim();
        Date dateFrom = Date.valueOf(request.getParameter("dateFrom").trim());
        Date dateTo = Date.valueOf(request.getParameter("dateTo").trim());
        String priceShowing = request.getParameter("priceShowing").trim();
        try {
            HttpSession session = request.getSession(false);
            TourBean bean = new TourBean(tourName, dateFrom, dateTo, priceShowing, tourId);
            TourCart tourCart = (TourCart) session.getAttribute("TOUR_CART");
            if (tourCart == null) {
                tourCart = new TourCart();
            }
            String username = (String) session.getAttribute("FULLNAME");
            if (username == null) {
                url = LOGIN_PAGE;
            } else {
                Object role = session.getAttribute("USER");
                
                if (role == null) {
                    request.setAttribute("USERREQUIRE", "Admin can not add to cart!");
                    url = "admin.jsp";
                } else {
                    tourCart.setCustomer(username);
                    tourCart.addItemToCart(bean);
                    session.setAttribute("TOUR_CART", tourCart);
                    String discountCode = (String) session.getAttribute("DISCOUNTCODE");
                    if (discountCode != null) {
                        session.removeAttribute("DISCOUNTCODE");
                    }
                }

            }

        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
