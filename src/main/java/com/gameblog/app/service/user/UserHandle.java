/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.user;

import com.gameblog.app.repository.UserRepository;
import com.gameblog.app.model.User;
import com.gameblog.app.service.post.PostHandle;
import com.gameblog.app.service.session.SessionHandle;
import com.gameblog.app.tools.GeneralViewTools;
import com.gameblog.app.utils.RepositoryException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

/**
 *
 * @author orlan
 */
@Named("UserHandle")
@SessionScoped
@Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
public class UserHandle implements Serializable{
    
    @Inject
    UserRepository userRepository;
    
    @Inject
    GeneralViewTools beanTools;

    @Inject
    SessionHandle sessionHandle;
    
    private User user;
    
    private Boolean disableEditSettings;
    
    @PostConstruct
    public void init(){
        user = new User();
        setDisableEditSettings(true);
    }
    
    
    private static final Logger logger = Logger.getLogger(UserHandle.class.getName());
    
    public UserHandle(){
        
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getDisableEditSettings() {
        return disableEditSettings;
    }

    public void setDisableEditSettings(Boolean disableEditSettings) {
        this.disableEditSettings = disableEditSettings;
    }
   
    public void registUser(){
        
        try {
            userRepository.create(user);
            beanTools.executePrimeFacesScript("PF('signUpDlg').hide();");
            showRegistSucessMessage();
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    
    public void updateUser(){
        try {
            System.out.println(user.getPassword());
            userRepository.update(user);
            setDisableEditSettings(true);
            showUpdateSucessMessage();
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    
    public User findUser(String username){
        try {
            return userRepository.findByName(username).get();
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        throw new RuntimeException("Something went wrong. (User not found)");
    }
    
    public void removeUser(){
         try {
            userRepository.delete(findUser(user.getUsername()));
            beanTools.redirectPage("/GameBlog/logout");
            beanTools.showAlertMessage("Remove Account","Account remove complete",FacesMessage.SEVERITY_INFO);
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    
    public void welcomeUserMessage(String title, String message){
        if(sessionHandle.isLogged()){
            beanTools.showPopUpMessage("Welcome", "Nice to see you again " + sessionHandle.getUserName(), FacesMessage.SEVERITY_INFO);     
        }
    }
        
    public void showRegistSucessMessage() {
        beanTools.showAlertMessage("Sucess", "Registation Complete", FacesMessage.SEVERITY_INFO);
    }
    
    public void showUpdateSucessMessage() {
        beanTools.showAlertMessage("Update", "Update complete", FacesMessage.SEVERITY_INFO);
    }
    
    public boolean isCurrentUserAdmin(){
        try{
            return findUser(sessionHandle.getUserName()).getRole().equals(User.Role.ADMIN.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return false; 
    }
    
    public void setUserByCurrentSection(){
        if(sessionHandle.isLogged()){          
            this.user = findUser(sessionHandle.getUserName());
        }
    }
}
