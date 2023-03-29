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
import java.util.ArrayList;
import java.util.Date;
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
@Table(name="Scene")
@NomTable(nom="scene")
@Vue()
public class Scene {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idscene")
    @SequenceGenerator(name="idscene", sequenceName="idscene", allocationSize=1)
    @Attribut(columnName = "idscene",isprimarykey = true)
    int idscene;
    @Attribut(columnName = "intitule")
    String intitule;
    @Attribut(columnName = "idplateau")
    int idplateau;
    @Attribut(columnName = "idwatch")
    int idwatch;
    @Attribut(columnName = "numero")
    int numero;
    @Attribut(columnName = "plannification")
    Date plannification;
    @Attribut(columnName = "idstatus")
    int idstatus;
    @Attribut(columnName = "idauteur")
    int idauteur;
    
    

    public Scene(String intitule, int idplateau,int idwatch,int numero,Date plannification,int idstatus,int idauteur) {
        this.idscene = -1;
        this.intitule = intitule;
        this.idplateau = idplateau;
        this.idwatch = idwatch;
        this.numero=numero;
        this.plannification=plannification;
        this.idstatus=idstatus;
        this.idauteur=idauteur;
    }

    public Date getPlannification() {
        return plannification;
    }

    public void setPlannification(Date plannification) {
        this.plannification = plannification;
    }
    

    public Scene() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getIdauteur() {
        return idauteur;
    }

    public void setIdauteur(int idauteur) {
        this.idauteur = idauteur;
    }
    
    
    

    public int getIdwatch() {
        return idwatch;
    }

    public void setIdwatch(int idwatch) {
        this.idwatch = idwatch;
    }
    
    
    public int getIdscene() {
        return idscene;
    }

    public void setIdscene(int idscene) {
        this.idscene = idscene;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getIdplateau() {
        return idplateau;
    }

    public void setIdplateau(int idplateau) {
        this.idplateau = idplateau;
    }
    
    public String auteur()throws Exception{
        Auteur ray=new Auteur(null);
        ray.setIdauteur(this.getIdauteur());
        return ((Auteur)new GenericDao().find(ray).get(0)).getPrenom();
    }
    public String watch()throws Exception{
        Watch ray=new Watch(null);
        ray.setIdwatch(this.getIdwatch());
        return ((Watch)new GenericDao().find(ray).get(0)).getIntitule();
    }
    
    public String plateau()throws Exception{
        Plateau ray=new Plateau(null);
        ray.setIdplateau(this.getIdplateau());
        return ((Plateau)new GenericDao().find(ray).get(0)).getIntitule();
    }
    public ArrayList<Act> listAct(){
        ArrayList<Act>list=new   ArrayList<>();
        try{
            list=Util.castAct(new GenericDao().find(new Act(null, this.getIdscene(),-1,-1,null))); 
        }catch(Exception e){
            
        }
        return list;
    }
    public long dure(){
        long total=0;
        ArrayList<Act>list=new   ArrayList<>();
        try{
            list=Util.castAct(new GenericDao().find(new Act(null, this.getIdscene(),-1,-1,null))); 
        }catch(Exception e){
            
        }
        for (int i = 0; i <list.size(); i++) {
            total+=list.get(i).dure();
        }
        return total;
    }
    public String duree(){
        long timeInMillis = this.dure(); // replace with your desired value in milliseconds

        int hours = (int) (timeInMillis / (1000 * 60 * 60));
        int minutes = (int) ((timeInMillis / (1000 * 60)) % 60);
        int seconds = (int) ((timeInMillis / 1000) % 60);
        
        return hours+":"+minutes+":"+seconds;

    }
    
    public String status(){
        String intitule="";
        try{
            Status ray=new Status(null);
            ray.setIdstatus(this.idstatus);
            Status lib=((Status)(new GenericDao().find(ray).get(0)));
            intitule=lib.getIntitule();
        }catch(Exception e){
            
        }
        return intitule;
    }

    public int getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(int idstatus) {
        this.idstatus = idstatus;
    }
    
    
    
    
    
    
    
}
