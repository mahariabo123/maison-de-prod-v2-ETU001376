/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import entity.Acteur;
import entity.Auteur;
import inter.InterfaceDao;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import util.Util;

/**
 *
 * @author USER
 */
@Component
public class AuteurService {
    @Autowired
    @Qualifier("genericDao")
    InterfaceDao gen;
    public ArrayList<Auteur> all()throws Exception{
        System.out.println("bebe e");
        Auteur ray=new Auteur(null);
        ArrayList<Auteur>list=new ArrayList<>();
        try{
            list=Util.castAuteur(gen.find(ray));
            
        }catch(Exception e){
            
        }
        System.out.println("size "+list.size());
        return list;
    }
}
