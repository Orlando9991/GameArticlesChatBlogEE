/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author orlan
 */
@Named("PostValidator")
@RequestScoped
public class PostValidator implements DataBaseFindValidator{

    @PersistenceContext(unitName = "gameblogPU")
    private EntityManager em;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String postTitle =  (String)value; 
        boolean dbExists = exists(postTitle, "title", "Post.findByTitle");
        
        if(dbExists){
            throw  new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Post title allready exists"));
        }
    }
    
    @Override
    public boolean exists(String value, String parameter, String query){
        
        Query getPostQuery = em.createNamedQuery(query);
        getPostQuery.setParameter(parameter, value);
        int postCount = getPostQuery.getResultList().size();

        return postCount > 0;
    }
}
