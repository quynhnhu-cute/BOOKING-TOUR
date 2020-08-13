/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.controllers;

import java.io.IOException;
import java.util.Map;
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
@WebServlet(name = "UpdateAmountServlet", urlPatterns = {"/UpdateAmountServlet"})
public class UpdateAmountServlet extends HttpServlet {
    
    private static final String VIEW_CART = "viewCart.jsp";
    private static final String PROCESS_DISCOUNT = "processDiscount";

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

        int tourId = Integer.parseInt(request.getParameter("tourId").trim());
        String number = request.getParameter("txtQuanity");
        int amount = 1;
        if (number.matches("\\d{1,3}")) {
            amount = Integer.parseInt(number.trim());
        }
        String url = VIEW_CART;
        try {
            HttpSession session = request.getSession();
            TourCart tourCart = (TourCart) session.getAttribute("TOUR_CART");
            Map<TourBean, Integer> items = tourCart.getItems();
            for (TourBean bean : items.keySet()) {
                if (bean.getTourId() == tourId) {
                    items.replace(bean, items.get(bean), amount);
                }
            }
            tourCart.calculatePrice(0);
            session.setAttribute("TOUR_CART", tourCart);
            String discountCode = (String) session.getAttribute("DISCOUNTCODE");
            if (discountCode != null) {
                url = PROCESS_DISCOUNT;
            }

        } finally {
            response.sendRedirect(url);
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
