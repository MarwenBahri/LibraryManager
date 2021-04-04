package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.SUBSCRIPTION;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDaoImpl {
    private static MembreDaoImpl instance;
    private MembreDaoImpl(){}

    public static MembreDaoImpl getInstance() {
        if(instance==null){
            instance=new MembreDaoImpl();
        }
        return instance;
    }
    public List<Membre> getList() throws DaoException{
        String selectQuery = "SELECT id,nom,prenom,adresse,email,telephone,abonnement " +
                "FROM membre " +
                "ORDER BY nom,prenom;";

        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Membre> l = new ArrayList<Membre>();
            while(rs.next()){
                l.add(new Membre(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),
                        rs.getString("adresse"),rs.getString("email"),
                        rs.getString("telephone"),
                        SUBSCRIPTION.valueOf(rs.getString("abonnement"))));
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("membre get list failed");
        }
    }
    public Membre getById(int id) throws DaoException{
        String selectQuery = "SELECT id,nom,prenom,adresse,email,telephone,abonnement " +
                "FROM membre " +
                "WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setInt(1,id);
            ResultSet rs = selectPreparedStatement.executeQuery();
            if(!rs.next())throw new DaoException("selecting membre failed -dao");
            Membre membre = new Membre(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),
                    rs.getString("adresse"),rs.getString("email"),
                    rs.getString("telephone"),
                    SUBSCRIPTION.valueOf(rs.getString("abonnement")));
            selectPreparedStatement.close();
            connection.close();
            return membre;
        }catch (SQLException e){
            throw new DaoException("selecting member failed -dao");
        }
    }
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException{
        String insertQuery = "INSERT INTO membre(nom,prenom, adresse,email" +
                ",telephone,abonnement) VALUES(?,?,?,?,?,?);";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);
            //PreparedStatement insertStatement = connection.prepareStatement(insertQuery,new String[]{"id"});
            insertStatement.setString(1,nom);
            insertStatement.setString(2,prenom);
            insertStatement.setString(3,adresse);
            insertStatement.setString(4,email);
            insertStatement.setString(5,telephone);
            insertStatement.setString(6,"BASIC");
            int affectedRows = insertStatement.executeUpdate();
            if(affectedRows == 0)throw new DaoException("creating member failed - no affected rows");
            ResultSet rs  = insertStatement.getGeneratedKeys();
            int cnt = -1;
            if(rs.next()){
                cnt = rs.getInt(1);
            }
            connection.commit();
            insertStatement.close();
            connection.close();
            return cnt;
        }catch (SQLException e){
            throw new DaoException("creating membre failed");
        }

    }
    public void update(Membre membre) throws DaoException{
        String updateQuery = "UPDATE membre SET nom=?, prenom=?, adresse=?,email=?,telephone=?,abonnement=?" +
                "WHERE id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1,membre.getNom());
            updateStatement.setString(2,membre.getPrenom());
            updateStatement.setString(3,membre.getAdresse());
            updateStatement.setString(4,membre.getEmail());
            updateStatement.setString(5,membre.getTelephone());
            updateStatement.setString(6,membre.getAbonnement().toString());
            updateStatement.setInt(7,membre.getId());
            updateStatement.executeUpdate();
            connection.commit();
            updateStatement.close();
            connection.close();
        }catch (SQLException e){

        }
    }
    public void delete(int id) throws DaoException{
        String deleteQuery = "DELETE FROM membre WHERE id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1,id);
            deleteStatement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e){

        }
    }
    public int count() throws DaoException{
        String selectQuery = "SELECT COUNT(id) AS count FROM membre;";
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
            throw new DaoException("count membre failed");
        }
    }
}
