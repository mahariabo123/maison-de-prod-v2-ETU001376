/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import annotation.Attribut;
import annotation.NomTable;
import annotation.Vue;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@NomTable(nom="auteur")
@Entity
@Table(name="Auteur")
@Vue()
public class Auteur {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idauteur")
    @SequenceGenerator(name="idauteur", sequenceName="idauteur", allocationSize=1)
    @Attribut(columnName = "idauteur",isprimarykey = true)
    int idauteur;
    @Attribut(columnName = "prenom")
    String prenom;

    public Auteur(String prenom) {
        this.idauteur = -1;
        this.prenom = prenom;
    }

    public Auteur() {
    }

    public int getIdauteur() {
        return idauteur;
    }

    public void setIdauteur(int idauteur) {
        this.idauteur = idauteur;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    
}
