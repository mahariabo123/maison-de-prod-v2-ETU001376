/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import entity.Acteur;
import entity.Status;
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
public class StatusService {
    @Autowired
    @Qualifier("genericDao")
    InterfaceDao gen;
    public ArrayList<Status> all()throws Exception{
        Status ray=new Status(null);
        ArrayList<Status>list=new ArrayList<>();
        try{
            list=Util.castStatus(gen.find(ray));
            
        }catch(Exception e){
            
        }
        return list;
    }
}
