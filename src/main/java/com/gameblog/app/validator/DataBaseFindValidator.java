/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gameblog.app.validator;

import javax.faces.validator.Validator;

/**
 *
 * @author orlan
 */
public interface DataBaseFindValidator extends Validator{
    boolean exists(String value, String parameter, String query);    
}
