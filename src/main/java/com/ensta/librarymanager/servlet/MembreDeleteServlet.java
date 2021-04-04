package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.MembreServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/membre_delete")
public class MembreDeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        try{
            if(req.getParameter("id")!=null){
                int id = Integer.valueOf(req.getParameter("id"));
                Membre membre = membreService.getById(id);
                req.setAttribute("membre",membre);
            }
            req.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp").forward(req,resp);
        }catch (NumberFormatException e){
            throw new ServletException("failed to get member id");
        }catch (ServiceException e){
            throw new ServletException("selecting member failed");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        try{
            int id;
            if(req.getParameter("id")!=null)id = Integer.valueOf(req.getParameter("id"));
            else throw new ServletException("member id missing");
            membreService.delete(id);
            resp.sendRedirect("/LibraryManager/membre_list");
        }catch (ServiceException e){
            throw new ServletException("deleting member failed");
        }
    }
}
