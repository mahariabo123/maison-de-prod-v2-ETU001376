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
@Table(name="Plateau")
@NomTable(nom="plateau")
@Vue()
public class Plateau {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idplateau")
    @SequenceGenerator(name="idplateau", sequenceName="idplateau", allocationSize=1)
    @Attribut(columnName = "idplateau",isprimarykey = true)
    int idplateau;
    @Attribut(columnName = "intitule")
    String intitule;

    public Plateau(String intitule) {
        this.idplateau = -1;
        this.intitule = intitule;
    }

    public Plateau() {
    }

    public int getIdplateau() {
        return idplateau;
    }

    public void setIdplateau(int idplateau) {
        this.idplateau = idplateau;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
    
   
    
}
