package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.SUBSCRIPTION;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDaoImpl implements EmpruntDao {
    private static EmpruntDaoImpl instance;
    private EmpruntDaoImpl(){}

    public static EmpruntDaoImpl getInstance() {
        if(instance==null){
            instance=new EmpruntDaoImpl();
        }
        return instance;
    }

    public List<Emprunt> getList() throws DaoException{
        String selectQuery = "SELECT e.id AS id, idMembre, nom, prenom,adresse " +
                ", email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour " +
                "FROM emprunt AS e INNER JOIN membre ON membre.id=e.idMembre " +
                "INNER JOIN livre ON livre.id = e.idLivre " +
                "ORDER BY dateRetour DESC;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Emprunt> l = new ArrayList<Emprunt>();
            while(rs.next()){
                Membre membre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),rs.getString("prenom"),
                        rs.getString("adresse"),rs.getString("email"),
                        rs.getString("telephone"),
                        SUBSCRIPTION.valueOf(rs.getString("abonnement")));
                Livre livre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),
                        rs.getString("isbn"));
                Emprunt emprunt = new Emprunt(rs.getInt("id"),membre,livre,
                        rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour")!=null)emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                l.add(emprunt);
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("Select emprunt query failed - dao");
        }
    }
    public List<Emprunt> getListCurrent() throws DaoException{
        String selectQuery = "SELECT e.id AS id, idMembre, nom, prenom,adresse " +
                ", email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour " +
                "FROM emprunt AS e INNER JOIN membre ON membre.id=e.idMembre " +
                "INNER JOIN livre ON livre.id = e.idLivre " +
                "WHERE dateRetour IS NULL;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Emprunt> l = new ArrayList<Emprunt>();
            while(rs.next()){
                Membre membre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),rs.getString("prenom"),
                        rs.getString("adresse"),rs.getString("email"),
                        rs.getString("telephone"),
                        SUBSCRIPTION.valueOf(rs.getString("abonnement")));
                Livre livre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),
                        rs.getString("isbn"));
                Emprunt emprunt = new Emprunt(rs.getInt("id"),membre,livre,
                        rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour")!=null)emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                l.add(emprunt);
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("Select emprunt query current failed");
        }
    }
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
        String selectQuery = "SELECT e.id AS id, idMembre, nom, prenom,adresse " +
                ", email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour " +
                "FROM emprunt AS e INNER JOIN membre ON membre.id=e.idMembre " +
                "INNER JOIN livre ON livre.id = e.idLivre " +
                "WHERE dateRetour IS NULL AND membre.id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setInt(1,idMembre);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Emprunt> l = new ArrayList<Emprunt>();
            while(rs.next()){
                Membre membre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),rs.getString("prenom"),
                        rs.getString("adresse"),rs.getString("email"),
                        rs.getString("telephone"),
                        SUBSCRIPTION.valueOf(rs.getString("abonnement")));
                Livre livre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),
                        rs.getString("isbn"));
                Emprunt emprunt = new Emprunt(rs.getInt("id"),membre,livre,
                        rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour")!=null)emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                l.add(emprunt);
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("Select emprunt query current by membre failed");
        }
    }
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
        String selectQuery = "SELECT e.id AS id, idMembre, nom, prenom,adresse " +
                ", email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour " +
                "FROM emprunt AS e INNER JOIN membre ON membre.id=e.idMembre " +
                "INNER JOIN livre ON livre.id = e.idLivre " +
                "WHERE dateRetour IS NULL AND livre.id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setInt(1,idLivre);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Emprunt> l = new ArrayList<Emprunt>();
            while(rs.next()){
                Membre membre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),rs.getString("prenom"),
                        rs.getString("adresse"),rs.getString("email"),
                        rs.getString("telephone"),
                        SUBSCRIPTION.valueOf(rs.getString("abonnement")));
                Livre livre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),
                        rs.getString("isbn"));
                Emprunt emprunt = new Emprunt(rs.getInt("id"),membre,livre,
                        rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour")!=null)emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                l.add(emprunt);
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("Select emprunt query current by livre failed");
        }
    }
    public Emprunt getById(int id) throws DaoException{
        String selectQuery = "SELECT e.id AS idEmprunt, idMembre, nom, prenom,adresse " +
                ", email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour " +
                "FROM emprunt AS e INNER JOIN membre ON membre.id=e.idMembre " +
                "INNER JOIN livre ON livre.id = e.idLivre " +
                "WHERE e.id=?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setInt(1,id);
            ResultSet rs = selectPreparedStatement.executeQuery();

            if(!rs.next()) throw new DaoException("emprunt not found -dao");
            Membre membre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),rs.getString("email"),
                    rs.getString("telephone"),
                    SUBSCRIPTION.valueOf(rs.getString("abonnement")));
            Livre livre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),
                    rs.getString("isbn"));
            Emprunt emprunt = new Emprunt(rs.getInt("idEmprunt"),membre,livre,
                    rs.getDate("dateEmprunt").toLocalDate());
            if(rs.getDate("dateRetour")!=null)emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
            selectPreparedStatement.close();
            connection.close();
            return emprunt;
        }catch (SQLException e){
            throw new DaoException("Select emprunt query by id failed -dao");
        }
    }
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException{
        String insertQuery = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt) values(?,?,?);";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1,idMembre);
            insertStatement.setInt(2,idLivre);
            insertStatement.setDate(3, Date.valueOf(dateEmprunt));
            insertStatement.executeUpdate();
            connection.commit();
            insertStatement.close();
            connection.close();
        }catch (SQLException e){
            throw new DaoException("Insert emprunt query failed");
        }
        return;
    }
    public void update(Emprunt emprunt) throws DaoException{
        String updateQuery = "UPDATE emprunt SET idMembre=?, idLivre=?, " +
                "dateEmprunt=?, dateRetour=? WHERE id=?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1,emprunt.getMembre().getId());
            updateStatement.setInt(2,emprunt.getLivre().getId());
            updateStatement.setDate(3,Date.valueOf(emprunt.getDateEmprunt()));
            updateStatement.setDate(4,Date.valueOf(emprunt.getDateRetour()));
            updateStatement.setInt(5,emprunt.getId());
            updateStatement.executeUpdate();
            connection.commit();
            updateStatement.close();
            connection.close();
        }catch (SQLException e){
            throw new DaoException("Update emprunt query failed");
        }
    }
    public int count() throws DaoException{
        String selectQuery = "SELECT COUNT(id) AS count FROM emprunt;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectStatement.executeQuery();
            rs.next();
            int cnt = rs.getInt("count");
            selectStatement.close();
            connection.close();
            return cnt;
        }catch (SQLException e){
            throw new DaoException("Count emprunt query failed");
        }

    }
}
