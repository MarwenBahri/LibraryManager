package com.ensta.librarymanager.service;

import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;

import java.util.ArrayList;
import java.util.List;

public class LivreServiceImpl {
    private static LivreServiceImpl instance;
    LivreDaoImpl livreDao;

    private LivreServiceImpl(){
        livreDao = LivreDaoImpl.getInstance();
    }
    public static LivreServiceImpl getInstance() {
        if(instance==null){
            instance = new LivreServiceImpl();
        }
        return instance;
    }
    public List<Livre> getList() throws ServiceException{
        try{
            List<Livre> livres = livreDao.getList();
            return livres;
        }catch (DaoException e){
            throw new ServiceException("Select livres query failed");
        }
    }
    public List<Livre> getListDispo() throws ServiceException{
        try{
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            List<Livre> livres = getList();
            List<Livre> livresDispo = new ArrayList<Livre>();
            for(int i=0;i<livres.size();i++){
                if(empruntService.isLivreDispo(livres.get(i).getId()))
                    livresDispo.add(livres.get(i));
            }
            return getList();
        }catch (ServiceException e){
            throw new ServiceException("Select list dispo failed");
        }
    }
    public Livre getById(int id) throws ServiceException{
        try{
            if(id<0)throw new ServiceException("Select id negative");
            Livre livre = livreDao.getById(id);
            return livre;
        }catch (DaoException e){
            throw new ServiceException("Select livre by id failed");
        }
    }
    public int create(String titre, String auteur, String isbn) throws ServiceException{
        try{
            if(titre==null)throw new ServiceException("livre title null");
            int cnt = livreDao.create(titre,auteur,isbn);
            return cnt;
        }catch (DaoException e){
            throw new ServiceException("Creating livre failed");
        }
    }
    public void update(Livre livre) throws ServiceException{
        try{
            if(livre.getTitre()==null)throw new ServiceException("livre title null");
            livreDao.update(livre);
        }catch (DaoException e){
            throw new ServiceException("update livre failed");
        }
    }
    public void delete(int id) throws ServiceException{
        try{
            if(id<0)throw new ServiceException("livre id negative");
            livreDao.delete(id);
        }catch (DaoException e){
            throw new ServiceException("delete livre failed");
        }
    }
    public int count() throws ServiceException{
        try{
            int cnt = livreDao.count();
            return cnt;
        }catch (DaoException e){
            throw new ServiceException("livre count failed");
        }
    }
}
