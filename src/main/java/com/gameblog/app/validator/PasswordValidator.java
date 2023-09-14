/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.validator;


import java.io.Serializable;
import java.util.function.BiFunction;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

/**
 *
 * @author orlan
 */
@Named("PasswordValidator")
@SessionScoped
public class PasswordValidator implements Serializable{

    public void validate(ComponentSystemEvent event){
   
        String psw = getStringComponentValue(event, "passForm");
        String pswC = getStringComponentValue(event, "confirmPassForm");
        String idOutput = "validatePswMessage";
        
        checkConstrains(event, psw, pswC,idOutput);
        
    }
    
    
    private void setStringComponentValue(ComponentSystemEvent event, String id,  final String value){
        UIComponent uic = event.getComponent().findComponent(id);
        
        uic.getAttributes().computeIfPresent("value", new BiFunction<String, Object, Object>(){
            @Override
            public Object apply(String t, Object u) {
                return value;
            }
        });
    }
    
    private String getStringComponentValue(ComponentSystemEvent event, String id){
        UIComponent uic = event.getComponent();
        UIInput uiPsw = (UIInput)uic.findComponent(id);
        String input = uiPsw.getValue().toString();
        
        return input==null?"":input;
    }     
    
    
    private void checkConstrains(ComponentSystemEvent event, String psw, String pswC, String idCompOutput){
        FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("Password must match confirm password");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage("passForm", msg);
		fc.renderResponse();
        String regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])((?=.*\\W)|(?=.*_))^[^ ]+$";
        
        StringBuilder sb = new StringBuilder();
        boolean errors = false;
        if(!psw.matches(regex)){
            errors=true;
            sb.append("*( ");
           // sb.append(EPasswordErrorMessage.ERRORREGEX.getMessage());
            sb.append(" )");
            sb.append("    ");
        }
        
        if(psw.equals(pswC)){
           sb.append("");
        }else{
            errors=true;
           sb.append("*( ");
       //    sb.append(EPasswordErrorMessage.ERRORNOTMATCH.getMessage());
           sb.append(" )");
        }
        if(errors){
           //
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage("passForm", msg);
		fc.renderResponse();
        }
        //setStringComponentValue(event, idCompOutput,sb.toString());
    }

}
