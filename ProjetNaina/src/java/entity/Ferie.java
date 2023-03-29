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
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author USER
 */
@NomTable(nom="Ferie")
@Vue(isvue = true)
public class Ferie {
    @Attribut(columnName = "ferie")
    Date ferie;

    public Date getFerie() {
        return ferie;
    }

    public void setFerie(Date ferie) {
        this.ferie = ferie;
    }

    public Ferie(Date ferie) {
        this.ferie = ferie;
    }

    public Ferie() {
    }
    
    public  boolean isferie(){
        try{
            Ferie fer=(Ferie)(new GenericDao().find(this).get(0));
        }catch(Exception e){
            System.out.println("test "+e.getMessage());
            return false;
        }
        return true;
    }
    
}

