package com.ensta.librarymanager.model;

public class Livre {
    int id;
    String titre;
    String auteur;
    String isbn;
    public Livre(int i, String t,String a,String ib){
        id=i;
        titre=t;
        auteur=a;
        isbn=ib;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitre() {
        return titre;
    }

    @Override
    public String toString(){
        return "Livre{" +
                "id=" + id +"\n"+
                ", titre='" + titre + '\''+"\n" +
                ", auteur='" + auteur + '\''+"\n" +
                ", isbn='" + isbn +'}';
    }
}
