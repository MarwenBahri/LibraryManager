package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDaoImpl {
    private static LivreDaoImpl instance;
    private LivreDaoImpl(){}

    public static LivreDaoImpl getInstance() {
        if(instance==null){
            instance = new LivreDaoImpl();
        }
        return instance;
    }
    public List<Livre> getList() throws DaoException{
        String selectQuery = "SELECT id, titre,auteur,isbn FROM livre;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Livre> l = new ArrayList<Livre>();
            while(rs.next()){
                Livre livre = new Livre(rs.getInt("id"),rs.getString("titre")
                        ,rs.getString("auteur"),rs.getString("isbn"));
                l.add(livre);
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("selecting books failed -dao");
        }
    }
    public Livre getById(int id) throws DaoException{
        String selectQuery = "SELECT id, titre,auteur,isbn FROM livre " +
                "WHERE id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setInt(1,id);
            ResultSet rs = selectPreparedStatement.executeQuery();
            rs.next();
            Livre livre = new Livre(rs.getInt("id"),rs.getString("titre")
                    ,rs.getString("auteur"),rs.getString("isbn"));

            selectPreparedStatement.close();
            connection.close();
            return livre;
        }catch (SQLException e){
            throw new DaoException("selecting book by id failed -dao");
        }
    }
    public int create(String titre, String auteur, String isbn) throws DaoException{
        String insertQuery = "INSERT INTO livre(titre,auteur,isbn) VALUES(?,?,?);";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1,titre);
            insertStatement.setString(2,auteur);
            insertStatement.setString(3,isbn);
            insertStatement.executeUpdate();
            ResultSet rs  = insertStatement.getGeneratedKeys();
            int cnt=-1;
            if(rs.next()){
                cnt = rs.getInt(1);
            }
            connection.commit();
            insertStatement.close();
            connection.close();
            return cnt;
        }catch (SQLException e){
            throw new DaoException("creating book failed -dao");
        }
    }
    public void update(Livre livre) throws DaoException{
        String updateQuery = "UPDATE livre SET titre=?, auteur=?, isbn=? WHERE id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1,livre.getTitre());
            updateStatement.setString(2,livre.getAuteur());
            updateStatement.setString(3, livre.getIsbn());
            updateStatement.setInt(4,livre.getId());
            updateStatement.executeUpdate();
            connection.commit();
            updateStatement.close();
            connection.close();
        }catch (SQLException e){
            throw new DaoException("updating book failed -dao");
        }
    }
    public void delete(int id) throws DaoException{
        String deleteQuery = "DELETE FROM livre WHERE id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1,id);
            deleteStatement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e){
            throw new DaoException("deleting book failed -dao");
        }
    }
    public int count() throws DaoException{
        String selectQuery = "SELECT COUNT(id) AS count FROM livre;";
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
            throw new DaoException("livre count failed -dao");
        }
    }
}
