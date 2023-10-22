/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.contact;

import com.gameblog.app.tools.JavaEmailTool;
import com.gameblog.app.utils.ESubject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named("ContactSubjectHandle")
@RequestScoped
public class ContactSubjectHandle {
    
    private List<SelectItem> subjects;
    private String selection;
    private ESubject subjectType;
    
    
    @PostConstruct
    public void init(){  
        selection = subjectType.GENERIC.getSubject();
        subjects = new ArrayList<>();
        SelectItemGroup group1 = new SelectItemGroup(subjectType.GENERIC.getSubject());
        group1.setSelectItems(new SelectItem[]{new SelectItem(subjectType.GENERIC.getSubject())});
        
        SelectItemGroup group2 = new SelectItemGroup(subjectType.PROBLEMS.getSubject());
        group2.setValue(subjectType.PROBLEMS.getSubject());
        group2.setSelectItems(new SelectItem[]{
            new SelectItem(subjectType.TECH_PROBLEMS.getSubject()),
            new SelectItem(subjectType.BUG_OCCURENCE.getSubject()),
            new SelectItem(subjectType.INAPPROPRIATE_CONTENT.getSubject())
        });
        
        SelectItemGroup group3 = new SelectItemGroup(subjectType.SUGGESTIONS.getSubject());
        group3.setSelectItems(new SelectItem[]{new SelectItem(subjectType.SUGGESTIONS.getSubject())});
        
        subjects.add(group1);
        subjects.add(group2);
        subjects.add(group3);
    }
    
    public void subjectSelected(SelectEvent event){
         Logger.getLogger(ContactSubjectHandle.class.getName()).log(Level.INFO, "Subject selected");
         selection = event.getObject().toString();
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
