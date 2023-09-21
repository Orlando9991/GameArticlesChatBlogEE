/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author orlan
 */
@Named("GeneralViewTools")
@RequestScoped
public class GeneralViewTools {
 
    public String shortenText(String txt,int size){
        if(txt.length()>size){
            return txt.substring(0,size)+"...";
        }
        return txt;
    }
    
    public String getFormatedDate(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    public void showAlertMessage(String summary,String detail, FacesMessage.Severity severity){
        
        FacesMessage message = new FacesMessage(severity, summary, detail);  
        PrimeFaces.current().dialog().showMessageDynamic(message);    
    }
    
    public void showPopUpMessage(String title, String message, FacesMessage.Severity severity){
        FacesContext context = FacesContext.getCurrentInstance();  
        context.addMessage(null, new FacesMessage( severity, title, message));  
    }
    
    
    public void executePrimeFacesScript(String script){
        PrimeFaces.current().executeScript(script);
    }
        
   
}
