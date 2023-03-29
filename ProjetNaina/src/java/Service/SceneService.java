/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import HibernateDao.GenericDao;
import entity.*;
import inter.InterfaceDao;
import util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
/**
 *
 * @author USER
 */
@Component
public class SceneService {
    @Autowired
    @Qualifier("genericDao")
    InterfaceDao gen;
    public ArrayList<Scene> all(Scene ray)throws Exception{
        ArrayList<Scene>list=new ArrayList<>();
        try{
            list=Util.castScene(gen.find(ray)); 
        }catch(Exception e){
            
        }
        return list;
    }
    public Planning planning(String debut,String fin,String[] idscene,int idwatch)throws Exception{
        int[]sc=Util.traduction(idscene);
        ArrayList<Scene>list=new ArrayList<>();
        try{
            if(sc.length>0){
                for (int i = 0; i < sc.length; i++) {
                    Scene ray=new Scene(null,-1,-1,-1,null,-1,-1);
                    ray.setIdscene(sc[i]);
                    list.add((Scene)gen.find(ray).get(0));
                }
            }
            
        }catch(Exception e){
            
        }
        
        ArrayList<Integer>listplateau=Util.listplateau(list);
        System.out.println("taille infini "+listplateau.size());
        ArrayList<Date>dt=Util.listDate(debut, fin);
        Planning plan=new Planning();
        if(dt.size()==1){
            plan=new Planning(dt.get(0), dt.get(0),idwatch, 0);
        }else{
            plan=new Planning(dt.get(0), dt.get(dt.size()-1),idwatch, 0);
        }
        gen.save(plan);
        ArrayList<Object>obj=gen.find(new Planning(null,null,-1,0));
        Planning farany=(Planning)(obj.get(obj.size()-1));
        Horaire h=(Horaire)(gen.find(new Horaire(-1)).get(0));
        for (int i = 0; i < dt.size(); i++) {
            ArrayList<Scene>vao2=new ArrayList<>();
            long heure=(long)(h.getHeure()*3600*1000);
            System.out.println("eto");
            System.out.println(dt.get(i).toString());
            System.out.println(new Ferie(dt.get(i)).isferie());
            if(new Ferie(dt.get(i)).isferie()==false&&Util.isweekend(dt.get(i))==false){
                for (int j = 0; j < listplateau.size(); j++) {
                        if(Liberte.isdisponible(listplateau.get(j), dt.get(i))==false){
                            continue;
                        }
                        ArrayList<Scene>temp=Util.correspondance(listplateau.get(j), list,dt.get(i));
                        if(temp.size()>0){
                            boolean exit=false;
                            for (int e = 0; e < temp.size(); e++) {
                                heure=heure-temp.get(e).dure();
                                if(heure<0){
                                    heure=heure+temp.get(e).dure();
                                    exit=true;
                                }else{

                                    //vao2.add(temp.get(0));
                                    Sousplanning pl=new Sousplanning(farany.getIdplanning(),temp.get(e).getIdscene(),dt.get(i));
                                    gen.save(pl);
                                    for (int k = 0; k < list.size(); k++) {
                                        if(list.get(k).getIdscene()==temp.get(e).getIdscene()){
                                            list.remove(k);
                                        }
                                    }

                                }
                            }
                            if(exit==true){
                                break;
                            }
                            
                        }
                }
            }
            
        }
        return farany;
    }
    
