/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nhunnq.Tours.ToursDAO;
import nhunnq.Tours.ToursDTO;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author USER
 */
@WebServlet(name = "InsertToursServlet", urlPatterns = {"/InsertToursServlet"})
@MultipartConfig
public class InsertToursServlet extends HttpServlet {

    private static final String SUCCESS_PAGE = "admin.jsp";
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(InsertToursServlet.class);
    private final String ERROR_PAGE = "error.html";

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
        String url = ERROR_PAGE;
        PrintWriter out = response.getWriter();

        try {
            String name = null;
            String dateFromm = null;
            String dateToo = null;
            String prices = null;
            String quotas = null;
            String place = null;
            String imgLink = null;
            if (!ServletFileUpload.isMultipartContent(request)) {
                out.println("Nothing to upload");
                return;
            }
            FileItemFactory itemFactory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(itemFactory);
            ToursDAO tours = new ToursDAO();
            try {
                List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
                Iterator iter = items.iterator();
                Hashtable params = new Hashtable();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        params.put(item.getFieldName(), item.getString());
                    }
                }
                name = (String) params.get("txtName");

                dateFromm = (String) params.get("txtDateFrom");

                Date dateFrom = Date.valueOf(dateFromm.trim());
                dateToo = (String) params.get("txtDateTo");

                Date dateTo = Date.valueOf(dateToo.trim());
                prices = (String) params.get("txtPrice");

                double price = Double.parseDouble(prices.trim());
                quotas = (String) params.get("txtQuota");

                int quota = Integer.parseInt(quotas.trim());
                place = (String) params.get("txtPlace");
                Date today = new Date(System.currentTimeMillis());
                for (FileItem itemm : items) {
                    String recentFile = request.getServletContext().getRealPath("/");
                    File uploadDir = new File(recentFile + "/images");
                    File file = File.createTempFile("img", ".jpg", uploadDir);
                    try {
                        itemm.write(file);
                    } catch (Exception ex) {
                        LOGGER.fatal("InsertToursServlet_FileUploadException: " + ex.getMessage());
                    }
                    String fileName = String.valueOf(file);
                   
                    String imgName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                    imgLink = "./images/" + imgName;

                }
                ToursDTO tour = new ToursDTO(quota, name, imgLink, place, dateFrom, dateTo, today, price);
                boolean insert = tours.insertTours(tour);
                if (insert) {
                    url = SUCCESS_PAGE;
                }
            } catch (FileUploadException ex) {
                LOGGER.fatal("InsertToursServlet_FileUploadException: " + ex.getMessage());
            } catch (SQLException ex) {
                LOGGER.fatal("InsertToursServlet_SQLException: " + ex.getMessage());
            } catch (NamingException ex) {
                LOGGER.fatal("InsertToursServlet_NamingException: " + ex.getMessage());
            } finally {
                response.sendRedirect(url);
                out.close();
            }
        } catch (Exception ex) {
            LOGGER.fatal("InsertTourServlet_Exception: " + ex.getMessage());
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
