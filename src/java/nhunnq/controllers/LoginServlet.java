/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhunnq.Users.UsersDAO;
import nhunnq.Users.UsersDTO;

/**
 *
 * @author USER
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(LoginServlet.class);
    private final String ERROR_PAGE = "login.jsp";
    private final String ADMIN_PAGE = "admin.jsp";
    private final String USER_PAGE = "user.jsp";
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
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String url = ERROR_PAGE;
        UsersDAO users = new UsersDAO();
        boolean validLogin = false;
        try{
            try {
                UsersDTO user = users.checkLogin(username, password);
                if(user != null){
                    HttpSession session = request.getSession();
                    session.setAttribute("USERNAME", user.getUsername());
                    session.setAttribute("FULLNAME", user.getFullname());
                    String role = user.getRolename();
                    if(role.equalsIgnoreCase("Admin")){
                        url = ADMIN_PAGE;
                        session.setAttribute("ADMIN", role);
                    }else if(role.equalsIgnoreCase("User")){
                        url = USER_PAGE;
                        session.setAttribute("USER", role);
                    }
                    validLogin = true;
                }else{
                    request.setAttribute("LOGINERROR", "Username or password is not correct!");
                }
            } catch (SQLException ex) {
               LOGGER.fatal("LoginServlet_SQLException: " + ex.getMessage());
            } catch (NamingException ex) {
                LOGGER.fatal("LoginServlet_NamingException: " + ex.getMessage());
            }
        }finally{
            if(validLogin){
                response.sendRedirect(url);
            }else{
                request.getRequestDispatcher(url).forward(request, response);
            }
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
