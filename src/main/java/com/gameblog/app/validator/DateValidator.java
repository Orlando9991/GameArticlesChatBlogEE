/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.validator;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

/**
 *
 * @author orlan
 */
@Named("DateValidator")
@RequestScoped
public class DateValidator implements Validator{
    
    private final static int MINDATE = 1924;
    private final static int MAXDATE = 2015;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object dateObject) throws ValidatorException {
        Date date = (Date) dateObject;
        int yearDate = date.getYear()+1900;

        if(yearDate<MINDATE || yearDate>MAXDATE){
            throw  new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Date not valid (1924 - 2014)"));
        }
    }
    
}
