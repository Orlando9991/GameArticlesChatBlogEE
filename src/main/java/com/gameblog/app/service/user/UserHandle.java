/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.user;

import com.gameblog.app.model.User;
import com.gameblog.app.service.session.SessionHandle;
import com.gameblog.app.tools.BeanTools;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 *
 * @author orlan
 */
@Named("UserHandle")
@SessionScoped
public class UserHandle implements Serializable{
    
    @Inject
    UserFacade userFacade;
    
    @Inject
    BeanTools beanTools;

    @Inject
    SessionHandle sessionHandle;
    
    private User user;
    
    public UserHandle(){
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Transactional
    public void registUser(){
        userFacade.create(user);
        beanTools.executePrimeFacesScript("PF('signUpDlg').hide();");
        showSucessMessage();
    }
    
    public void welcomeUserMessage(String title, String message){
        if(sessionHandle.isLogged()){
            beanTools.showPopUpMessage("Welcome", "Nice to see you again " + sessionHandle.getUserName(), FacesMessage.SEVERITY_INFO);
        }
    }
    
    public void showSucessMessage() {
        beanTools.showAlertMessage("Sucess", "Registation Complete", FacesMessage.SEVERITY_INFO);
    }
    
}
