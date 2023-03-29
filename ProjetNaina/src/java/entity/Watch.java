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

/**
 *
 * @author USER
 */
@Entity
@Table(name="Watch")
@NomTable(nom="watch")
@Vue()
public class Watch {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idwatch")
    @SequenceGenerator(name="idwatch", sequenceName="idwatch", allocationSize=1)
    @Attribut(columnName = "idwatch",isprimarykey = true)
    int idwatch;
    @Attribut(columnName = "intitule")
    String intitule;

    public Watch(String intitule) {
        this.idwatch=-1;
        this.idwatch = idwatch;
        this.intitule = intitule;
    }

    public Watch() {
    }

    public int getIdwatch() {
        return idwatch;
    }

    public void setIdwatch(int idwatch) {
        this.idwatch = idwatch;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    
    
}
