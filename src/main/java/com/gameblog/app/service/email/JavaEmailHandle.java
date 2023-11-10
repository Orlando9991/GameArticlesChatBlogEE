/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.email;

import com.gameblog.app.model.Email;
import com.gameblog.app.service.contact.ContactSubjectHandle;
import com.gameblog.app.service.session.SessionHandle;
import com.gameblog.app.service.user.UserHandle;
import com.gameblog.app.tools.GeneralViewTools;
import com.gameblog.app.tools.JavaEmailTool;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author orlan
 */
@Named("JavaEmailHandle")
@RequestScoped
public class JavaEmailHandle{

    private Email email;
    
    @Inject
    private UserHandle UserHandle;
    
    @Inject
    private SessionHandle sessionHandle;
    
    @Inject
    private ContactSubjectHandle ContactSubjectHandle;
    
    @Inject
    private GeneralViewTools generalViewTools;
  
    @PostConstruct
    public void init() {
        email = new Email();
    } 
    
    public void sendSelfEmail(){
        JavaEmailTool emailTool = JavaEmailTool.getInstance();
        prepareSelfEmailSubject();
        prepareSelfEmailBodyText();
        email.setRecepient(emailTool.getACCOUNT_EMAIL());
        emailTool.sendEmail(email);
        generalViewTools.showAlertMessage("", "Message Sent", FacesMessage.SEVERITY_INFO);
        cleanEmail();
    }
    
    
    private void prepareSelfEmailSubject(){
        if(sessionHandle.isLogged()){
            String subjectString = new String()
                    .concat("(")
                    .concat(ContactSubjectHandle.getSelection())
                    .concat(") ")
                    .concat(email.getSubject())
                    .concat(" -")
                    .concat(UserHandle.getUser().getUsername());
                            
            email.setSubject(subjectString);
        }else{
            String subjectString = new String()
                    .concat("(")
                    .concat(ContactSubjectHandle.getSelection())
                    .concat(") ")
                    .concat(email.getSubject())
                    .concat(" (external) ")
                    .concat(UserHandle.getUser()
                        .getEmail()
                        .substring(0,UserHandle.getUser().getEmail().indexOf("@")));
            
            email.setSubject(subjectString);          
        }
    }
    
    private void prepareSelfEmailBodyText(){
         email.setTextBody(email.getTextBody()
                 .concat(" (user-email): ")
                 .concat(UserHandle.getUser().getEmail()));
    }
    
    private void cleanEmail(){
        this.email = null;
    }
    
    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
    
}
