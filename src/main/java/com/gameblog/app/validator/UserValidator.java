/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author orlan
 */
@Named("UserValidator")
@RequestScoped
public class UserValidator implements Validator{

    @PersistenceContext(unitName = "gameblogPU")
    private EntityManager em;
    
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        String strValue =  ((String)value); 
        String idComponent = uic.getId();
        boolean exists = false;
        
        if(idComponent.equals("nameForm")){
                exists = userExists(strValue, "username", "User.findByUsername");
        }else if(idComponent.equals("emailForm")){
                exists = userExists(strValue, "email", "User.findByEmail");
        }
        
        if(exists){
            throw  new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"","User allready exists"));
        }
    }
    
    
    
    private boolean userExists(String value, String parameter, String query){
        System.err.println("value");
        
        Query getUserQuery = em.createNamedQuery(query);
        getUserQuery.setParameter(parameter, value);
        int userCount = getUserQuery.getResultList().size();

        return userCount > 0;
    }
    
}
