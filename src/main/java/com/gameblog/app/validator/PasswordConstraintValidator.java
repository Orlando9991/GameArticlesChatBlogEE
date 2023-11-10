/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author orlan
 */
public class PasswordConstraintValidator implements ConstraintValidator <PasswordConstraint, String>{
   
    private int pswSize;
    private String pswPattern;
    
    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        this.pswSize = constraintAnnotation.size();
        this.pswPattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {            
        return password.matches(pswPattern)&& password.length()>=pswSize;   
    }
  
}
