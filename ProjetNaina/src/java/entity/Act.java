/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import HibernateDao.GenericDao;
import annotation.Attribut;
import annotation.NomTable;
import annotation.Vue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import util.*;
/**
 *
 * @author USER
 */

@Entity
@Table(name="Act")
@NomTable(nom="Act")
@Vue()
public class Act {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idact")
    @SequenceGenerator(name="idact", sequenceName="idact", allocationSize=1)
    @Attribut(columnName = "idact",isprimarykey = true)
    int idact;
    @Attribut(columnName = "intitule")
    String intitule;
    @Attribut(columnName = "idscene")
    int idscene;
    @Attribut(columnName = "idsentiment")
    int idsentiment;
    @Attribut(columnName = "idacteur")
    int idacteur;
     @Attribut(columnName = "dure")
    String dure;

    public Act(String intitule, int idscene, int idsentiment,int idacteur,String dure) {
        this.idact = -1;
        this.intitule = intitule;
        this.idscene = idscene;
        this.idsentiment = idsentiment;
        this.idacteur=idacteur;
        this.dure=dure;
    }

    public String getDure() {
        return dure;
    }

    public void setDure(String dure) {
        this.dure = dure;
    }
    
    

    public Act() {
    }
    
    

    public int getIdacteur() {
        return idacteur;
    }

    public void setIdacteur(int idacteur) {
        this.idacteur = idacteur;
    }
    

    public int getIdact() {
        return idact;
    }

    public void setIdact(int idact) {
        this.idact = idact;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getIdscene() {
        return idscene;
    }

    public void setIdscene(int idscene) {
        this.idscene = idscene;
    }

    public int getIdsentiment() {
        return idsentiment;
    }

    public void setIdsentiment(int idsentiment) {
        this.idsentiment = idsentiment;
    }
    public String acteur()throws Exception{
        Acteur ray=new Acteur(null);
        ray.setIdacteur(this.getIdacteur());
        return ((Acteur)new GenericDao().find(ray).get(0)).getPrenom();
    }
    public String sentiment()throws Exception{
        Sentiment ray=new Sentiment(null);
        ray.setIdsentiment(this.getIdsentiment());
        return ((Sentiment)new GenericDao().find(ray).get(0)).getIntitule();
    }
    
    public long dure(){
        return Util.strToMillis(this.getDure());
    }
    
    public String duree(){
        long timeInMillis = this.dure(); // replace with your desired value in milliseconds

        int hours = (int) (timeInMillis / (1000 * 60 * 60));
        int minutes = (int) ((timeInMillis / (1000 * 60)) % 60);
        int seconds = (int) ((timeInMillis / 1000) % 60);
        
        return hours+":"+minutes+":"+seconds;
    }
    
}
