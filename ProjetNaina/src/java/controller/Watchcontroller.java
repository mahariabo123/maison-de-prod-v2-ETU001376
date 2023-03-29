/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Service.*;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author USER
 */
@Controller
public class Watchcontroller {
    @Autowired
    WatchService ws;
    @RequestMapping(value={"/index","/"})
    public String  listeWatch(Model mod,@RequestParam Map<String,String> allParams){
        try{
            mod.addAttribute("listWatch",ws.all());
        }catch(Exception e){
            
        }
        return "index";
    }
    
}
