package com.ensta.librarymanager.service;

import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Membre;

import java.util.ArrayList;
import java.util.List;

public class MembreServiceImpl {
    private static MembreServiceImpl instance;
    MembreDaoImpl membreDao;

    private MembreServiceImpl(){
        membreDao = MembreDaoImpl.getInstance();
    }
    public static MembreServiceImpl getInstance() {
        if(instance==null){
            instance = new MembreServiceImpl();
        }
        return instance;
    }
    public List<Membre> getList() throws ServiceException{
        try{
            List<Membre> membres = membreDao.getList();
            return membres;
        }catch (DaoException e){
            throw new ServiceException("Select membres query failed");
        }
    }
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
        try{
            List<Membre> membres = getList();
            List<Membre> membrePossibles = new ArrayList<Membre>();
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            for(int i=0;i<membres.size();i++){
                if(empruntService.isEmpruntPossible(membres.get(i)))
                    membrePossibles.add(membres.get(i));
            }
            return membrePossibles;
        }catch (ServiceException e){
            throw new ServiceException("membre emprunt possible failed");
        }
    }
    public Membre getById(int id) throws ServiceException{
        try{
            if(id<0)throw new ServiceException("Select id negative");
            Membre membre = membreDao.getById(id);
            return membre;
        }catch (DaoException e){
            throw new ServiceException("Select membre by id failed");
        }
    }
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
        try{
            if(nom==null)throw new ServiceException("membre lastname null");
            if(prenom==null)throw new ServiceException("membre name null");
            nom = nom.toUpperCase();
            int id = membreDao.create(nom,prenom,adresse,email,telephone);
            return id;
        }catch (DaoException e){
            throw new ServiceException("creating membre failed - service");
        }
    }
    public void update(Membre membre) throws ServiceException{
        try{
            if(membre.getNom()==null)throw new ServiceException("membre lastname null");
            if(membre.getPrenom()==null)throw new ServiceException("membre name null");
            membre.setNom(membre.getNom().toUpperCase());
            membreDao.update(membre);
        }catch (DaoException e){
            throw new ServiceException("update membre failed");
        }
    }
    public void delete(int id) throws ServiceException{
        try{
            if(id<0)throw new ServiceException("membre id negative");
            membreDao.delete(id);
        }catch (DaoException e){
            throw new ServiceException("delete membre failed");
        }
    }
    public int count() throws ServiceException{
        try{
            int cnt = membreDao.count();
            return cnt;
        }catch (DaoException e){
            throw new ServiceException("membre count failed");
        }
    }

}
