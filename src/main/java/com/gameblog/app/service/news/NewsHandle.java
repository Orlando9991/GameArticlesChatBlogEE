/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.news;

import com.gameblog.app.model.Post;
import com.gameblog.app.service.post.PostHandle;
import com.gameblog.app.tools.GeneralViewTools;
import com.gameblog.app.utils.EPostEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author orlan
 */
@Named("NewsHandle")
@Singleton
public class NewsHandle{
    
    @Inject
    GeneralViewTools generalViewTools;
    
    @EJB
    PostHandle postHandle;
    
    private final static Logger logger = Logger.getLogger(NewsHandle.class.getName());
    
    private  List<Post> postList;
    
    @PostConstruct
    public void init() {
        postList = new ArrayList<>();
        refreshDayNews();
    }
    
    public NewsHandle(){}
    
    //Update every day 0h
    @Schedule(dayOfWeek = "*", month = "*", hour = "0", dayOfMonth = "*", year = "*", minute = "*", second = "*", persistent = false)
    public void refreshDayNews(){
        Date date = new Date();
        this.postList.clear();
        this.postList = postHandle.getAllPostsListByDate(date);
        logger.log(Level.INFO, "News Updated");
    }
    
    public void postEventListener(@Observes EPostEvent postEvent){     
       refreshDayNews();
       updateView();
    }
    
    @Lock(LockType.READ)
    private void updateView(){
        generalViewTools.executePrimeFacesUpdate(":news-data");
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
    
}
