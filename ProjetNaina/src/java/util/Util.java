/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import annotation.*;
import entity.*;
import java.util.*;
import java.lang.reflect.*;
import java.sql.Timestamp;
import java.time.*;
import java.lang.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 *
 * @author USER
 */
public class Util {
    public static long strToMillis(String ray){
        int[]tab=traduction(ray.split(":"));
        long total=0;
        for (int i = 0; i < tab.length; i++) {
            if(i==0){
                total+=tab[i]*3600*1000;
            }else if(i==1){
                total+=tab[i]*60*1000;
            }else if(i==2){
                total+=tab[i]*1000;
            }
        }
        return total;
        
    }
    public static int[] traduction(String[]tab){
        int[]t=null;
        if(tab.length>0){
            t=new int[tab.length];
            for (int i = 0; i < t.length; i++) {
                t[i]=Integer.parseInt(tab[i]);
            }
        }
        return t;
    }
    public static  ArrayList<Watch> castWatch(ArrayList<Object>ray){
        ArrayList<Watch>ar=new ArrayList<Watch>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Watch){
                    ar.add((Watch)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Auteur> castAuteur(ArrayList<Object>ray){
        ArrayList<Auteur>ar=new ArrayList<Auteur>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Auteur){
                    ar.add((Auteur)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Ferie> castFerie(ArrayList<Object>ray){
        ArrayList<Ferie>ar=new ArrayList<Ferie>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Ferie){
                    ar.add((Ferie)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Sousplanning> castSousplanning(ArrayList<Object>ray){
        ArrayList<Sousplanning>ar=new ArrayList<Sousplanning>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Sousplanning){
                    ar.add((Sousplanning)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Scene> castScene(ArrayList<Object>ray){
        ArrayList<Scene>ar=new ArrayList<Scene>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Scene){
                    ar.add((Scene)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Act> castAct(ArrayList<Object>ray){
        ArrayList<Act>ar=new ArrayList<Act>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Act){
                    ar.add((Act)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Plateau> castPlateau(ArrayList<Object>ray){
        ArrayList<Plateau>ar=new ArrayList<Plateau>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Plateau){
                    ar.add((Plateau)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Status> castStatus(ArrayList<Object>ray){
        ArrayList<Status>ar=new ArrayList<Status>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Status){
                    ar.add((Status)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Acteur> castActeur(ArrayList<Object>ray){
        ArrayList<Acteur>ar=new ArrayList<Acteur>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Acteur){
                    ar.add((Acteur)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static  ArrayList<Sentiment> castSentiment(ArrayList<Object>ray){
        ArrayList<Sentiment>ar=new ArrayList<Sentiment>();
        if(ray.size()>0){
            for(int i=0;i<ray.size();i++){
                if(ray.get(i) instanceof Sentiment){
                    ar.add((Sentiment)ray.get(i));
                }
            }
        }
        return ar;
    }
    public static String FtoBase64(byte[]bytes){
       
       return Base64.getEncoder().encodeToString(bytes);
    }
    public static Date toGod(String dt){
        ZonedDateTime dateTime  = ZonedDateTime.parse(dt+":00.000Z");
        LocalDateTime localDateTime  = dateTime.toLocalDateTime();
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        Date date = Date.from(zdt.toInstant());
		return date;
    }
    public static Timestamp toT(String dt){
        ZonedDateTime dateTime  = ZonedDateTime.parse(dt+":00.000Z");
        LocalDateTime localDateTime  = dateTime.toLocalDateTime();
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());
        return timestamp;
    }
    public static Object traductionParameterDynamique(Map<String,String> attributValue)throws Exception{
        Class ray=Class.forName(attributValue.get("class"));
        Object temp=ray.getDeclaredConstructor().newInstance();
        Field[]fields=ray.getDeclaredFields();
        Method[]methods=ray.getDeclaredMethods();
       for (int i=0;i<fields.length ;i++) 
       {
               for (int j=0;j<methods.length ;j++ ) 
               {
                   if (("set"+fields[i].getName()).compareToIgnoreCase(methods[j].getName())==0)
                   {
                           if(attributValue!=null){
                                       if(attributValue.get(fields[i].getName())!=null){
                                               if (fields[i].getType().getName()=="int")
                                               {
                                                   try{
                                                       int a=Integer.parseInt(attributValue.get(fields[i].getName()));
                                                       methods[j].invoke(temp,a);
                                                   }catch(Exception er){
                                                       
                                                   }              
                                               }else if (fields[i].getType().getName()=="float")
                                               {
                                                   try{
                                                       float a=Float.parseFloat(attributValue.get(fields[i].getName()));
                                                       methods[j].invoke(temp,a);
                                                   }catch(Exception er){

                                                   }              
                                               }else if (fields[i].getType().getName()=="double")
                                               {
                                                   try{
                                                       double a=Double.parseDouble(attributValue.get(fields[i].getName()));
                                                       methods[j].invoke(temp,a);
                                                   }catch(Exception er){

                                                   }              
                                               }else if (fields[i].getType().getName()=="long")
                                               {
                                                   try{
                                                       long a=Long.parseLong(attributValue.get(fields[i].getName()));
                                                       methods[j].invoke(temp,a);
                                                   }catch(Exception er){

                                                   }              
                                               }else if (fields[i].getType().getName()=="java.lang.String")
                                               {
                                                   try{
                                                       methods[j].invoke(temp,attributValue.get(fields[i].getName()));
                                                   }catch(Exception er){

                                                   }              
                                               }else if(fields[i].getType().getName()=="java.sql.Timestamp"){
                                                try{
                                                    java.sql.Timestamp a=toT(attributValue.get(fields[i].getName()));
                                                    methods[j].invoke(temp,a);
                                                }catch(Exception er){

                                                }  
                                            }
                                            else if(fields[i].getType().getName()=="java.util.Date"){
                                                try{
                                                    Date a=toGod(attributValue.get(fields[i].getName()));
                                                    methods[j].invoke(temp,a);
                                                }catch(Exception er){

                                                }  
                                            }
                                       }
                           }
                           
                   }        
               }    
       }
       return temp;

    }
    
    public static String StringtoDate(String date){
        String[]total=date.split("-");
        String expect=total[1];
        if(Integer.parseInt(total[1])<10){
            expect="0"+total[1];
        }
        String vao=total[2]+"/"+expect+"/"+total[0];
        
        return vao;
    }
    
    public static ArrayList<Date> listDate(String debut,String fin)throws Exception{
        ArrayList<Date>list=new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = dateFormat.parse(debut);
            Date endDate = dateFormat.parse(fin);
            long interval = 24 * 1000 * 60 * 60; // 1 day in milliseconds
            long endTime = endDate.getTime();
            for (long time = startDate.getTime(); time <= endTime; time += interval) {
                Date date = new Date(time);
                list.add(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<Integer> listplateau(ArrayList<Scene> ray){
        HashMap<Integer,Boolean> check=new HashMap<Integer,Boolean>();
        ArrayList<Integer>integ=new ArrayList<>();
        for (int i = 0; i < ray.size(); i++) {
            if(check.get(ray.get(i).getIdplateau())==null){
                integ.add(ray.get(i).getIdplateau());
                check.put(ray.get(i).getIdplateau(), Boolean.TRUE);
            }
        }
        return integ;
    }
    public static ArrayList<Scene> correspondance(int idplateau,ArrayList<Scene> ray,Date dt){
        ArrayList<Scene>scene=new ArrayList<>();
        for (int i = 0; i < ray.size(); i++) {
                    if(ray.get(i).getIdplateau()==idplateau){
                        if(IndispoActeur.ifscenedisponible(ray.get(i), dt)==true){
                            scene.add(ray.get(i));
                        }
                        
                    }
        }
        return scene;
    }
    public static ArrayList<Sousplanning> correspondanceDate(Date d,ArrayList<Sousplanning> ray){
        ArrayList<Sousplanning>scene=new ArrayList<>();
        for (int i = 0; i < ray.size(); i++) {
                    if(ray.get(i).getDateplanning().equals(d)){
                        scene.add(ray.get(i));
                    }
        }
        return scene;
    }
    public static String decoupage(String[]ray){
        String chaine = String.join(";", ray);
        return chaine;
    }
    public static boolean isweekend(Date date){
        int dayOfWeek = date.getDay();
        if (date.getDay() == 0 || date.getDay()== 6) {
           return true;
        } 
        return false;
    }
    public static HashMap<Date,ArrayList<Sousplanning>> reconstruction(ArrayList<Sousplanning> ray){
       HashMap<Date,ArrayList<Sousplanning>>scene=new HashMap<Date,ArrayList<Sousplanning>>();
       Sousplanning[]plan=new Sousplanning[ray.size()];
       int count=0;
       for (int i = 0; i <ray.size(); i++) {
            plan[count]=ray.get(i);
            count++;
       }
       for (int k=0 ; k < (plan.length); k++)
        {
            for (int e=0 ; e < (plan.length); e++)
                {

                        if ((plan[e].getDateplanning()).after(plan[k].getDateplanning())) 
                        {
							Sousplanning tmp = plan[k];
							plan[k] = plan[e];
							plan[e] = tmp;
			}
                }
        }
       ArrayList<Date>dt=getUniqueDate(plan);
        for (int i = 0; i < dt.size(); i++) {
            ArrayList<Sousplanning>sous=correspondanceDate(dt.get(i), ray);
            scene.put(dt.get(i),sous);
        }
       return scene;
    }
    public static ArrayList<Date> makakey(ArrayList<Sousplanning> ray){
       HashMap<Date,ArrayList<Sousplanning>>scene=new HashMap<Date,ArrayList<Sousplanning>>();
       Sousplanning[]plan=new Sousplanning[ray.size()];
       int count=0;
       for (int i = 0; i <ray.size(); i++) {
            plan[count]=ray.get(i);
            count++;
       }
       for (int k=0 ; k < (plan.length); k++)
        {
            for (int e=0 ; e < (plan.length); e++)
                {

                        if ((plan[e].getDateplanning()).after(plan[k].getDateplanning())) 
                        {
							Sousplanning tmp = plan[k];
							plan[k] = plan[e];
							plan[e] = tmp;
			}
                }
        }
        ArrayList<Date>dt=getUniqueDate(plan);
        System.out.println("debug");
       return dt;
    }
    public static ArrayList<Date> getUniqueDate(Sousplanning[]ray){
        HashMap<Date,Boolean> check=new HashMap<Date,Boolean>();
        ArrayList<Date>dt=new ArrayList<>();
        if(ray.length>0){
            for (int i = 0; i < ray.length; i++) {
                if(check.get(ray[i].getDateplanning())==null){
                    dt.add(ray[i].getDateplanning());
                    check.put(ray[i].getDateplanning(), Boolean.TRUE);
                }
            }
        }
        return dt;
    }
}
