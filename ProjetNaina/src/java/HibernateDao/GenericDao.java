package HibernateDao;
import java.sql.*;
import java.lang.reflect.*;
import java.util.Date;
import database.*;
import annotation.*;
import inter.InterfaceDao;
import java.util.ArrayList;
import org.springframework.stereotype.Component;
@Component
public class GenericDao implements InterfaceDao{
    public Connection c;
    public String url="jdbc:postgresql://localhost:5432/film";
    public GenericDao(){
        
    }
    @Override
     public void upt(int etat,int id)throws Exception{
         
     }
     public static String toLowerFirst(String a) {
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
        return first.toLowerCase() + other;
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
    @Override
    public void save(Object ray)throws Exception
    {
        String sequence="";
        Vue vue=(Vue) ray.getClass().getAnnotation(Vue.class);
        // String id = (String) ray.getClass().getMethod("getId").invoke(ray);
        Field[] fields = ray.getClass().getDeclaredFields();
        int fieldlength=fields.length-1;
        if(vue.isvue()==true){
            fieldlength=fields.length;
        }
        Method[] method = new Method[fieldlength];
        Object[] value = new Object[fieldlength];
        String req = "";
        int compte=0;
        NomTable nomTable=(NomTable) ray.getClass().getAnnotation(NomTable.class);
        String nom=nomTable.nom();
        sequence="nextval('id"+toLowerFirst(nom)+"')";
        if(vue.isvue()==true){
            req ="INSERT INTO "+nomTable.nom()+" VALUES (";
        }else{
            req = "INSERT INTO "+nomTable.nom()+" VALUES ("+sequence+", ";
        }
        
        
        Attribut att=null;
       int countForeignkey=0;
        for(int i=0;i<fields.length;i++) {
            att=fields[i].getAnnotation(Attribut.class);
           if(att.isprimarykey()==false) 
           {
               if(att.isforeignkey()==true)
               {
                    method[compte]=ray.getClass().getMethod("get"+toUpperFirst(fields[i].getName()));
                    value[compte]=method[compte].invoke(ray);
                    compte++;
                    countForeignkey++;
               }else{
                    method[compte]=ray.getClass().getMethod("get"+toUpperFirst(fields[i].getName()));
                    value[compte]=method[compte].invoke(ray);
                    compte++;
               }
                

           }
        }
         int[]fkey=null;
        if(countForeignkey>0){
            fkey=new int[countForeignkey];
            int count=0;
            for(int i=0;i<fields.length;i++) {
                att=fields[i].getAnnotation(Attribut.class);
                if (att.isprimarykey()==false) 
                {
                    if(att.isforeignkey()==true){
                            fkey[count]=i;
                            count++;
                    }
                }
            }
        }
        
            if(fields.length==1) {
                if (value[0]!=null) 
                {
                    //String[] string = value[0].toString().split(" ");
                    if(value[0] instanceof Number) {
                    req += value[0]+")";
                    }else if(value[0] instanceof java.sql.Timestamp){
                        int jour = ((java.sql.Timestamp) value[0]).getDate();
                        int mois = ((java.sql.Timestamp) value[0]).getMonth()+1;
                        int annee = ((java.sql.Timestamp) value[0]).getYear()+1900;
                        int hours=((java.sql.Timestamp) value[0]).getHours();
                        int minutes=((java.sql.Timestamp) value[0]).getMinutes();
                        int secondes=((java.sql.Timestamp) value[0]).getSeconds();
                        req += "TO_TIMESTAMP('"+jour+"-"+mois+"-"+annee+" "+hours+":"+minutes+":"+secondes+"','DD-MM-YYYY HH24:MI:SS'))";

                    }
                    else if(value[0] instanceof java.util.Date) {
                        int jour = ((java.util.Date) value[0]).getDate();
                        int mois = ((java.util.Date) value[0]).getMonth()+1;
                        int annee = ((java.util.Date) value[0]).getYear()+1900;
                        req += "TO_DATE('"+jour+"-"+mois+"-"+annee+"','DD-MM-YYYY'),";
                    }
                    else {
                     req += "'"+value[0]+"')";
                    }    
                }else{
                    req += "null)";
                }
            }
            else {
                
                
                for(int i=0;i<value.length;i++) {
                    if(i<value.length-1) {
                       
                               if (value[i]!=null) 
                               {
                                    String[] string = value[i].toString().split(" ");
                                     if(value[i] instanceof Number) {
                                        req += value[i]+",";
                                    }else if(value[i] instanceof java.sql.Timestamp){
                                        System.out.println("zsdfghjklm");
                                        int jour = ((java.sql.Timestamp) value[i]).getDate();
                                        int mois = ((java.sql.Timestamp) value[i]).getMonth()+1;
                                        int annee = ((java.sql.Timestamp) value[i]).getYear()+1900;
                                        int hours=((java.sql.Timestamp) value[i]).getHours();
                                        int minutes=((java.sql.Timestamp) value[i]).getMinutes();
                                        int secondes=((java.sql.Timestamp) value[i]).getSeconds();
                                        req += "TO_TIMESTAMP('"+jour+"-"+mois+"-"+annee+" "+hours+":"+minutes+":"+secondes+"','DD-MM-YYYY HH24:MI:SS'),";
                
                                    }
                                    else if(value[i] instanceof java.util.Date) {
                                        int jour = ((java.util.Date) value[i]).getDate();
                                        int mois = ((java.util.Date) value[i]).getMonth()+1;
                                        int annee = ((java.util.Date) value[i]).getYear()+1900;
                                        req += "TO_DATE('"+jour+"-"+mois+"-"+annee+"','DD-MM-YYYY'),";
                                    }
                                    else if(value[i] instanceof String){
                                        req += "'"+value[i]+"',";
                                    }
                                    else {
                                        if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                     Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                     Field[]foreign=value[i].getClass().getDeclaredFields();
                                                     int val=-1;
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                            Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                            val=(int)met.invoke(value[i]);
                                                                        }
                                                                    }
                                                    if(val!=-1){
                                                         req += ""+val+",";
                                                    }else{
                                                        throw new Exception("manque de foreign key");
                                                    }
                                                }
                                            }
                                        }
                                    }    
                               }else{
                                req+="null,";
                               }
                        
                            
                        
                    }
                    else {
                        if (value[i]!=null) 
                        {
                                String[] string = value[i].toString().split(" ");
                                if(value[i] instanceof Number) {
                                    req += value[i]+")";
                                 
                                }else if(value[i] instanceof java.sql.Timestamp){
                                    int jour = ((java.sql.Timestamp) value[i]).getDate();
                                    int mois = ((java.sql.Timestamp) value[i]).getMonth()+1;
                                    int annee = ((java.sql.Timestamp) value[i]).getYear()+1900;
                                    int hours=((java.sql.Timestamp) value[i]).getHours();
                                    int minutes=((java.sql.Timestamp) value[i]).getMinutes();
                                    int secondes=((java.sql.Timestamp) value[i]).getSeconds();
                                    req += "TO_TIMESTAMP('"+jour+"-"+mois+"-"+annee+" "+hours+":"+minutes+":"+secondes+"','DD-MM-YYYY HH24:MI:SS'))";
            
                                }
                                else if(value[i] instanceof java.util.Date) {
                                    int jour = ((java.util.Date) value[i]).getDate();
                                    int mois = ((java.util.Date) value[i]).getMonth()+1;
                                    int annee = ((java.util.Date) value[i]).getYear()+1900;
                                    req += "TO_DATE('"+jour+"-"+mois+"-"+annee+"','DD-MM-YYYY'))";
                                }
                                else if(value[i] instanceof String){
                                        req += "'"+value[i]+"')";
                                }
                                else {
                                    if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                    Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                    Field[]foreign=value[i].getClass().getDeclaredFields();
                                                    int val=-1;
                                                                   for(int k=0;k<foreign.length;k++){
                                                                       Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                       if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                           Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                           val=(int)met.invoke(value[i]);
                                                                       }
                                                                   }
                                                    if(val!=-1){
                                                         req += ""+val+")";
                                                    }else{
                                                        throw new Exception("manque de foreign key");
                                                    }
                                                    
                                                }
                                            }
                                        }
                                }    
                        }else{
                            req += "null)";
                        }
                    }
                }
            }
            Statement stmt =null;
            try{
                c=Connexion.getConnection(url,"postgres","mahary");
                //System.out.println(req);
                stmt = c.createStatement();
                stmt.executeUpdate(req);
                c.commit();
            }catch(Exception e){
                throw e;
            }finally{
                if(stmt!=null) stmt.close();
                if(c!=null) c.close();
            }
        

        
    }
    @Override
    public void update(Object ray)throws Exception
    {
        Field[] fields = ray.getClass().getDeclaredFields();
        Method[] method = new Method[fields.length];
        Object[] value = new Object[fields.length];
        NomTable nomTable=(NomTable)ray.getClass().getAnnotation(NomTable.class);
        Attribut att=null;
        int countForeignkey=0;
        for(int i=0;i<fields.length;i++) {
            att=fields[i].getAnnotation(Attribut.class);
             if(att.isforeignkey()==true){
                countForeignkey++;
             }
            method[i] = ray.getClass().getMethod("get"+toUpperFirst(fields[i].getName()));
            value[i] = method[i].invoke(ray);
        }
        int[]fkey=null;
        if(countForeignkey>0){
            fkey=new int[countForeignkey];
            int count=0;
            for(int i=0;i<fields.length;i++) {
                    att=fields[i].getAnnotation(Attribut.class);
                    if(att.isforeignkey()==true){
                            fkey[count]=i;
                            count++;
                    }
            }
        }
        String req = "UPDATE "+nomTable.nom()+" SET ";
        for(int i=1;i<value.length;i++) {
            Attribut atte=fields[i].getAnnotation(Attribut.class);
            if(i<value.length-1) {
               if (value[i]!=null) 
               {
                     if(value[i] instanceof Number) {
                            req += atte.columnName()+" = "+value[i]+", ";
                        }else if(value[i] instanceof Timestamp){
                            int jour = ((java.sql.Timestamp) value[i]).getDate();
                            int mois = ((java.sql.Timestamp) value[i]).getMonth()+1;
                            int annee = ((java.sql.Timestamp) value[i]).getYear()+1900;
                            int hours=((java.sql.Timestamp) value[i]).getHours();
                            int minutes=((java.sql.Timestamp) value[i]).getMinutes();
                            int secondes=((java.sql.Timestamp) value[i]).getSeconds();
                            req +=atte.columnName()+"=TO_TIMESTAMP('"+jour+"-"+mois+"-"+annee+" "+hours+":"+minutes+":"+secondes+"','DD-MM-YYYY HH24:MI:SS'),";
    
                        }
                        else if(value[i] instanceof java.util.Date) {
                            int jour = ((java.util.Date) value[i]).getDate();
                            int mois = ((java.util.Date) value[i]).getMonth()+1;
                            int annee = ((java.util.Date) value[i]).getYear()+1900;
                            req += atte.columnName()+"=TO_DATE('"+jour+"-"+mois+"-"+annee+"','DD-MM-YYYY'),";
                        }
                        else if(value[i] instanceof String){
                            req += atte.columnName()+" = '"+value[i]+"', ";
                        }else{
                            if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                    Attribut atter=fields[fkey[j]].getAnnotation(Attribut.class);
                                                     Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                     Field[]foreign=value[i].getClass().getDeclaredFields();
                                                     int  val=-1;
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                            Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                            val=(int)met.invoke(value[i]);
                                                                        }
                                                                    }
                                                    if(val!=-1){
                                                        req += atter.columnName()+" = "+val+", ";
                                                    }else{
                                                        throw new Exception("manque de foreign key");
                                                    }
                                                    
                                                }
                                            }
                                        }
                        }    
               }else{
                     req +=atte.columnName()+" = "+"null,";
               }
            }
            else {
                if (value[i]!=null) 
                {
                    if(value[i] instanceof Number) {
                    req += atte.columnName()+" = "+value[i];
                    }else if(value[i] instanceof Timestamp){
                        int jour = ((java.sql.Timestamp) value[i]).getDate();
                        int mois = ((java.sql.Timestamp) value[i]).getMonth()+1;
                        int annee = ((java.sql.Timestamp) value[i]).getYear()+1900;
                        int hours=((java.sql.Timestamp) value[i]).getHours();
                        int minutes=((java.sql.Timestamp) value[i]).getMinutes();
                        int secondes=((java.sql.Timestamp) value[i]).getSeconds();
                        req +=atte.columnName()+"=TO_TIMESTAMP('"+jour+"-"+mois+"-"+annee+" "+hours+":"+minutes+":"+secondes+"','DD-MM-YYYY HH24:MI:SS')";

                    }
                    else if(value[i] instanceof java.util.Date) {
                        int jour = ((java.util.Date) value[i]).getDate();
                        int mois = ((java.util.Date) value[i]).getMonth()+1;
                        int annee = ((java.util.Date) value[i]).getYear()+1900;
                        req += atte.columnName()+"=TO_DATE('"+jour+"-"+mois+"-"+annee+"','DD-MM-YYYY')";
                    }
                    else if(value[i] instanceof String){
                        req += atte.columnName()+" = '"+value[i]+"' ";
                    }
                    else {
                        if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                    Attribut atter=fields[fkey[j]].getAnnotation(Attribut.class);
                                                     Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                     Field[]foreign=value[i].getClass().getDeclaredFields();
                                                     int val=-1;
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                            Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                            val=(int)met.invoke(value[i]);
                                                                        }
                                                                    }
                                                     if(val!=-1){
                                                       req += atter.columnName()+" = "+val+" ";
                                                    }else{
                                                        throw new Exception("manque de foreign key");
                                                    }
                                                    
                                                }
                                            }
                                        }
                    }    
                }else{
                    req += atte.columnName()+" = "+"null";
                }
            }
        }
        for(int i=0;i<fields.length;i++) {
            att=fields[i].getAnnotation(Attribut.class);
           if (att.isprimarykey()==true) 
           {
                req += " WHERE "+att.columnName()+" = ";
                if(value[i] instanceof Number) {
                    req += value[i];
                }
                else {
                    req += " '"+value[i]+"' ";
                }
           }
        }
            
            Statement stmt =null;
            try{
                c=Connexion.getConnection(url,"postgres","mahary");
                //System.out.println(req);
                stmt = c.createStatement();
                stmt.executeUpdate(req);
                c.commit();
            }catch(Exception e){
                throw e;
            }finally{
                if(stmt!=null) stmt.close();
                if(c!=null) c.close();
            }
            
    }
    @Override
    public ArrayList<Object> findBySql(Object ray,String req)throws Exception{
        Field[] fields = ray.getClass().getDeclaredFields();
        Method[] method = new Method[fields.length];
        Object[] value = new Object[fields.length];
        NomTable nomTable=(NomTable)ray.getClass().getAnnotation(NomTable.class);
        Attribut att=null;
        int countForeignkey=0;
        for(int i=0;i<fields.length;i++) {
            att=fields[i].getAnnotation(Attribut.class);
             if(att.isforeignkey()==true){
                countForeignkey++;
             }
            method[i] = ray.getClass().getMethod("get"+toUpperFirst(fields[i].getName()));
            value[i] = method[i].invoke(ray);
        }
        int[]fkey=null;
        if(countForeignkey>0){
            fkey=new int[countForeignkey];
            int count=0;
            for(int i=0;i<fields.length;i++) {
                    att=fields[i].getAnnotation(Attribut.class);
                    if(att.isforeignkey()==true){
                            fkey[count]=i;
                            count++;
                    }
            }
        }
        for(int i=0;i<fields.length;i++) {
            method[i] = ray.getClass().getMethod("get"+toUpperFirst(fields[i].getName()));
            value[i] = method[i].invoke(ray);
        }
        try{
            c=Connexion.getConnection(url,"postgres","mahary");
        }catch(Exception e){
            throw e;
        }
        PreparedStatement stmt = c.prepareStatement(req);
        ResultSet res = stmt.executeQuery();
        int nb = 0;
        while(res.next()) {
            nb++;
        }
        Object[] retour = new Object[nb];
        ResultSet res2 = stmt.executeQuery();
        int indice = 0;
        int id = 1;
        while(res2.next()) {
            retour[indice] = ray.getClass().getDeclaredConstructor().newInstance();
            for(int i=0;i<method.length;i++) {
                Class[] classe = new Class[1];
                classe[0] = fields[i].getType();
                Attribut roa=fields[i].getAnnotation(Attribut.class);
                if(classe[0].getSimpleName().equals("int")){
                    ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getInt(id));
                }else if(classe[0].getSimpleName().equals("double")){
                     ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getDouble(id));
                }else if(classe[0].getSimpleName().equals("String")){
                    ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getString(id));
                }else if(classe[0].getSimpleName().equals("Timestamp")){
                    ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getTimestamp(id));
                }else if(classe[0].getSimpleName().equals("Date")){
                     ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getDate(id));
                }else{
                    if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(fields[i].getType().getSimpleName())==0){
                                                    Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                    Object temp=fields[fkey[j]].getType().getDeclaredConstructor().newInstance();
                                                    String exemple="mechant";
                                                    Object obj=new Object();
                                                    Field[]foreign=temp.getClass().getDeclaredFields();
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                               temp.getClass().getMethod("set"+toUpperFirst(foreign[k].getName()),int.class).invoke(temp,res2.getInt(id));
                                                                        }
                                                                    }
                                                    ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),obj.getClass()).invoke(retour[indice],temp);
                                                }
                                            }
                                        }
                }
                id++;
            }
            id=1;
            indice++;
        }
        ArrayList<Object>obj=new ArrayList<Object>();
        if(retour.length>0){
            for (int i = 0; i <retour.length; i++) {
                obj.add(retour[i]);
            }
        }
        if(stmt!=null) stmt.close();
        if(res!=null) res.close();
        if(res2!=null) res2.close();
        if(c!=null) c.close();
        return obj;
    }
    @Override
    public ArrayList<Object> find(Object ray)throws Exception{
        Field[] fields = ray.getClass().getDeclaredFields();
        Method[] method = new Method[fields.length];
        Object[] value = new Object[fields.length];
        NomTable nomTable=(NomTable)ray.getClass().getAnnotation(NomTable.class);
        Attribut att=null;
        int countForeignkey=0;
        for(int i=0;i<fields.length;i++) {
            att=fields[i].getAnnotation(Attribut.class);
             if(att.isforeignkey()==true){
                countForeignkey++;
             }
            method[i] = ray.getClass().getMethod("get"+toUpperFirst(fields[i].getName()));
            value[i] = method[i].invoke(ray);
        }
        int[]fkey=null;
        if(countForeignkey>0){
            fkey=new int[countForeignkey];
            int count=0;
            for(int i=0;i<fields.length;i++) {
                    att=fields[i].getAnnotation(Attribut.class);
                    if(att.isforeignkey()==true){
                            fkey[count]=i;
                            count++;
                    }
            }
        }
        for(int i=0;i<fields.length;i++) {
            method[i] = ray.getClass().getMethod("get"+toUpperFirst(fields[i].getName()));
            value[i] = method[i].invoke(ray);
        }
        String req = "SELECT * FROM "+nomTable.nom();
        boolean setWhere = false;
        for(int i=0;i<value.length;i++) {
            if(i==0) {
                Attribut atte=fields[i].getAnnotation(Attribut.class);
                if(value[i] instanceof Number) {
                    if(((Number)value[i]).doubleValue()>=0) {
                        
                        req += " WHERE "+atte.columnName()+"="+value[i];
                        setWhere = true;
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else if(value[i] instanceof Timestamp){
                    if(value[i]!=null) {
                        req += " WHERE "+atte.columnName()+" = TO_TIMESTAMP('"+((java.sql.Timestamp) value[i]).getDate()+"-"+(((java.sql.Timestamp) value[i]).getMonth()+1)+"-"+(((java.sql.Timestamp) value[i]).getYear()+1900)+" "+(((java.sql.Timestamp) value[i]).getHours())+":"+(((java.sql.Timestamp) value[i]).getMinutes())+":"+(((java.sql.Timestamp) value[i]).getSeconds())+"','DD-MM-YYYY HH24:MI:SS')";
                        setWhere = true;
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else if(value[i] instanceof java.util.Date) {
                    if(value[i]!=null) {
                        req += " WHERE "+atte.columnName()+" = TO_DATE('"+((java.util.Date) value[i]).getDate()+"-"+(((java.util.Date) value[i]).getMonth()+1)+"-"+(((java.util.Date) value[i]).getYear()+1900)+"','DD-MM-YYYY')";
                        setWhere = true;
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else {
                    if(value[i]!=null) {
                        if(value[i] instanceof String){
                                if (value[i].toString().contains("%")==false) {
                                    req += " WHERE "+atte.columnName()+"='"+value[i]+"' ";
                                }else{
                                    req += " WHERE "+atte.columnName()+" like '"+value[i]+"' ";
                                }
                        }else{
                            if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                    Attribut atter=fields[fkey[j]].getAnnotation(Attribut.class);
                                                     Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                     Field[]foreign=value[i].getClass().getDeclaredFields();
                                                     int val=-1;
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                            Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                            val=(int)met.invoke(value[i]);
                                                                        }
                                                                    }
                                                     if(val!=-1){
                                                        req += " WHERE "+atter.columnName()+"="+val+" ";
                                                     }else{
                                                        req += " WHERE 1=1 ";
                                                     }
                                                }
                                            }
                                        }
                        }
                        setWhere = true;
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
            }
            if(i<value.length-1 && i>0) {
               Attribut atte=fields[i].getAnnotation(Attribut.class);
                if(value[i] instanceof Number) {
                    if(((Number)value[i]).doubleValue()>=0) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+"="+value[i];
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }else if(value[i] instanceof Timestamp) {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+" = TO_TIMESTAMP('"+((java.sql.Timestamp) value[i]).getDate()+"-"+(((java.sql.Timestamp) value[i]).getMonth()+1)+"-"+(((java.sql.Timestamp) value[i]).getYear()+1900)+" "+(((java.sql.Timestamp) value[i]).getHours())+":"+(((java.sql.Timestamp) value[i]).getMinutes())+":"+(((java.sql.Timestamp) value[i]).getSeconds())+"','DD-MM-YYYY HH24:MI:SS')";
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else if(value[i] instanceof java.util.Date) {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+" = TO_DATE('"+((java.util.Date) value[i]).getDate()+"-"+(((java.util.Date) value[i]).getMonth()+1)+"-"+(((java.util.Date) value[i]).getYear()+1900)+"','DD-MM-YYYY')";
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        if(value[i] instanceof String){
                            if(value[i].toString().contains("%")==false){
                                req += atte.columnName()+"='"+value[i]+"' ";    
                            }else{
                                req += atte.columnName()+" like '"+value[i]+"' ";
                            } 
                        }else{
                             if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                    Attribut atter=fields[fkey[j]].getAnnotation(Attribut.class);
                                                     Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                     Field[]foreign=value[i].getClass().getDeclaredFields();
                                                     int val=-1;
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                            Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                            val=(int)met.invoke(value[i]);
                                                                        }
                                                                    }
                                                     if(val!=-1){
                                                        req +=atter.columnName()+"="+val+" ";
                                                     }else{
                                                        req +="1=1 ";
                                                     }
                                                }
                                            }
                                        }
                        }
                        
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
            }
            if(i==value.length-1) {
                Attribut atte=fields[i].getAnnotation(Attribut.class);
                if(value[i] instanceof Number) {
                    if(((Number)value[i]).doubleValue()>=0) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+"="+value[i];
                    }
                }
                else if(value[i] instanceof Timestamp) {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+" = TO_TIMESTAMP('"+((java.sql.Timestamp) value[i]).getDate()+"-"+(((java.sql.Timestamp) value[i]).getMonth()+1)+"-"+(((java.sql.Timestamp) value[i]).getYear()+1900)+" "+(((java.sql.Timestamp) value[i]).getHours())+":"+(((java.sql.Timestamp) value[i]).getMinutes())+":"+(((java.sql.Timestamp) value[i]).getSeconds())+"','DD-MM-YYYY HH24:MI:SS')";
                    }
                }
                else if(value[i] instanceof java.util.Date) {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+"  = TO_DATE('"+((java.util.Date) value[i]).getDate()+"-"+(((java.util.Date) value[i]).getMonth()+1)+"-"+(((java.util.Date) value[i]).getYear()+1900)+"','DD-MM-YYYY')";
                    }
                }
                
                else {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        if(value[i] instanceof String){
                                if (value[i].toString().contains("%")==false) {
                                req += atte.columnName()+"='"+value[i]+"'";
                            }else{
                                req += atte.columnName()+" like '"+value[i]+"'";
                            }
                        }else{
                            if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                    Attribut atter=fields[fkey[j]].getAnnotation(Attribut.class);
                                                     Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                     Field[]foreign=value[i].getClass().getDeclaredFields();
                                                     int val=-1;
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                            Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                            val=(int)met.invoke(value[i]);
                                                                        }
                                                                    }
                                                                    if(val!=-1){
                                                                        req +=atter.columnName()+"="+val+" ";
                                                                     }else{
                                                                        req +="1=1 ";
                                                                     }
                                                }
                                            }
                                        }
                        }
                    }
                }
            }
        }
        System.out.println("tay1 "+req);
        try{
            c=Connexion.getConnection(url,"postgres","mahary");
            c.commit();
        }catch(Exception e){
            throw e;
        }
        PreparedStatement stmt = c.prepareStatement(req);
        ResultSet res = stmt.executeQuery();
        int nb = 0;
        while(res.next()) {
            nb++;
        }
        //System.out.println("tay1 "+req);
        Object[] retour = new Object[nb];
        ResultSet res2 = stmt.executeQuery();
        int indice = 0;
        int id = 1;
        while(res2.next()) {
            retour[indice] = ray.getClass().getDeclaredConstructor().newInstance();
            for(int i=0;i<method.length;i++) {
                Class[] classe = new Class[1];
                classe[0] = fields[i].getType();
                Attribut roa=fields[i].getAnnotation(Attribut.class);
                if(classe[0].getSimpleName().equals("int")){
                    ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getInt(id));
                }else if(classe[0].getSimpleName().equals("double")){
                     ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getDouble(id));
                }else if(classe[0].getSimpleName().equals("String")){
                    ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getString(id));
                }else if(classe[0].getSimpleName().equals("Timestamp")){
                    ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getTimestamp(id));
                }else if(classe[0].getSimpleName().equals("Date")){
                     ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),classe).invoke(retour[indice], res2.getDate(id));
                }else{
                    if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(fields[i].getType().getSimpleName())==0){
                                                    Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                    Object temp=fields[fkey[j]].getType().getDeclaredConstructor().newInstance();
                                                    String exemple="mechant";
                                                    Object obj=new Object();
                                                    Field[]foreign=temp.getClass().getDeclaredFields();
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                               temp.getClass().getMethod("set"+toUpperFirst(foreign[k].getName()),int.class).invoke(temp,res2.getInt(id));
                                                                        }
                                                                    }
                                                    ray.getClass().getMethod("set"+toUpperFirst(fields[i].getName()),obj.getClass()).invoke(retour[indice],temp);
                                                }
                                            }
                                        }
                }
                id++;
            }
            id=1;
            indice++;
        }
        ArrayList<Object>obj=new ArrayList<Object>();
        if(retour.length>0){
            for (int i = 0; i <retour.length; i++) {
                obj.add(retour[i]);
            }
        }
        if(stmt!=null) stmt.close();
        if(res!=null) res.close();
        if(res2!=null) res2.close();
        if(c!=null) c.close();
        return obj;
    }
    @Override
    public void delete(Object ray)throws Exception{
        Field[] fields = ray.getClass().getDeclaredFields();
        Method[] method = new Method[fields.length];
        Object[] value = new Object[fields.length];
        NomTable nomTable=(NomTable)ray.getClass().getAnnotation(NomTable.class);
        Attribut att=null;
        int countForeignkey=0;
        for(int i=0;i<fields.length;i++) {
            att=fields[i].getAnnotation(Attribut.class);
             if(att.isforeignkey()==true){
                countForeignkey++;
             }
            method[i] = ray.getClass().getMethod("get"+toUpperFirst(fields[i].getName()));
            value[i] = method[i].invoke(ray);
        }
        int[]fkey=null;
        if(countForeignkey>0){
            fkey=new int[countForeignkey];
            int count=0;
            for(int i=0;i<fields.length;i++) {
                    att=fields[i].getAnnotation(Attribut.class);
                    if(att.isforeignkey()==true){
                            fkey[count]=i;
                            count++;
                    }
            }
        }
        for(int i=0;i<fields.length;i++) {
            method[i] = ray.getClass().getMethod("get"+toUpperFirst(fields[i].getName()));
            value[i] = method[i].invoke(ray);
        }
        String req = "DELETE FROM "+nomTable.nom();
        boolean setWhere = false;
        for(int i=0;i<value.length;i++) {
            if(i==0) {
                Attribut atte=fields[i].getAnnotation(Attribut.class);
                if(value[i] instanceof Number) {
                    if(((Number)value[i]).doubleValue()>=0) {
                        
                        req += " WHERE "+atte.columnName()+"="+value[i];
                        setWhere = true;
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else if(value[i] instanceof Timestamp){
                    if(value[i]!=null) {
                        req += " WHERE "+atte.columnName()+" = TO_TIMESTAMP('"+((java.sql.Timestamp) value[i]).getDate()+"-"+(((java.sql.Timestamp) value[i]).getMonth()+1)+"-"+(((java.sql.Timestamp) value[i]).getYear()+1900)+" "+(((java.sql.Timestamp) value[i]).getHours())+":"+(((java.sql.Timestamp) value[i]).getMinutes())+":"+(((java.sql.Timestamp) value[i]).getSeconds())+"','DD-MM-YYYY HH24:MI:SS')";
                        setWhere = true;
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else if(value[i] instanceof java.util.Date) {
                    if(value[i]!=null) {
                        req += " WHERE "+atte.columnName()+" = TO_DATE('"+((java.util.Date) value[i]).getDate()+"-"+(((java.util.Date) value[i]).getMonth()+1)+"-"+(((java.util.Date) value[i]).getYear()+1900)+"','DD-MM-YYYY')";
                        setWhere = true;
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else {
                    if(value[i]!=null) {
                        if(value[i] instanceof String){
                                if (value[i].toString().contains("%")==false) {
                                    req += " WHERE "+atte.columnName()+"='"+value[i]+"' ";
                                }else{
                                    req += " WHERE "+atte.columnName()+" like '"+value[i]+"' ";
                                }
                        }else{
                            if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                    Attribut atter=fields[fkey[j]].getAnnotation(Attribut.class);
                                                     Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                     Field[]foreign=value[i].getClass().getDeclaredFields();
                                                     int val=-1;
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                            Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                            val=(int)met.invoke(value[i]);
                                                                        }
                                                                    }
                                                     if(val!=-1){
                                                        req += " WHERE "+atter.columnName()+"="+val+" ";
                                                     }else{
                                                        req += " WHERE 1=1 ";
                                                     }
                                                }
                                            }
                                        }
                        }
                        setWhere = true;
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
            }
            if(i<value.length-1 && i>0) {
               Attribut atte=fields[i].getAnnotation(Attribut.class);
                if(value[i] instanceof Number) {
                    if(((Number)value[i]).doubleValue()>=0) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+"="+value[i];
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }else if(value[i] instanceof Timestamp) {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+" = TO_TIMESTAMP('"+((java.sql.Timestamp) value[i]).getDate()+"-"+(((java.sql.Timestamp) value[i]).getMonth()+1)+"-"+(((java.sql.Timestamp) value[i]).getYear()+1900)+" "+(((java.sql.Timestamp) value[i]).getHours())+":"+(((java.sql.Timestamp) value[i]).getMinutes())+":"+(((java.sql.Timestamp) value[i]).getSeconds())+"','DD-MM-YYYY HH24:MI:SS')";
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else if(value[i] instanceof java.util.Date) {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+" = TO_DATE('"+((java.util.Date) value[i]).getDate()+"-"+(((java.util.Date) value[i]).getMonth()+1)+"-"+(((java.util.Date) value[i]).getYear()+1900)+"','DD-MM-YYYY')";
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
                else {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        if(value[i] instanceof String){
                            if(value[i].toString().contains("%")==false){
                                req += atte.columnName()+"='"+value[i]+"' ";    
                            }else{
                                req += atte.columnName()+" like '"+value[i]+"' ";
                            } 
                        }else{
                             if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                    Attribut atter=fields[fkey[j]].getAnnotation(Attribut.class);
                                                     Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                     Field[]foreign=value[i].getClass().getDeclaredFields();
                                                     int val=-1;
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                            Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                            val=(int)met.invoke(value[i]);
                                                                        }
                                                                    }
                                                     if(val!=-1){
                                                        req +=atter.columnName()+"="+val+" ";
                                                     }else{
                                                        req +="1=1 ";
                                                     }
                                                }
                                            }
                                        }
                        }
                        
                        if(value[i+1] instanceof Number) {
                            if(((Number)value[i+1]).doubleValue()>=0) req += " AND ";
                        }
                        else {
                            if(value[i+1]!=null) req += " AND ";
                        }
                    }
                }
            }
            if(i==value.length-1) {
                Attribut atte=fields[i].getAnnotation(Attribut.class);
                if(value[i] instanceof Number) {
                    if(((Number)value[i]).doubleValue()>=0) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+"="+value[i];
                    }
                }
                else if(value[i] instanceof Timestamp) {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+" = TO_TIMESTAMP('"+((java.sql.Timestamp) value[i]).getDate()+"-"+(((java.sql.Timestamp) value[i]).getMonth()+1)+"-"+(((java.sql.Timestamp) value[i]).getYear()+1900)+" "+(((java.sql.Timestamp) value[i]).getHours())+":"+(((java.sql.Timestamp) value[i]).getMinutes())+":"+(((java.sql.Timestamp) value[i]).getSeconds())+"','DD-MM-YYYY HH24:MI:SS')";
                    }
                }
                else if(value[i] instanceof java.util.Date) {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        req += atte.columnName()+"  = TO_DATE('"+((java.util.Date) value[i]).getDate()+"-"+(((java.util.Date) value[i]).getMonth()+1)+"-"+(((java.util.Date) value[i]).getYear()+1900)+"','DD-MM-YYYY')";
                    }
                }
                
                else {
                    if(value[i]!=null) {
                        int count = 0;
                        for(int j=0;j<=i-1;j++) {
                            if(value[j] instanceof Number) {
                                if((((Number)value[j]).doubleValue())>=0) count++;
                            }
                            else {
                                if(value[j]!=null) count++;
                            }
                        }
                        if(count==0 && setWhere==false) req += " WHERE ";
                        setWhere = true;
                        if(count>0) {
                            if(value[i-1] instanceof Number) {
                                if(((Number)value[i-1]).doubleValue()<0) req += " AND ";
                            }
                            else {
                                if(value[i-1]==null) req += " AND ";
                            }
                        }
                        if(value[i] instanceof String){
                                if (value[i].toString().contains("%")==false) {
                                req += atte.columnName()+"='"+value[i]+"'";
                            }else{
                                req += atte.columnName()+" like '"+value[i]+"'";
                            }
                        }else{
                            if(countForeignkey>0){
                                            for(int j=0;j<fkey.length;j++){
                                                if(fields[fkey[j]].getType().getSimpleName().compareToIgnoreCase(value[i].getClass().getSimpleName())==0){
                                                    Attribut atter=fields[fkey[j]].getAnnotation(Attribut.class);
                                                     Foreignkey fore=fields[fkey[j]].getAnnotation(Foreignkey.class);
                                                     Field[]foreign=value[i].getClass().getDeclaredFields();
                                                     int val=-1;
                                                                    for(int k=0;k<foreign.length;k++){
                                                                        Attribut attri=foreign[k].getAnnotation(Attribut.class);
                                                                        if(attri.columnName().compareToIgnoreCase(fore.columnRef())==0){
                                                                            Method met=value[i].getClass().getMethod("get"+toUpperFirst(foreign[k].getName()));
                                                                            val=(int)met.invoke(value[i]);
                                                                        }
                                                                    }
                                                                    if(val!=-1){
                                                                        req +=atter.columnName()+"="+val+" ";
                                                                     }else{
                                                                        req +="1=1 ";
                                                                     }
                                                }
                                            }
                                        }
                        }
                    }
                }
            }
        }
        Statement stmt =null;
        try{
            c=Connexion.getConnection(url,"postgres","mahary");
            //System.out.println(req);
            stmt = c.createStatement();
            stmt.executeUpdate(req);
            c.commit();
        }catch(Exception e){
            throw e;
        }finally{
            if(stmt!=null) stmt.close();
            if(c!=null) c.close();
        }
        
    }
    public ArrayList<Object> pagination(String ray,int page,int nbPage)throws Exception{
        return new ArrayList<Object>();
    }
    public ArrayList<Object> pagination2(Object ra,String ray,int page,int nbPage)throws Exception{
        int offset=(nbPage*(page-1));
        String vao=" limit "+Integer.toString(nbPage)+" offset "+Integer.toString(offset);
        vao=ray+vao;
        System.out.println(vao);
        ArrayList<Object>fin=new ArrayList<Object>();
        fin=this.findBySql(ra,vao);
        return fin;
        
    }

    
}