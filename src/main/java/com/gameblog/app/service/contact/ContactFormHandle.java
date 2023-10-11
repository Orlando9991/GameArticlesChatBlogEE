/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.contact;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author orlan
 */
@Named("ContactFormHandle")
@RequestScoped
public class ContactFormHandle {
    
    private List<SelectItem> subjects;
    private String selection;
    
    
    @PostConstruct
    public void init(){
        subjects = new ArrayList<>();
        SelectItemGroup group1 = new SelectItemGroup("Generic");
        group1.setSelectItems(new SelectItem[]{new SelectItem("Generic")});
        
        SelectItemGroup group2 = new SelectItemGroup("Problems");
        group2.setValue("Problems");
        group2.setSelectItems(new SelectItem[]{
            new SelectItem("Tech Problems"),
            new SelectItem("Bug inform"),
            new SelectItem("Inconsistente data")
        });
        
        SelectItemGroup group3 = new SelectItemGroup("Suggestions");
        group3.setSelectItems(new SelectItem[]{new SelectItem("Suggestions")});
        
        subjects.add(group1);
        subjects.add(group2);
        subjects.add(group3);
    }
    
    public void subjectSelected(SelectEvent event){
        System.out.println(event.getObject().toString());
    }

    public List<SelectItem> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SelectItem> subjects) {
        this.subjects = subjects;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
    
}
