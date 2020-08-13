/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhunnq.Users.UsersDAO;
import nhunnq.Users.UsersDTO;
import nhunnq.wrapper.APIWrapper;

/**
 *
 * @author USER
 */
@WebServlet(name = "LoginFacebookServlet", urlPatterns = {"/LoginFacebookServlet"})
public class LoginFacebookServlet extends HttpServlet {

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
         UsersDAO users = new UsersDAO();
        try {
            String code = request.getParameter("code");
            APIWrapper wrapper = new APIWrapper();
                String accessToken = wrapper.getAcessToken(code);
            wrapper.setAccessToken(accessToken);
            UsersDTO user = wrapper.getUserDTO();
            try {
                boolean userExist = users.checkLogin(user.getFacebookID()) != null;
               String username = null;
                if(!userExist){
                    //username bi null nen bi sql exception. t
                    username = String.valueOf(users.countUser());
                    users.register(user.getFullname(), user.getFacebookID().trim(), username);
                }
                HttpSession session = request.getSession();
                session.setAttribute("USER", user);
                session.setAttribute("FULLNAME", user.getFullname());
                session.setAttribute("USERNAME", user.getUsername());
                
            } catch (NamingException ex) {
                Logger.getLogger(LoginFacebookServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(LoginFacebookServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            request.getRequestDispatcher("user.jsp").forward(request, response);
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
