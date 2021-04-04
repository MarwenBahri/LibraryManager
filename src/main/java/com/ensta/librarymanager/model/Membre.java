package com.ensta.librarymanager.model;

public class Membre {
    int id;
    String nom,prenom;
    String adresse;
    String email,telephone;
    SUBSCRIPTION abonnement;
    public Membre(int i,String n,String p,String a,String e,String tel,SUBSCRIPTION ab){
        id=i;
        nom=n;
        prenom=p;
        adresse=a;
        email=e;
        telephone=tel;
        abonnement=ab;
    }
    public void setNom(String n){
        nom=n;
    }
    public void setPrenom(String p){
        prenom=p;
    }
    public void setAdresse(String a){
        adresse=a;
    }
    public void setEmail(String e){
        email=e;
    }
    public void setTelephone(String t){
        telephone=t;
    }
    public void setAbonnement(SUBSCRIPTION s){
        abonnement=s;
    }
    public int getId(){
        return id;
    }
    public String getNom(){
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public SUBSCRIPTION getAbonnement() {
        return abonnement;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "id=" + id +"\n"+
                ", nom='" + nom + '\'' +"\n"+
                ", prenom='" + prenom + '\'' +"\n"+
                ", adresse='" + adresse + '\'' +"\n"+
                ", email='" + email + '\'' +"\n"+
                ", telephone='" + telephone + '\'' +"\n"+
                ", abonnement=" + abonnement.name() +
                "}\n";
    }
}
