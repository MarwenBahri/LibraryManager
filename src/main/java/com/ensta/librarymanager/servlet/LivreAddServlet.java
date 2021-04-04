package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.LivreServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/View/livre_add.jsp").forward(req,resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        try{
            String titre = req.getParameter("titre");
            String auteur = req.getParameter("auteur");
            String isbn = req.getParameter("isbn");
            int id = livreService.create(titre,auteur,isbn);
            resp.sendRedirect("/LibraryManager/livre_details?id="+id);
        }catch (ServiceException e){
            throw new ServletException("creating book failed");
        }
    }
}
