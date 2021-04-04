package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try{
            int nbLivres = livreService.count();
            int nbMembres = membreService.count();
            int nbEmprunts = empruntService.count();
            List<Emprunt> empruntList = empruntService.getListCurrent();
            req.setAttribute("nbLivres",nbLivres);
            req.setAttribute("nbMembres",nbMembres);
            req.setAttribute("nbEmprunts",nbEmprunts);
            req.setAttribute("empruntList",empruntList);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
            dispatcher.forward(req,resp);
        }catch (ServiceException e){
            throw new ServletException("dashboard error");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        doGet(req,resp);
    }
}
