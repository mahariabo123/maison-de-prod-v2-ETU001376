/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import HibernateDao.GenericDao;
import Service.*;
import entity.*;
import inter.InterfaceDao;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class ActController {
    @Autowired
    ActService ws;
    @Autowired
    SentimentService ss;
    @Autowired
    ActeurService as;
    @Autowired
    @Qualifier("genericDao")
    InterfaceDao gen;
    @RequestMapping(value="/listAct")
    public String  listeWatch(Model mod,@RequestParam Map<String,String> allParams){
        Act ray=new Act(null,Integer.parseInt(allParams.get("idscene")),-1,-1,null);
        try{
            mod.addAttribute("idscene",Integer.parseInt(allParams.get("idscene")));
            mod.addAttribute("listAct",ws.all(ray));
        }catch(Exception e){
            
        }
        return "listeAct";
    }
    @RequestMapping(value="/addact")
    public String  addAct(Model mod,@RequestParam Map<String,String> allParams){
        Act sc=new Act();
        mod.addAttribute("class",sc.getClass().getName());
        String request="redirect:/list2?idscene="+allParams.get("idscene");
        try{
            mod.addAttribute("idscene",Integer.parseInt(allParams.get("idscene")));
            mod.addAttribute("listSentiment",ss.all());
            mod.addAttribute("listActeur",as.all());
            mod.addAttribute("redirect",request);
        }catch(Exception e){
            
        }
        return "addAct";
    }
    @RequestMapping(value="/modifact")
    public String  modifAct(Model mod,@RequestParam Map<String,String> allParams){
        Act sc=new Act();
        mod.addAttribute("class",sc.getClass().getName());
        
        try{
            Act ra=new Act(null,-1,-1,-1,null);
            ra.setIdact(Integer.parseInt(allParams.get("idact")));
            Act vao2=((Act)gen.find(ra).get(0));
            mod.addAttribute("listSentiment",ss.all());
            mod.addAttribute("listActeur",as.all());
            mod.addAttribute("modif",vao2);
            String request="redirect:/list2?idscene="+Integer.toString(vao2.getIdscene());
            mod.addAttribute("redirect",request);
        }catch(Exception e){
            
        }
        return "updateAct";
    }
    @RequestMapping(value="/list2")
    public String  list (Model mod,@RequestParam Map<String,String> allParams){
        int nbPage=3;
        String sessionrecherche="tsisy";
        int num =1;
        mod.addAttribute("idscene",Integer.parseInt(allParams.get("idscene")));
        
       //int recherche=0;
       
       if(allParams.get("numPage")!=""&&allParams.get("numPage")!=null){
           num=Integer.parseInt(allParams.get("numPage"));
       }
       if((allParams.get("recherche")!=""&&allParams.get("recherche")!=null)){
           try{
               num=1;
               sessionrecherche=allParams.get("recherche");
               mod.addAttribute("listAct",ws.pagination(new Act(allParams.get("recherche"),Integer.parseInt(allParams.get("idscene")),-1,-1,null), num, nbPage));
               mod.addAttribute("nombrePage",ws.nombreDePage(ws.pagination(new Act(allParams.get("recherche"),Integer.parseInt(allParams.get("idscene")),-1,-1,null), num, 0), nbPage));
               if(ws.pagination(new Act(allParams.get("recherche"),Integer.parseInt(allParams.get("idscene")),-1,-1,null), num+1, nbPage).size()>0){
                   mod.addAttribute("ariana",1);
               }else{
                   mod.addAttribute("ariana",0);
               }
           }catch(Exception e){ 
               e.printStackTrace();
           }
     
       }else if((allParams.get("sessionrecherche")!=""&&allParams.get("sessionrecherche")!=null&&allParams.get("sessionrecherche").compareToIgnoreCase("tsisy")!=0)){
           try{
               sessionrecherche=allParams.get("sessionrecherche");
                mod.addAttribute("listAct",ws.pagination(new Act(allParams.get("sessionrecherche"),Integer.parseInt(allParams.get("idscene")),-1,-1,null), num, nbPage));
               mod.addAttribute("nombrePage",ws.nombreDePage(ws.pagination(new Act(allParams.get("sessionrecherche"),Integer.parseInt(allParams.get("idscene")),-1,-1,null), num, 0), nbPage));
               if(ws.pagination(new Act(allParams.get("sessionrecherche"),Integer.parseInt(allParams.get("idscene")),-1,-1,null), num+1, nbPage).size()>0){
                   mod.addAttribute("ariana",1);
               }else{
                   mod.addAttribute("ariana",0);
               }
           }catch(Exception e){ 
               
           }
    }else {
           try{
               mod.addAttribute("listAct",ws.pagination(new Act(null,Integer.parseInt(allParams.get("idscene")),-1,-1,null), num, nbPage));
               mod.addAttribute("nombrePage",ws.nombreDePage(ws.pagination(new Act(null,Integer.parseInt(allParams.get("idscene")),-1,-1,null), num, 0), nbPage));
               if(ws.pagination(new Act(null,Integer.parseInt(allParams.get("idscene")),-1,-1,null), num+1, nbPage).size()>0){
                   mod.addAttribute("ariana",1);
               }else{
                   mod.addAttribute("ariana",0);
               }
           }catch(Exception e){
               
           }
        }
       mod.addAttribute("sessionrecherche",sessionrecherche);
       mod.addAttribute("nbPage",nbPage);
       mod.addAttribute("numPage",num);
       
       return "resultAct";
    }
}
