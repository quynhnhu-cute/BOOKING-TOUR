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
import java.text.SimpleDateFormat;
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
@WebServlet(name = "ShowToursServlet", urlPatterns = {"/ShowToursServlet"})
public class ShowToursServlet extends HttpServlet {
    private final String SHOW_TOUR_PAGE = "showTour.jsp";
    private final String ERROR_PAGE = "error.html";
    private static final String USER_PAGE = "user.jsp";
    private static final String ADMIN_PAGE = "admin.jsp";
    
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ShowToursServlet.class);
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
        ToursDAO tours = new ToursDAO();
        HttpSession session = request.getSession(false);
        String url = SHOW_TOUR_PAGE;
        int numberPage = 1;
        try {
            List<ToursDTO> tourList;
            try {
                
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
//                LocalDateTime dateTo = LocalDateTime.from(date.toInstant().plusSeconds(24*60*60*30));
//                Date dateTo = new Date(date.getTime() + (20*1000*24*60*60));
                Date today = Date.valueOf(formatter.format(date));
//                Date finalDay = Date.valueOf(formatter.format(dateTo));
                request.setAttribute("TODAY", today);
//                request.setAttribute("DATETO", finalDay);
                
                int noOfRow = tours.countNumberOfList(today);
                int maxNumber = (int)Math.ceil((double)noOfRow/5);
                request.setAttribute("NUMBERMAX", maxNumber);
                String pageNum = request.getParameter("numberPage");
                if(pageNum != null){
                    numberPage = Integer.parseInt(pageNum.trim());
                }
                
                tourList = tours.getTourList(today, numberPage);
                
                if (tourList.size() > 0) {
                    request.setAttribute("TOURLIST", tourList);
                    String user = (String) session.getAttribute("USER");
                    String admin = (String) session.getAttribute("ADMIN");
                    if(user != null){
                        url = USER_PAGE;
                    }
                    if(admin != null){
                        url = ADMIN_PAGE;
                    }
                }else{
                    url = ERROR_PAGE;
                }
            } catch (SQLException ex) {
               LOGGER.fatal("ShowToursServlet_SQLException: " + ex.getMessage());
            } catch (NamingException ex) {
                LOGGER.fatal("ShowToursServlet_NamingException: " + ex.getMessage());
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
