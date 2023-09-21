/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.post.comments;

import com.gameblog.app.repository.PostCommentsRepository;
import com.gameblog.app.model.Post;
import com.gameblog.app.model.PostComments;
import com.gameblog.app.model.User;
import com.gameblog.app.repository.PostRepository;
import com.gameblog.app.service.session.SessionHandle;
import com.gameblog.app.repository.UserRepository;
import com.gameblog.app.tools.GeneralViewTools;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 *
 * @author orlan
 */
@Named("PostCommentsHandle")
@RequestScoped
public class PostCommentsHandle implements Serializable{
    
    @Inject
    PostCommentsRepository postCommentsRepo;
    
    @Inject
    PostRepository postRepo;
    
    @Inject
    UserRepository userRepo;
    
    @Inject
    SessionHandle sessionHandle;
    
    @Inject
    GeneralViewTools generalViewTools;
    
    private PostComments postComments;
    
    private String commentText;
    
    public PostCommentsHandle(){
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    
    @Transactional
    public void createComment(Post post){
        postComments = new PostComments();
        User user = (User)(userRepo.findByName(sessionHandle.getUserName()).orElseGet(null));
        postComments.setUser(user);
        postComments.setPost(postRepo.findById(post.getId()).get());
        postComments.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        postComments.setComment(commentText);
        postCommentsRepo.create(postComments);
        
        generalViewTools.showPopUpMessage("Comment added",
                user.getUsername()+ "comment: "+ generalViewTools.shortenText(commentText, 10),
                FacesMessage.SEVERITY_INFO);
    }
    
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public List <PostComments> getAllCommentsByPost(Post post){
        return postCommentsRepo.findbyPost(post).get();
    }
    
    
    
}
