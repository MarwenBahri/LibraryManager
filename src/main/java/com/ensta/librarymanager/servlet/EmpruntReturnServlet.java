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

@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try{
            List<Emprunt> empruntList = empruntService.getListCurrent();
            if(req.getParameter("id")!=null) {
                int id = Integer.valueOf(req.getParameter("id"));
                for(int i=0;i<empruntList.size();i++){
                    if(empruntList.get(i).getId() == id){
                        Emprunt emprunt = empruntList.get(i);
                        empruntList.remove(i);
                        req.setAttribute("chosen",Boolean.TRUE);
                        req.setAttribute("chosenEmprunt",emprunt);
                        break;
                    }
                }
            }
            req.setAttribute("empruntList",empruntList);
            req.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp").forward(req,resp);
        }catch (ServiceException e){
            throw new ServletException("get emprunt return list failed");
        }catch (NumberFormatException e){
            throw new ServletException("failed to parse id");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try{
            int empruntId = Integer.valueOf(req.getParameter("id"));
            empruntService.returnBook(empruntId);
            resp.sendRedirect("/LibraryManager/emprunt_list");
        }catch (NumberFormatException e){
            throw new ServletException("returning livre failed");
        }catch (ServiceException e){
            throw new ServletException("returning book failed");
        }
    }
}
