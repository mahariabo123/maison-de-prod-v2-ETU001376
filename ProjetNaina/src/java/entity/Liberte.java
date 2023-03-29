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
import java.util.*;

/**
 *
 * @author USER
 */
@Table(name="Liberte")
@NomTable(nom="Liberte")
@Vue(isvue=true)
public class Liberte {
    @Attribut(columnName = "idplateau")
    int idplateau;
    @Attribut(columnName = "observation")
    String observation;
    @Attribut(columnName = "indisponible")
    Date indisponible;

    public Liberte(int idplateau,String observation, Date indisponible) {
        this.idplateau = idplateau;
        this.observation=observation;
        this.indisponible = indisponible;
    }

    public Liberte() {
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
    
    public int getIdplateau() {
        return idplateau;
    }

    public void setIdplateau(int idplateau) {
        this.idplateau = idplateau;
    }

    public Date getIndisponible() {
        return indisponible;
    }

    public void setIndisponible(Date indisponible) {
        this.indisponible = indisponible;
    }
    
    public static boolean isdisponible(int idplateau,Date ray){
        ArrayList<Liberte>list=new ArrayList<>();
        try{
            Liberte lib=((Liberte)(new GenericDao().find(new Liberte(idplateau,null, ray)).get(0)));
        }catch(Exception e){
            return true;
        }
        return false;
    }
    
    
}
