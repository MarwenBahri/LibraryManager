package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/livre_details")
public class LivreDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try{
            int livreID = Integer.valueOf(req.getParameter("id"));
            Livre livre = livreService.getById(livreID);
            req.setAttribute("livre",livre);
            List<Emprunt> emprunt = empruntService.getListCurrentByLivre(livreID);
            req.setAttribute("emprunts",emprunt);
            if(emprunt!=null && emprunt.size()!=0)
                req.setAttribute("exists",Boolean.TRUE);
            else req.setAttribute("exists",Boolean.FALSE);
            req.getRequestDispatcher("/WEB-INF/View/livre_details.jsp").forward(req,resp);
        }catch (NumberFormatException e){
            throw new ServletException("livre id missing");
        }catch (ServiceException e){
            throw new ServletException("getting book failed");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        try{
            String titre = req.getParameter("titre");
            String auteur = req.getParameter("auteur");
            String isbn = req.getParameter("isbn");
            int id = Integer.valueOf(req.getParameter("id"));
            Livre livre = livreService.getById(id);
            livre.setAuteur(auteur);
            livre.setIsbn(isbn);
            livre.setTitre(titre);
            livreService.update(livre);
            resp.sendRedirect("/LibraryManager/livre_details?id="+id);
        }catch (NumberFormatException e){
            throw new ServletException("livre id missing");
        }catch (ServiceException e){
            throw new ServletException("updating book failed");
        }
    }
}
