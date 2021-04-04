package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.MembreServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/membre_add")
public class MembreAddServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/View/membre_add.jsp").forward(req,resp);

    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        try{
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String adresse = req.getParameter("adresse");
            String tel = req.getParameter("telephone");
            String email = req.getParameter("email");
            int id = membreService.create(nom,prenom,adresse,email,tel);
            resp.sendRedirect("/LibraryManager/membre_details?id="+id);
        }catch (ServiceException e){
            throw new ServletException("creating member failed");
        }
    }
}
