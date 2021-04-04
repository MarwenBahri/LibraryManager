package com.ensta.librarymanager.model;

import java.time.LocalDate;

public class Emprunt {
    int id;
    Membre membre;
    Livre livre;
    LocalDate dateRetour;
    LocalDate dateEmprunt;

    public Emprunt(int i ,Membre m,Livre l,LocalDate de){
        id = i;
        membre=m;
        livre=l;
        dateEmprunt = de;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public int getId() {
        return id;
    }

    public Livre getLivre() {
        return livre;
    }

    public Membre getMembre() {
        return membre;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    @Override
    public String toString(){
        String r = membre.toString()+"\n";
        r+=livre.toString()+"\n";
        r+="Date d'emprunt : "+dateEmprunt.toString()+"\n";
        if(dateRetour!=null)
            r+="Date de retour : "+dateRetour.toString()+"\n";
        else r+="Date de retour : null\n";
        return r;
    }
}
