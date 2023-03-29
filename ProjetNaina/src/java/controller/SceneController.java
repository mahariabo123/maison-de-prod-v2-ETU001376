/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import HibernateDao.GenericDao;
import Service.*;
import entity.Planning;
import entity.Scene;
import entity.Sousplanning;
import inter.InterfaceDao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import util.Util;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

@Controller
public class SceneController {
    @Autowired
    SceneService ws;
    @Autowired
    PlateauService ps;
    @Autowired
    StatusService ss;
    @Autowired
    GeneraliseService gs;
    @Autowired
    AuteurService as;
    @Autowired
    @Qualifier("genericDao")
    InterfaceDao gen;
    @RequestMapping(value="/avantplanning")
    public String  afterplann(Model mod,@RequestParam Map<String,String> allParams){
        mod.addAttribute("idwatch",allParams.get("idwatch"));
        try{
            mod.addAttribute("listScene",ws.listEcritureFinie(Integer.parseInt(allParams.get("idwatch"))));
        }catch(Exception e){
            
        }
        return "avantplanning";
    }
    @RequestMapping(value="/planning")
    public String  planning(Model mod,@RequestParam Map<String,String> allParams,@RequestParam String[] idscene){
        System.out.println("debut "+allParams.get("debut"));
        System.out.println("fin "+allParams.get("fin"));
        try{
            Planning ray=ws.planning(allParams.get("debut"),allParams.get("fin"), idscene,Integer.parseInt(allParams.get("idwatch")));
            ArrayList<Date>dt=ws.makakey(ray);
            HashMap<Date,ArrayList<Sousplanning>> all=ws.allplan(ray);
            mod.addAttribute("listDate",dt);
            mod.addAttribute("map",all);
            mod.addAttribute("idplanning",ray.getIdplanning());
        }catch(Exception e){
           e.printStackTrace();
        }
        return "planning";
    }
    @RequestMapping(value="/here")
    public String  here(Model mod,@RequestParam Map<String,String> allParams){
        try{
            mod.addAttribute("idplanning",Integer.parseInt(allParams.get("idplanning")));
            mod.addAttribute("date",allParams.get("date"));
            mod.addAttribute("listScene",ws.listnoncouvert(Integer.parseInt(allParams.get("idplanning"))));
        }catch(Exception e){
           e.printStackTrace();
        }
        return "sceneplanning";
    }
    @RequestMapping(value="/addhere")
    public String  addhere(Model mod,@RequestParam Map<String,String> allParams){
        try{
            int idplanning=Integer.parseInt(allParams.get("idplanning"));
            DateFormat ray=new SimpleDateFormat("yyyy-MM-dd");
            Date date=ray.parse(allParams.get("date"));
            int idsc=Integer.parseInt(allParams.get("idscene"));
            ws.check(idsc, idplanning, date);
        }catch(Exception e){
           e.printStackTrace();
        }
        return "redirect:/planinsert?idplanning="+allParams.get("idplanning");
    }
    @RequestMapping(value="/annulerplanning")
    public String  annulerplanning(Model mod,@RequestParam Map<String,String> allParams){
        try{
            int idplanning=Integer.parseInt(allParams.get("idplanning"));
            ws.delete(idplanning);
        }catch(Exception e){
           e.printStackTrace();
        }
        return "redirect:/index";
    }
    @RequestMapping(value="/validerplanning")
    public String  validerplanning(Model mod,@RequestParam Map<String,String> allParams){
        try{
            int idplanning=Integer.parseInt(allParams.get("idplanning"));
            ws.valider(idplanning);
        }catch(Exception e){
           e.printStackTrace();
        }
        return "redirect:/index";
    }
    @RequestMapping(value="/deletesousplanning")
    public String  deleteplanning(Model mod,@RequestParam Map<String,String> allParams){
        try{
            Sousplanning ray=new Sousplanning(-1, -1,null);
            ray.setIdsousplanning(Integer.parseInt(allParams.get("idsousplanning")));
            gs.delete(ray);
        }catch(Exception e){
           e.printStackTrace();
        }
        return "redirect:/planinsert?idplanning="+allParams.get("idplanning");
    }
    @RequestMapping(value="/planinsert")
    public String  listforplanning(Model mod,@RequestParam Map<String,String> allParams){
        try{
            Planning ray=new Planning();
            ray.setIdplanning(Integer.parseInt(allParams.get("idplanning")));
            ArrayList<Date>dt=ws.makakey(ray);
            HashMap<Date,ArrayList<Sousplanning>> all=ws.allplan(ray);
            mod.addAttribute("listDate",dt);
            mod.addAttribute("map",all);
            mod.addAttribute("idplanning",ray.getIdplanning());
        }catch(Exception e){
           e.printStackTrace();
        }
        return "planning";
    }
    @RequestMapping(value="/addscene")
    public String  addScene(Model mod,@RequestParam Map<String,String> allParams){
        Scene sc=new Scene();
        mod.addAttribute("class",sc.getClass().getName());
        String request="redirect:/list?idwatch="+allParams.get("idwatch");
        try{
            mod.addAttribute("idwatch",Integer.parseInt(allParams.get("idwatch")));
            mod.addAttribute("listPlateau",ps.all());
            mod.addAttribute("listAuteur",as.all());
            mod.addAttribute("redirect",request);
        }catch(Exception e){
            
        }
        return "addScene";
    }
    @RequestMapping(value="/modifscene")
    public String  modifScene(Model mod,@RequestParam Map<String,String> allParams){
        Scene sc=new Scene();
        mod.addAttribute("class",sc.getClass().getName());
        
        try{
            Scene ra=new Scene(null,-1,-1,-1,null,-1,-1);
            ra.setIdscene(Integer.parseInt(allParams.get("idscene")));
            Scene vao2=((Scene)gen.find(ra).get(0));
            mod.addAttribute("listPlateau",ps.all());
            mod.addAttribute("listStatus",ss.all());
            mod.addAttribute("listAuteur",as.all());
            mod.addAttribute("modif",vao2);
            String request="redirect:/list?idwatch="+Integer.toString(vao2.getIdwatch());
            mod.addAttribute("redirect",request);
        }catch(Exception e){
            
        }
        return "updateScene";
    }
    @RequestMapping(value="/details")
    public String  details(Model mod,@RequestParam Map<String,String> allParams){
        Scene sc=new Scene();
        mod.addAttribute("class",sc.getClass().getName());
        
        try{
            Scene ra=new Scene(null,-1,-1,-1,null,-1,-1);
            ra.setIdscene(Integer.parseInt(allParams.get("idscene")));
            Scene vao2=((Scene)gen.find(ra).get(0));
            mod.addAttribute("listPlateau",ps.all());
            mod.addAttribute("modif",vao2);
            String request="redirect:/list?idwatch="+Integer.toString(vao2.getIdwatch());
            mod.addAttribute("redirect",request);
        }catch(Exception e){
            
        }
        return "detail";
    }
    @RequestMapping(value="/list")
    public String  list (Model mod,@RequestParam Map<String,String> allParams){
        int nbPage=3;
        String sessionrecherche="0";
        int num =1;
        try{
            mod.addAttribute("listStatus",ss.all());
        }catch(Exception e){
            
        }
        mod.addAttribute("idwatch",Integer.parseInt(allParams.get("idwatch")));
        
       //int recherche=0;
       
       if(allParams.get("numPage")!=""&&allParams.get("numPage")!=null){
           num=Integer.parseInt(allParams.get("numPage"));
       }
       if((allParams.get("recherche")!=""&&allParams.get("recherche")!=null)){
           try{
               num=1;
               sessionrecherche=allParams.get("recherche");
               mod.addAttribute("listScene",ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),-1,null,Integer.parseInt(allParams.get("recherche")),-1), num, nbPage));
               mod.addAttribute("nombrePage",ws.nombreDePage(ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),-1,null,Integer.parseInt(allParams.get("recherche")),-1), num, 0), nbPage));
               if(ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),-1,null,Integer.parseInt(allParams.get("recherche")),-1), num+1, nbPage).size()>0){
                   mod.addAttribute("ariana",1);
               }else{
                   mod.addAttribute("ariana",0);
               }
           }catch(Exception e){ 
               e.printStackTrace();
           }
     
       }else if((allParams.get("sessionrecherche")!=""&&allParams.get("sessionrecherche")!=null&&allParams.get("sessionrecherche").compareToIgnoreCase("0")!=0)){
           try{
               sessionrecherche=allParams.get("sessionrecherche");
                mod.addAttribute("listScene",ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),-1,null,Integer.parseInt(allParams.get("sessionrecherche")),-1), num, nbPage));
               mod.addAttribute("nombrePage",ws.nombreDePage(ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),-1,null,Integer.parseInt(allParams.get("sessionrecherche")),-1), num, 0), nbPage));
               if(ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),-1,null,Integer.parseInt(allParams.get("sessionrecherche")),-1), num+1, nbPage).size()>0){
                   mod.addAttribute("ariana",1);
               }else{
                   mod.addAttribute("ariana",0);
               }
           }catch(Exception e){ 
               
           }
    }else {
           try{
               mod.addAttribute("listScene",ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),-1,null,-1,-1), num, nbPage));
               mod.addAttribute("nombrePage",ws.nombreDePage(ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),-1,null,-1,-1), num, 0), nbPage));
               if(ws.pagination(new Scene(null,-1,Integer.parseInt(allParams.get("idwatch")),-1,null,-1,-1), num+1, nbPage).size()>0){
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
       
       return "resultScene";
    }
    @RequestMapping(value="/pdf-test")
    public String  generateDialoguePdf(@RequestParam Map<String,String> allParams) throws Exception {
        Scene ray=ws.getScene(Integer.parseInt(allParams.get("idscene")));
        ws.genereTotalAct(ray);
        //response.setContentType("application/pdf");
        //response.setHeader("Content-Disposition", "attachment; filename=\"dialogue.pdf\"");
        return "redirect:/list?idwatch="+ray.getIdwatch();
    }
}
