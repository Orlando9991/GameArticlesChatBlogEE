/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.session;

import com.gameblog.app.service.user.UserHandle;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author orlan
 */
@Named("SessionHandle")
@RequestScoped
public class SessionHandle{
    
    @Inject
    UserHandle userHandle;
        
    public boolean isLogged(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        Principal p = externalContext.getUserPrincipal();

        //User principal and sessionId valid
        return p != null && request.isRequestedSessionIdValid();

    }
    
    public String getUserName(){
        if(isLogged()){
            if(userHandle.getUser()!=null && userHandle.getUser().getUsername()!=null){
                return userHandle.getUser().getUsername();
            }
            Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            return userHandle.findUser(p.getName()).getUsername();  
        }
        return "";
    }

    
}
