/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import annotation.Attribut;
import annotation.NomTable;
import annotation.Vue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author USER
 */
@NomTable(nom="horaire")
@Entity
@Table(name="Horaire")
@Vue()
public class Horaire {
     @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idhoraire")
    @SequenceGenerator(name="idhoraire", sequenceName="idhoraire", allocationSize=1)
    @Attribut(columnName = "idhoraire",isprimarykey = true)
    int idhoraire;
    @Attribut(columnName = "heure")
    double heure;

    public Horaire(double heure) {
        this.idhoraire = -1;
        this.heure = heure;
    }

    public Horaire() {
    }
    

    public int getIdhoraire() {
        return idhoraire;
    }

    public void setIdhoraire(int idhoraire) {
        this.idhoraire = idhoraire;
    }

    public double getHeure() {
        return heure;
    }

    public void setHeure(double heure) {
        this.heure = heure;
    }
    
    
}
