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
import java.util.List;

@WebServlet("/livre_list")
public class LivreListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        try{
            List<Livre> livreList = livreService.getList();
            req.setAttribute("livreList",livreList);
            req.getRequestDispatcher("/WEB-INF/View/livre_list.jsp").forward(req,resp);
        }catch (ServiceException e){
            throw new ServletException("getting livre list failed -servlet");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        doGet(req,resp);
    }
}
