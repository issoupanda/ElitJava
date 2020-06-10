/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.entities;

/**
 *
 * @author megapc
 */
public class User {
    private int id,idClass,phoneNumber,frais,idrdv,nbr_mois_payer;
    private String username,email,password,type,firstname,lastname,Birth,profilePicture;
    private String annee_scolaire,niveau,etat,adresse;

    public User(int id, String email, String firstname, String lastname) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User() {
    }

    public User(int phoneNumber, int idrdv, String username, String email, String password, String type, String firstname, String lastname, String Birth, String profilePicture, String etat) {
       
        this.phoneNumber = phoneNumber;
        this.idrdv = idrdv;
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
        this.firstname = firstname;
        this.lastname = lastname;
        this.Birth = Birth;
        this.profilePicture = profilePicture;
        this.etat = etat;
    }

    public User(int idClass, int phoneNumber, int frais, String username, String email, String firstname, String lastname, String Birth, String profilePicture, int nbr_mois_payer, String annee_scolaire, String niveau) {
        
        this.idClass = idClass;
        this.phoneNumber = phoneNumber;
        this.frais = frais;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.Birth = Birth;
        this.profilePicture = profilePicture;
        this.nbr_mois_payer = nbr_mois_payer;
        this.annee_scolaire = annee_scolaire;
        this.niveau = niveau;
    }
    public User(int idClass, int phoneNumber, int frais, String username, String email, String firstname, String lastname, String Birth, String profilePicture, int nbr_mois_payer, String annee_scolaire, String niveau,String adresse) {
        
        this.idClass = idClass;
        this.phoneNumber = phoneNumber;
        this.frais = frais;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.Birth = Birth;
        this.profilePicture = profilePicture;
        this.nbr_mois_payer = nbr_mois_payer;
        this.annee_scolaire = annee_scolaire;
        this.niveau = niveau;
        this.adresse=adresse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getFrais() {
        return frais;
    }

    public void setFrais(int frais) {
        this.frais = frais;
    }

    public int getIdrdv() {
        return idrdv;
    }

    public void setIdrdv(int idrdv) {
        this.idrdv = idrdv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirth() {
        return Birth;
    }

    public void setBirth(String Birth) {
        this.Birth = Birth;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getNbr_mois_payer() {
        return nbr_mois_payer;
    }

    public void setNbr_mois_payer(int nbr_mois_payer) {
        this.nbr_mois_payer = nbr_mois_payer;
    }

    public String getAnnee_scolaire() {
        return annee_scolaire;
    }

    public void setAnnee_scolaire(String annee_scolaire) {
        this.annee_scolaire = annee_scolaire;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getEtat() {
        return etat;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname ;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    
}
