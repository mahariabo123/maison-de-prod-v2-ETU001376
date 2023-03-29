/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import HibernateDao.GenericDao;
import entity.Watch;
import inter.InterfaceDao;
import util.*;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
/**
 *
 * @author USER
 */
@Component
public class WatchService {
    @Autowired
    @Qualifier("genericDao")
    InterfaceDao gen;
    public ArrayList<Watch> all()throws Exception{
        Watch ray=new Watch(null);
        ArrayList<Watch>list=new ArrayList<>();
        try{
            list=Util.castWatch(gen.find(ray));
            
        }catch(Exception e){
            
        }
        return list;
    }
    
}
