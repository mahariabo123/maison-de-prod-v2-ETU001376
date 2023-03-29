/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;

import  java.util.*;
/**
 *
 * @author USER
 */

public interface InterfaceDao {
    public void save(Object ray)throws Exception;
    public void update(Object ray)throws Exception;
    public void delete(Object ray)throws Exception;
    public ArrayList<Object> find(Object ray)throws Exception;
    public ArrayList<Object> findBySql(Object ray,String req)throws Exception;
    public ArrayList<Object> pagination(String ray,int page,int nbPage)throws Exception;
    public ArrayList<Object> pagination2(Object ra,String ray,int page,int nbPage)throws Exception;
    public void upt(int etat,int id)throws Exception;
}
