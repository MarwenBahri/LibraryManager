package com.ensta.librarymanager.service;

import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.SUBSCRIPTION;

import java.time.LocalDate;
import java.util.List;

public class EmpruntServiceImpl {
    private static EmpruntServiceImpl instance;
    EmpruntDaoImpl empruntDao;

    private EmpruntServiceImpl(){
        empruntDao = EmpruntDaoImpl.getInstance();
    }
    public static EmpruntServiceImpl getInstance() {
        if(instance==null){
            instance = new EmpruntServiceImpl();
        }
        return instance;
    }
    public List<Emprunt> getList() throws ServiceException{
        try{
            List<Emprunt> empruntList = empruntDao.getList();
            return empruntList;
        }catch (DaoException e){
            throw new ServiceException("Select emprunts query failed");
        }
    }
    public List<Emprunt> getListCurrent() throws ServiceException{
        try{
            List<Emprunt> empruntList = empruntDao.getListCurrent();
            return empruntList;
        }catch (DaoException e){
            return null;
        }
    }
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{
        try{
            if(idMembre<0)throw new ServiceException("Membre id negative");
            List<Emprunt> empruntList = empruntDao.getListCurrentByMembre(idMembre);
            return empruntList;
        }catch (DaoException e){
            return null;
        }
    }
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
        try{
            if(idLivre<0)throw new ServiceException("Livre id negative");
            List<Emprunt> empruntList = empruntDao.getListCurrentByLivre(idLivre);
            return empruntList;
        }catch (DaoException e){
            return null;
        }
    }
    public Emprunt getById(int id) throws ServiceException{
        try{
            if(id<0)throw new ServiceException("emprunt id negative -service");
            Emprunt emprunt = empruntDao.getById(id);
            return emprunt;
        }catch(DaoException e){
            throw new ServiceException("selecting emprunt failed -service");
        }
    }
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{
        try{
            if(idMembre<0)throw new ServiceException("Membre id negative");
            if(idLivre<0)throw new ServiceException("Livre id negative");
            //maybe dateEmprunt should always be bigger than the current date ?
            empruntDao.create(idMembre,idLivre,dateEmprunt);
        }catch (DaoException e){
            throw new ServiceException("create emprunt failed");
        }
    }
    public void returnBook(int id) throws ServiceException{
        try {
            if(id<0)throw new ServiceException("Emprunt id negative");
            Emprunt emprunt = empruntDao.getById(id);
            emprunt.setDateRetour(LocalDate.now());
            empruntDao.update(emprunt);
        }catch (DaoException e){
            throw new ServiceException("returning book failed -service");
        }
    }
    public int count() throws ServiceException{
        try{
            int cnt = empruntDao.count();
            if (cnt <0 ){
                throw new ServiceException("Count emprunts negative");
            }
            return cnt;
        }catch (DaoException e){
            throw new ServiceException("count emprunts failed - service");
        }
    }
    public boolean isLivreDispo(int idLivre) throws ServiceException{
        try{
            if(idLivre<0)throw new ServiceException("Livre id negative");
            List<Emprunt> empruntList = empruntDao.getListCurrentByLivre(idLivre);
            if(empruntList.size()==0) return true;
            else return false;
        }catch (DaoException e){
            return false;
        }
    }
    public boolean isEmpruntPossible(Membre membre) throws ServiceException{
        try{
            List<Emprunt> empruntList = empruntDao.getListCurrentByMembre(membre.getId());
            int cnt = empruntList.size();
            SUBSCRIPTION subscription = membre.getAbonnement();
            int n = -1;
            switch (subscription){
                case BASIC:
                    n = 2;
                    break;
                case PREMIUM:
                    n = 5;
                    break;
                case VIP:
                    n = 20;
                    break;
            }
            if(n==-1)throw new ServiceException("is emprunt possible failed");
            return cnt < n;
        }catch (DaoException e){
            throw new ServiceException("is emprunt failed ");
        }
    }
}
