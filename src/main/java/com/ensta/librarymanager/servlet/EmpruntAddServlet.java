package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
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
import java.time.LocalDate;
import java.util.List;

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        try{
            List<Livre> livreList = livreService.getListDispo();
            List<Membre> membreList = membreService.getListMembreEmpruntPossible();
            req.setAttribute("livreList",livreList);
            req.setAttribute("membreList",membreList);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
            dispatcher.forward(req,res);
        }catch (ServiceException e){
            throw new ServletException("Emprunt add servlet doGet failed");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException{
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try{
            int idMembre = Integer.valueOf(req.getParameter("idMembre"));
            int idLivre = Integer.valueOf(req.getParameter("idLivre"));
            empruntService.create(idMembre,idLivre, LocalDate.now());
            res.sendRedirect("/LibraryManager/emprunt_list");
        }catch (NumberFormatException e){
            throw new ServletException("creation emprunt failed - bad args");
        }catch (ServiceException e){
            throw new ServletException("creation emprunt failed");
        }
    }
}
