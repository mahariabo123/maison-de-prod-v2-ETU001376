/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author USER
 */
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
@NomTable(nom="acteur")
@Entity
@Table(name="Acteur")
@Vue()
public class Acteur {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idacteur")
    @SequenceGenerator(name="idacteur", sequenceName="idacteur", allocationSize=1)
    @Attribut(columnName = "idacteur",isprimarykey = true)
    int idacteur;
    @Attribut(columnName = "prenom")
    String prenom;

    public Acteur(String prenom) {
        this.idacteur = -1;
        this.prenom = prenom;
    }

    public Acteur() {
    }

    public int getIdacteur() {
        return idacteur;
    }

    public void setIdacteur(int idacteur) {
        this.idacteur = idacteur;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    
}
