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
@Table(name="Sousplanning")
@NomTable(nom="Sousplanning")
@Vue()
public class Sousplanning {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idsousplanning")
    @SequenceGenerator(name="idsousplanning", sequenceName="idsousplanning", allocationSize=1)
    @Attribut(columnName = "idsousplanning",isprimarykey = true)
    int idsousplanning;
    @Attribut(columnName = "idplanning")
    int idplanning;
    @Attribut(columnName = "idscene")
    int idscene;
    @Attribut(columnName = "dateplanning")
    Date dateplanning;

    public Sousplanning(int idplanning, int idscene, Date dateplanning) {
        this.idsousplanning = -1;
        this.idplanning = idplanning;
        this.idscene = idscene;
        this.dateplanning = dateplanning;
    }

    public Sousplanning() {
    }

    public int getIdsousplanning() {
        return idsousplanning;
    }

    public void setIdsousplanning(int idsousplanning) {
        this.idsousplanning = idsousplanning;
    }

    public int getIdplanning() {
        return idplanning;
    }

    public void setIdplanning(int idplanning) {
        this.idplanning = idplanning;
    }

    public int getIdscene() {
        return idscene;
    }

    public void setIdscene(int idscene) {
        this.idscene = idscene;
    }

    public Date getDateplanning() {
        return dateplanning;
    }

    public void setDateplanning(Date dateplanning) {
        this.dateplanning = dateplanning;
    }
    public Scene getScene(){
        Scene ray=new Scene(null,-1,-1,-1, null,-1,-1);
        ray.setIdscene(this.idscene);
        Scene search=new Scene();
        try{
            search=(Scene)new GenericDao().find(ray).get(0);
        }catch(Exception e){
            
        }
        return search;
    }

    
    
    
}