    public HashMap<Date,ArrayList<Sousplanning>> allplan(Planning ray)throws Exception{
       HashMap<Date,ArrayList<Sousplanning>>scene=new HashMap<Date,ArrayList<Sousplanning>>();
       ArrayList<Sousplanning>sp=Util.castSousplanning(gen.find(new Sousplanning(ray.getIdplanning(),-1, null)));
        scene=Util.reconstruction(sp);
       return scene;
    }
    public ArrayList<Date> makakey(Planning ray)throws Exception{
        ArrayList<Date>scene=new ArrayList<Date>();
       ArrayList<Sousplanning>sp=Util.castSousplanning(gen.find(new Sousplanning(ray.getIdplanning(),-1, null)));
        scene=Util.makakey(sp);
       return scene;
    }
    public ArrayList<Sousplanning> allsousplan(Planning ray)throws Exception{
       ArrayList<Sousplanning>sp=Util.castSousplanning(gen.find(new Sousplanning(ray.getIdplanning(),-1, null)));
       return sp;
    }
    public ArrayList<Scene> pagination(Scene ra,int page,int nbPage)throws Exception{
       
        String str="";
       
           str="select * from Scene WHERE idwatch="+Integer.toString(ra.getIdwatch());
       
           if(ra.getIdstatus()!=-1){
                  str="select * from Scene WHERE  idstatus="+Integer.toString(ra.getIdstatus())+" and idwatch="+Integer.toString(ra.getIdwatch());
           }
        
           
       
        ArrayList<Object>list=gen.pagination2(ra,str,page,nbPage);   
        ArrayList<Scene>liste=new ArrayList<Scene>();
        for (int i = 0; i <list.size(); i++) {
            liste.add((Scene)list.get(i));
        }
        return liste;
    }
    public int nombreDePage(ArrayList<Scene>ray,int nbPage)throws Exception{
        int mod=(ray.size()%nbPage);
        int valiny=(int)(ray.size()/nbPage);
        if(mod==0){
            return valiny;
        }
        return valiny+1;
           
    }
    public Scene getScene(int idscene){
        Scene ray=new Scene(null,-1,-1,-1, null,-1,-1);
        ray.setIdscene(idscene);
        Scene search=new Scene();
        try{
            search=(Scene)gen.find(ray).get(0);
        }catch(Exception e){
            
        }
        return search;
    }
    public ArrayList<Scene> listEcritureFinie(int idwatch){
        ArrayList<Scene>list=new ArrayList<>();
        try{
            list=Util.castScene(gen.find(new Scene(null,-1,idwatch,-1,null,3,-1))); 
        }catch(Exception e){
            
        }
        ArrayList<Scene>vao2=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getPlannification()==null){
                vao2.add(list.get(i));
            }
        }
        return vao2;
    }
    
    public ArrayList<Scene> listnoncouvert(int idplanning)throws Exception{
        Planning p=new Planning(null,null,-1,-1);
        p.setIdplanning(idplanning);
        Planning vao2=new Planning();
        try{
            vao2=(Planning)gen.find(p).get(0);
        }catch(Exception e){
            
        }
        ArrayList<Scene> sc=new ArrayList<>();
        ArrayList<Sousplanning>sp=Util.castSousplanning(gen.find(new Sousplanning(idplanning,-1, null)));
        ArrayList<Scene> finie=this.listEcritureFinie(vao2.getIdwatch());
        for (int i = 0; i < finie.size(); i++) {
            boolean check=false;
            for (int j = 0; j < sp.size(); j++) {
                if(finie.get(i).getIdscene()==sp.get(j).getIdscene()){
                    check=true;
                }
            }
            if(check==false){
                sc.add(finie.get(i));
            }
        }
        return sc;
        
    }
    public  void check(int idscene,int idplanning,Date dt)throws Exception{
        Scene sc=this.getScene(idscene);
        ArrayList<Sousplanning>sp=Util.castSousplanning(gen.find(new Sousplanning(idplanning,-1,dt)));
        long total=0;
        for (int i = 0; i < sp.size(); i++) {
            Scene hakai = this.getScene(sp.get(i).getIdscene());
            total+=hakai.dure();
        }
        Horaire h=(Horaire)(gen.find(new Horaire(-1)).get(0));
        long heure=(long)(h.getHeure()*3600*1000);
        if(sc.dure()<=(heure-total)){
            if(Liberte.isdisponible(sc.getIdplateau(), dt)==true&&IndispoActeur.ifscenedisponible(sc, dt)==true){
                Sousplanning pl=new Sousplanning(idplanning,idscene,dt);
                gen.save(pl);
            }
            
        }
    }
    public  void delete(int idplanning)throws Exception{
        Planning p=new Planning(null,null,-1,-1);
        p.setIdplanning(idplanning);
        ArrayList<Sousplanning>sp=Util.castSousplanning(gen.find(new Sousplanning(idplanning,-1,null)));
        for (int i = 0; i < sp.size(); i++) {
            gen.delete(sp.get(i));
        }
        gen.delete(p);
        
    }
    public  void valider(int idplanning)throws Exception{
        Planning p=new Planning(null,null,-1,-1);
        p.setIdplanning(idplanning);
        Planning vao2=new Planning();
        try{
            vao2=(Planning)gen.find(p).get(0);
        }catch(Exception e){
            
        }
        vao2.setValider(1);
        gen.update(vao2);
        ArrayList<Sousplanning>sp=Util.castSousplanning(gen.find(new Sousplanning(idplanning,-1,null)));
        for (int i = 0; i < sp.size(); i++) {
            Scene hakai = this.getScene(sp.get(i).getIdscene());
            hakai.setPlannification(sp.get(i).getDateplanning());
            hakai.setIdstatus(4);
            gen.update(hakai);
            try{
                Liberte lib=((Liberte)(new GenericDao().find(new Liberte(hakai.getIdplateau(),null,sp.get(i).getDateplanning())).get(0)));
            }catch(Exception e){
                Liberte vao=new Liberte(hakai.getIdplateau(),null, sp.get(i).getDateplanning());
                gen.save(vao);
            }
        }
    }
    public static void generePdf(Scene ray,String nom)throws Exception{
        String namefile="E:\\filmProject\\film-"+ray.watch()+"-scene-"+Integer.toString(ray.getIdscene())+"-"+nom+".pdf";
        try {
         // Créer un objet File pour représenter le fichier
         File file = new File(namefile);

         // Vérifier si le fichier existe déjà ou non
         if (file.exists()) {
            System.out.println("Le fichier existe déjà.");
         } else {
            // Créer un nouveau fichier
            FileOutputStream fos = new FileOutputStream(file);
            fos.close();
            System.out.println("Le fichier a été créé avec succès !");
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
        String dateplanning=(ray.getPlannification()).toString();
        String scene="scene numero "+ray.getNumero()+":"+ray.getIntitule();
        String plateau=ray.plateau();
        int x=200;
        int y=690;
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("Sample Dialogue") / 1000 * 16;
        float titleHeight = PDType1Font.HELVETICA_BOLD.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 16;
        float titleX = (page.getMediaBox().getWidth() - titleWidth) / 2;
        float titleY = page.getMediaBox().getHeight() - titleHeight - 20;
        titleY=titleY;
        contentStream.newLineAtOffset(titleX, titleY);
        contentStream.showText(scene);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(titleX, titleY-15);
        contentStream.showText(dateplanning);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(titleX, titleY-30);
        contentStream.showText("plateau :"+plateau);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(titleX, titleY-45);
        contentStream.showText("Mr/Mdme "+nom);
        contentStream.endText();
        ArrayList<Act>list=new ArrayList<>();
        try{
            list=Util.castAct(new GenericDao().find(new Act(null,ray.getIdscene(),-1,-1,null))); 
        }catch(Exception e){
            
        }
        int taille=list.size();
        int i=0;
        if(taille==0){
            contentStream.close(); 
        }
        while(i<taille){
            
                 contentStream.beginText();
                 contentStream.setFont(PDType1Font.HELVETICA, 12);
                 contentStream.newLineAtOffset(20, y);
                 String text="";
                 text=list.get(i).acteur()+":"+list.get(i).getIntitule()+" durée:"+list.get(i).duree()+"("+list.get(i).sentiment()+")";
                 contentStream.showText(text);
                 contentStream.endText();
                 y-=10;
                 i++;
            if(y==20){
                contentStream.close();
                y=690;
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
            }else{
                if(i==taille){
                    contentStream.close();
                }
            }
        }
        
        document.save(namefile);
        document.close();
    }
    
    public static void genereTotalAct(Scene ray)throws Exception{
        ArrayList<Act>list=new ArrayList<>();
        try{
            list=Util.castAct(new GenericDao().find(new Act(null,ray.getIdscene(),-1,-1,null))); 
        }catch(Exception e){
            
        }
        for (int i = 0; i < list.size(); i++) {
            generePdf(ray, list.get(i).acteur());
        }
    }
}
