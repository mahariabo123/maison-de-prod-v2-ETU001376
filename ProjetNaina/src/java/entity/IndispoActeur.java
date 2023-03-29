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
import javax.persistence.Table;
import util.Util;

/**
 *
 * @author USER
 */
@NomTable(nom="indispoacteur")
@Vue(isvue=true)
public class IndispoActeur {
    @Attribut(columnName = "idacteur")
    int idacteur;
    @Attribut(columnName = "observation")
    String observation;
    @Attribut(columnName = "indisponible")
    Date indisponible;

    public IndispoActeur(int idacteur, String observation, Date indisponible) {
        this.idacteur = idacteur;
        this.observation = observation;
        this.indisponible = indisponible;
    }

    public IndispoActeur() {
    }
    
    

    public int getIdacteur() {
        return idacteur;
    }

    public void setIdacteur(int idacteur) {
        this.idacteur = idacteur;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getIndisponible() {
        return indisponible;
    }

    public void setIndisponible(Date indisponible) {
        this.indisponible = indisponible;
    }
    public static boolean isdisponible(int idacteur,Date ray){
        ArrayList<IndispoActeur>list=new ArrayList<>();
        try{
            IndispoActeur lib=((IndispoActeur)(new GenericDao().find(new IndispoActeur(idacteur,null, ray)).get(0)));
        }catch(Exception e){
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }
    public static boolean ifscenedisponible(Scene ray,Date dt){
        ArrayList<Act>listAct=new ArrayList<>();
        try{
             listAct=Util.castAct(new GenericDao().find(new Act(null,ray.getIdscene(),-1,-1,null)));
        }catch(Exception e){
            
        }
        for (int i = 0; i < listAct.size(); i++) {
            if(IndispoActeur.isdisponible(listAct.get(i).getIdacteur(), dt)==false){
                return false;
            }
        }
        return true;
    }
}
