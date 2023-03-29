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
@Table(name="Sentiment")
@NomTable(nom="sentiment")
@Vue()
public class Sentiment {
     @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idsentiment")
    @SequenceGenerator(name="idsentiment", sequenceName="idsentiment", allocationSize=1)
    @Attribut(columnName = "idsentiment",isprimarykey = true)
    int idsentiment;
    @Attribut(columnName = "intitule")
    String intitule;

    public Sentiment(String intitule) {
        this.idsentiment =-1;
        this.intitule = intitule;
    }

    public Sentiment() {
    }

    public int getIdsentiment() {
        return idsentiment;
    }

    public void setIdsentiment(int idsentiment) {
        this.idsentiment = idsentiment;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
    
    
}
