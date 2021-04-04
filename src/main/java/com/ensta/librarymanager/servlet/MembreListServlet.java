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
import java.util.List;

@WebServlet("/membre_list")
public class MembreListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        try{
            List<Membre> membreList = membreService.getList();
            req.setAttribute("membreList",membreList);
            req.getRequestDispatcher("/WEB-INF/View/membre_list.jsp").forward(req,resp);
        }catch (ServiceException e){
            throw new ServletException("geting member list failed");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        doGet(req,resp);
    }
}
