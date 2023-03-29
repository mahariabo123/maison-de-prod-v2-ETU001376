/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import annotation.Attribut;
import annotation.NomTable;
import annotation.Vue;
import java.util.Date;
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
@Entity
@Table(name="Planning")
@NomTable(nom="planning")
@Vue()
public class Planning {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idplanning")
    @SequenceGenerator(name="idplanning", sequenceName="idplanning", allocationSize=1)
    @Attribut(columnName = "idplanning",isprimarykey = true)
    int idplanning;
    @Attribut(columnName = "debut")
    Date debut;
    @Attribut(columnName = "fin")
    Date fin;
    @Attribut(columnName = "idwatch")
    int idwatch;
    @Attribut(columnName = "valider")
    int valider;

    public Planning(Date debut, Date fin, int idwatch,int valider) {
        this.idplanning = -1;
        this.debut = debut;
        this.fin = fin;
        this.valider = valider;
        this.idwatch=idwatch;
    }
    

    
    public Planning() {
    }

    public int getIdwatch() {
        return idwatch;
    }

    public void setIdwatch(int idwatch) {
        this.idwatch = idwatch;
    }

    public int getValider() {
        return valider;
    }

    public void setValider(int valider) {
        this.valider = valider;
    }

    public int getIdplanning() {
        return idplanning;
    }

    public void setIdplanning(int idplanning) {
        this.idplanning = idplanning;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }
    
    
    
}
