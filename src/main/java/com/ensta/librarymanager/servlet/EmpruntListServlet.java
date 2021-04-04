package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.service.EmpruntServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/emprunt_list")
public class EmpruntListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try{
            String all = req.getParameter("show");
            List<Emprunt> empruntList;
            if(all!=null && all.equals("all"))empruntList = empruntService.getList();
            else empruntList = empruntService.getListCurrent();
            req.setAttribute("empruntList", empruntList);
            req.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(req,res);
        }catch (ServiceException e){
            throw new ServletException("show emprunt list failed");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException{
        doGet(req,res);
    }
}
