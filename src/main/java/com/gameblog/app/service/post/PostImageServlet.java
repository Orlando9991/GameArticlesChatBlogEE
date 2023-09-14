/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.gameblog.app.service.post;

import com.gameblog.app.model.Post;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author orlan
 */
@WebServlet(name = "GetPostImage", urlPatterns = {"/getpostimage"})
public class PostImageServlet extends HttpServlet {
    
    @Inject
    PostFacade postFacade;
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
        
        String indexString = request.getParameter("photo");
        long index = Integer.parseInt(indexString.trim());
        response.setContentType("image/jpeg");
        
       
       
       
        try (OutputStream out = response.getOutputStream()) {
            Post post = postFacade.findById(index).get();
            byte[] bytes = post.getImage();
            for (int i = 0; i < bytes.length; i++) {
                out.write(bytes[i]);
            }
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
    

 
}
