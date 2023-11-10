/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.gameblog.app.utils;

/**
 *
 * @author orlan
 */
public enum EMonth {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");
    
    private final String month;
    
    private EMonth(String month){
        this.month = month;
    }
    
    public String getFullMonth(){
        return month;
    }
    
    public String getAbrevMonth(int v){
        return month.substring(0,v);
    } 
}
