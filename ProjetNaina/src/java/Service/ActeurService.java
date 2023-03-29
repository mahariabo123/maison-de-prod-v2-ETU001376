/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import HibernateDao.GenericDao;
import entity.*;
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
public class ActeurService {
    @Autowired
    @Qualifier("genericDao")
    InterfaceDao gen;
    public ArrayList<Acteur> all()throws Exception{
        Acteur ray=new Acteur(null);
        ArrayList<Acteur>list=new ArrayList<>();
        try{
            list=Util.castActeur(gen.find(ray));
            
        }catch(Exception e){
            
        }
        return list;
    }
}
