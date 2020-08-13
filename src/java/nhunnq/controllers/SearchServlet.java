/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhunnq.Tours.ToursDAO;
import nhunnq.Tours.ToursDTO;

/**
 *
 * @author USER
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchServlet.class);
    private final String SHOW_TOUR_SERVLET = "showTour.jsp";
    private static final String USER_PAGE = "user.jsp";
    private static final String ADMIN_PAGE = "admin.jsp";

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
        HttpSession session = request.getSession(false);
        String url = SHOW_TOUR_SERVLET;
        ToursDAO tours = new ToursDAO();
        String place = null;
        Date fromDate = null;
        Date toDate = null;
        double fromPrice, toPrice;

        int pageNum = 1;
        try {

            try {

                if (request.getParameter("txtPageNum") != null) {
                    pageNum = Integer.parseInt(request.getParameter("txtPageNum").trim());
                    place = (String) request.getParameter("place");

                    fromDate = Date.valueOf((String) request.getParameter("dateFrom").trim());
                    toDate = Date.valueOf((String) request.getParameter("dateTo").trim());

                    fromPrice = Double.parseDouble(request.getParameter("priceFrom").replace(" ", "").trim());
                    toPrice = Double.parseDouble(request.getParameter("priceTo").replace(" ", "").trim());
                } else {
                    place = request.getParameter("txtPlace");
                    String dateFrom = request.getParameter("txtDateFrom");
                    String dateTo = request.getParameter("txtDateTo");
                    String priceFrom = request.getParameter("txtPriceFrom");
                    String priceTo = request.getParameter("txtPriceTo");

                    if (!dateFrom.isEmpty()) {
                        fromDate = Date.valueOf(dateFrom.trim());
                    }

                    if (!dateTo.isEmpty()) {
                        toDate = Date.valueOf(dateTo.trim());
                    }

                    priceFrom = priceFrom.replace(" ", "");
                    fromPrice = Double.parseDouble(priceFrom.trim());

                    priceTo = priceTo.replace(" ", "");
                    toPrice = Double.parseDouble(priceTo.trim());
                }
                Date today =  new Date(System.currentTimeMillis());
                request.setAttribute("PLACE", place);
                request.setAttribute("DATEFROM", fromDate);
                request.setAttribute("DATETO", toDate);
                request.setAttribute("FROMPRICE", fromPrice);
                request.setAttribute("TOPRICE", toPrice);
                int number = tours.countSearchList(place, fromDate, toDate, toPrice, fromPrice,today);
                int pageNumber = (int) Math.ceil((double) number / 5);
                request.setAttribute("NUMBER", pageNumber);
                List<ToursDTO> tourList = tours.getSearchList(place, fromDate, toDate, toPrice, fromPrice, pageNum,today);
                if (tourList.size() > 0) {
                    request.setAttribute("SEARCHLIST", tourList);
                    String user = (String) session.getAttribute("USER");
                    String admin = (String) session.getAttribute("ADMIN");
                    if (user != null) {
                        url = USER_PAGE;
                    }
                    if (admin != null) {
                        url = ADMIN_PAGE;
                    }
                } else {
                    request.setAttribute("EMPTYSEARCH", "There is no matching result!");
                }
            } catch (SQLException ex) {
                LOGGER.fatal("SearchServlet_SQLException: " + ex.getMessage());
            } catch (NamingException ex) {
                LOGGER.fatal("SearchServlet_SQLException: " + ex.getMessage());
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
