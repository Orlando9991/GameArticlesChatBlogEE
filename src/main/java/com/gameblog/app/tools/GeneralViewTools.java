/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.tools;

import com.gameblog.app.service.post.PostHandle;
import com.gameblog.app.utils.EPostTab;
import static com.gameblog.app.utils.EPostTab.ACTION;
import static com.gameblog.app.utils.EPostTab.ADVENTURE;
import static com.gameblog.app.utils.EPostTab.ALL;
import static com.gameblog.app.utils.EPostTab.PUZZLE;
import static com.gameblog.app.utils.EPostTab.RPG;
import static com.gameblog.app.utils.EPostTab.SIMULATION;
import static com.gameblog.app.utils.EPostTab.SPORTS_RACING;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author orlan
 */
@Named("GeneralViewTools")
@RequestScoped
public class GeneralViewTools{
    
    @Inject
    PostHandle postHandle;
    
    private TabView tabView;
    private int activeIndex;

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
    
    public void executePrimeFacesUpdate(String el){
        PrimeFaces.current().ajax().update(el);
    }
    
    public void changeTab(int i){
        activeIndex = i;
        tabView.setActiveIndex(i);
        postHandle.setCurrentPostTab(EPostTab.values()[i]);
        PrimeFaces.current().executeScript("setActiveRenderSubMenu('articles-form')");
    }

    public void setPostCategory(TabChangeEvent event) {
        
        String title = event.getTab().getTitle().replaceFirst("/", "_").toUpperCase();
     
        
        switch (EPostTab.valueOf(title)) {
            case ALL:
                postHandle.setCurrentPostTab( EPostTab.ALL);
                break;
            case ACTION:
                 postHandle.setCurrentPostTab(EPostTab.ACTION);
                break;
            case ADVENTURE:
                 postHandle.setCurrentPostTab(EPostTab.ADVENTURE);
                break;
            case RPG:
                 postHandle.setCurrentPostTab(EPostTab.RPG);
                break;
            case SIMULATION:
                 postHandle.setCurrentPostTab(EPostTab.SIMULATION);
                break;
            case PUZZLE:
                 postHandle.setCurrentPostTab(EPostTab.PUZZLE);
                break;
            case SPORTS_RACING:
                 postHandle.setCurrentPostTab(EPostTab.SPORTS_RACING);
                break;
            default:
                 postHandle.setCurrentPostTab(EPostTab.ALL);
        }
        
        tabView.setActiveIndex(activeIndex);
    }
    
    public void redirectPage(String url){
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(GeneralViewTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getTodayDate(){
        return getFormatedDate(new Date(), "dd-MMMM-yyyy");
    }
    
    
    public TabView getTabView() {
        return tabView;
    }

    public void setTabView(TabView tabView) {
        this.tabView = tabView;
    }
}
