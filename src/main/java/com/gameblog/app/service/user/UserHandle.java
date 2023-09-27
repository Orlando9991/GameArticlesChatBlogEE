/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.user;

import com.gameblog.app.repository.UserRepository;
import com.gameblog.app.model.User;
import com.gameblog.app.service.session.SessionHandle;
import com.gameblog.app.tools.GeneralViewTools;
import com.gameblog.app.utils.RepositoryException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    UserRepository userFacade;
    
    @Inject
    GeneralViewTools beanTools;

    @Inject
    SessionHandle sessionHandle;
    
    private User user;
    
    private static final Logger logger = Logger.getLogger(UserHandle.class.getName());
    
    public UserHandle(){
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
    public void registUser(){
        
        try {
            userFacade.create(user);
            beanTools.executePrimeFacesScript("PF('signUpDlg').hide();");
            showSucessMessage();
        } catch (RepositoryException e) {
            logger.log(Level.WARNING, e.getMessage());        
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
        }   
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
