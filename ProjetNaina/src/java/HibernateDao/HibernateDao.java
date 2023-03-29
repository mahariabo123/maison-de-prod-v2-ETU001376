/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import static HibernateDao.GenericDao.toUpperFirst;
import annotation.Attribut;
import java.lang.reflect.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.*;
import javax.persistence.JoinColumn;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import inter.InterfaceDao;
import javax.persistence.PrimaryKeyJoinColumn;
import org.hibernate.Query;
/**
 *
 * @author USER
 */
@Component
public class HibernateDao implements InterfaceDao{
    @Autowired
    public SessionFactory sessionFactory;
    public HibernateDao(){
        
    }
    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    @Override
    public void save(Object ray)throws Exception{
            Session session=null;
            try{
                session=sessionFactory.openSession();
                session.beginTransaction();
                session.save(ray);
                session.getTransaction().commit();

            }catch(Exception e){
                throw e;
            }finally{
                if(session!=null){
                    session.close();
                }
            }
    }
    @Override
    public ArrayList<Object> pagination2(Object ra,String ray,int page,int nbPage)throws Exception{
        return new ArrayList<Object>();
    }
    @Override
    public ArrayList<Object> findBySql(Object ray,String req)throws Exception{
        return new ArrayList<Object>();
    }
    @Override
    public void delete(Object ray)throws Exception{
        Session session=null;
        ArrayList<Object>obj=new GenericDao().find(ray);
            try{
                session=sessionFactory.openSession();
                session.beginTransaction();
                if(obj.size()>0){
                    for (int i = 0; i < obj.size(); i++) {
                         session.delete(obj.get(i));
                    }
                    session.getTransaction().commit();
                }
                
                
                
            }catch(Exception e){
                throw e;
            }finally{
                if(session!=null){
                    session.close();
                }
            }
    }
    @Override
    public ArrayList<Object> pagination(String ray,int page,int nbPage)throws Exception{
        List<Object>bm=new ArrayList<Object>();
        ArrayList<Object>obj=new ArrayList<Object>();
        Session session=null;
        int increment=0;
        try{
            session=sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery(ray);
            query.setFirstResult(nbPage*(page-1));
            query.setMaxResults(nbPage);
            bm=query.list();
            if(bm.size()>0){
                
                for (int i = 0; i < bm.size(); i++) {
                    obj.add(bm.get(i));
                }
            }
            session.getTransaction().commit();
        }catch(Exception e){
            throw e;
        }finally{
                if(session!=null){
                    session.close();
                }
        }
        return obj;
    }
    @Override
    public void update(Object ray)throws Exception{
        Session session=null;
            try{
                session=sessionFactory.openSession();
                session.beginTransaction();
                session.update(ray);
                session.getTransaction().commit();
                
            }catch(Exception e){
                throw e;
            }finally{
                if(session!=null){
                    session.close();
                }
            }
    }
    @Override
    public ArrayList<Object> find(Object ray)throws Exception{
        
        List<Object>bm=new ArrayList<Object>();
        Session session=null;
        ArrayList<Object>obj=new ArrayList<Object>();
        try{
            session=sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria=session.createCriteria(ray.getClass());
            transCriteria(criteria,ray);
            bm=criteria.list();
            if(bm.size()>0){
                
                for (int i = 0; i < bm.size(); i++) {
                    obj.add(bm.get(i));
                }
            }
            session.getTransaction().commit();
        }catch(Exception e){
            throw e;
        }finally{
                if(session!=null){
                    session.close();
                }
        }
        return obj;
    }
    public void closeFactory()throws Exception{
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
                sessionFactory=null;
            } catch (Exception e) {
               throw e;
            }
        }
    }
    public static String toUpperFirst(String a) {
        char[] toChar = a.toCharArray();
        char[] f = new char[1];
        f[0] = toChar[0];
        String first = new String(f);
        String other;
        char[] oth = new char[toChar.length - 1];
        for (int i = 0; i < oth.length; i++) {
            oth[i] = toChar[i + 1];
        }
        other = new String(oth);
        return first.toUpperCase() + other;
    }
    public static void transCriteria(Criteria crit,Object ray)throws Exception{
        Field[]fields=ray.getClass().getDeclaredFields();
        Method[]methods=ray.getClass().getDeclaredMethods();
        System.out.println("size field"+fields.length);
        System.out.println("size method"+methods.length);
       for (int i=0;i<fields.length ;i++) 
       {
               for (int j=0;j<methods.length ;j++ ) 
               {
                   if (("get"+fields[i].getName()).compareToIgnoreCase(methods[j].getName())==0)
                   {
                           
                                              if(methods[j].invoke(ray)!=null){
                                                  System.out.println("ato");
                                                 if (methods[j].invoke(ray) instanceof Number)
                                                {
                                                   if(((Number)methods[j].invoke(ray)).doubleValue()>=0){
                                                            try{
                                                           crit.add(Restrictions.eq(fields[i].getName(), methods[j].invoke(ray)));
                                                        }catch(Exception er){

                                                        }
                                                   }
                                                                
                                               }else if (fields[i].getType().getName()=="java.lang.String")
                                               {
                                                   try{
                                                      crit.add(Restrictions.like(fields[i].getName(), methods[j].invoke(ray)));
                                                   }catch(Exception er){

                                                   }              
                                               }else if(fields[i].getType().getName()=="java.sql.Timestamp"){
                                                try{
                                                    crit.add(Restrictions.eq(fields[i].getName(), methods[j].invoke(ray)));
                                                }catch(Exception er){

                                                }  
                                            }
                                            else if(fields[i].getType().getName()=="java.util.Date"){
                                                try{
                                                   crit.add(Restrictions.eq(fields[i].getName(), methods[j].invoke(ray)));
                                                }catch(Exception er){

                                                }  
                                            }else{
                                                           JoinColumn atter=fields[i].getAnnotation(JoinColumn.class);
                                                           Field[]cleE=fields[i].getClass().getDeclaredFields();
                                                           Method[]metE=fields[i].getClass().getDeclaredMethods();
                                                           String val="";
                                                           if(atter!=null){
                                                               for(int k=0;k<cleE.length;k++){
                                                                        Attribut attri=cleE[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(atter.referencedColumnName())==0){
                                                                            Method met=fields[i].getClass().getMethod("get"+toUpperFirst(cleE[k].getName()));
                                                                            val=(String)met.invoke(fields[i]);
                                                                        }
                                                                    }
                                                               crit.add(Restrictions.eq(atter.name(), val));
                                                           }
                                               } 
                                         }
                                       
                           
                           
                   }        
               }    
       }
       System.out.println("tay be lesy elah le2");
        
    }
    @Override
    public void upt(int etat,int id)throws Exception{
        Session session=null;
        int increment=0;
        try{
            session=sessionFactory.openSession();
            session.beginTransaction();
            Query q=session.createQuery("update Objectinsert set etat = :status where id = :ids");
            q.setParameter("status", etat);
            q.setParameter("ids", id);
                    int status=q.executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e){
            throw e;
        }finally{
                if(session!=null){
                    session.close();
                }
        }
    }
    
}
