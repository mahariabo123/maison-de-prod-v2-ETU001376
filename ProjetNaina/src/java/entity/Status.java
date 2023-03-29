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
@Entity
@Table(name="Status")
@NomTable(nom="Status")
@Vue()
public class Status {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idstatus")
    @SequenceGenerator(name="idstatus", sequenceName="idstatus", allocationSize=1)
    @Attribut(columnName = "idstatus",isprimarykey = true)
    int idstatus;
    @Attribut(columnName = "intitule")
    String intitule;

    public int getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(int idstatus) {
        this.idstatus = idstatus;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Status(String intitule) {
        this.idstatus = -1;
        this.intitule = intitule;
    }

    public Status() {
    }
    
}
