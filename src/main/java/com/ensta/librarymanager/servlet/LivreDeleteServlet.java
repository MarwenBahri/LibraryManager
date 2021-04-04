package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.LivreServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/livre_delete")
public class LivreDeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        try{
            if(req.getParameter("id")!=null){
                int id = Integer.valueOf(req.getParameter("id"));
                Livre livre = livreService.getById(id);
                req.setAttribute("livre",livre);
            }
            req.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp").forward(req,resp);
        }catch (NumberFormatException e){
            throw new ServletException("failed to get book id -servlet");
        }catch (ServiceException e){
            throw new ServletException("selecting book failed -servlet");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        try{
            int id;
            if(req.getParameter("id")!=null)id = Integer.valueOf(req.getParameter("id"));
            else throw new ServletException("book id missing");
            livreService.delete(id);
            resp.sendRedirect("/LibraryManager/livre_list");
        }catch (ServiceException e){
            throw new ServletException("deleting book failed");
        }
    }
}
