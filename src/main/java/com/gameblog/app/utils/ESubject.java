/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.gameblog.app.utils;

/**
 *
 * @author orlan
 */
public enum ESubject {
    GENERIC("Generic"),
    PROBLEMS("Problems"),
    TECH_PROBLEMS("Tech Problems"),
    BUG_OCCURENCE("Bug occurrence"),
    INAPPROPRIATE_CONTENT("Inappropriate content"),
    SUGGESTIONS("Suggestions");

    private final String subject;

    private ESubject(String subject) {
        this.subject = subject;
    }

    public String getSubject(){
        return  this.subject;
    }
    
}
