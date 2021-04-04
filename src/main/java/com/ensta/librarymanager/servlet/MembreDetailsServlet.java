package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.SUBSCRIPTION;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.MembreServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try{
            int membreID = Integer.valueOf(req.getParameter("id"));
            Membre membre = membreService.getById(membreID);
            req.setAttribute("membre",membre);
            List<Emprunt> emprunt = empruntService.getListCurrentByMembre(membreID);
            req.setAttribute("emprunts",emprunt);
            if(emprunt!=null && emprunt.size()!=0)
                req.setAttribute("exists",Boolean.TRUE);
            else req.setAttribute("exists",Boolean.FALSE);
            req.getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(req,resp);
        }catch (NumberFormatException e){
            throw new ServletException("membre id missing");
        }catch (ServiceException e){
            throw new ServletException("getting membre details failed");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        try{
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String adresse = req.getParameter("adresse");
            String tel = req.getParameter("telephone");
            String email = req.getParameter("email");
            SUBSCRIPTION subscription = SUBSCRIPTION.valueOf(req.getParameter("abonnement"));
            int id = Integer.valueOf(req.getParameter("id"));
            Membre membre = membreService.getById(id);
            membre.setNom(nom);
            membre.setAbonnement(subscription);
            membre.setAdresse(adresse);
            membre.setEmail(email);
            membre.setPrenom(prenom);
            membre.setTelephone(tel);
            membreService.update(membre);
            resp.sendRedirect("/LibraryManager/membre_details?id="+id);
        }catch (NumberFormatException e){
            throw new ServletException("membre id missing");
        }catch (ServiceException e){
            throw new ServletException("updating membre failed");
        }
    }
}
