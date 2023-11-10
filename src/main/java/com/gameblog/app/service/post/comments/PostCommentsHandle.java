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
import com.gameblog.app.repository.RepositoryDAO;
import com.gameblog.app.service.session.SessionHandle;
import com.gameblog.app.repository.UserRepository;
import com.gameblog.app.tools.GeneralViewTools;
import com.gameblog.app.utils.RepositoryException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

/**
 *
 * @author orlan
 */

@Named("PostCommentsHandle")
@RequestScoped
@Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, RepositoryException.class},
            dontRollbackOn = {SQLWarning.class})
public class PostCommentsHandle implements Serializable {
    
    private static final Logger logger = Logger.getLogger(PostCommentsHandle.class.getName());
    
    @Inject
    private RepositoryDAO<PostComments> repository;

    @Inject
    private RepositoryDAO<Post> postRepository;

    @Inject
    private RepositoryDAO<User> userRepository;

    @Inject
    private SessionHandle sessionHandle;

    @Inject
    private GeneralViewTools generalViewTools;

    private PostComments postComments;

    private String commentText;

    public PostCommentsHandle() {
    }

    public void createComment(Post post) {
        try {
            postComments = new PostComments();
            User user = (User) (userRepository.findByName(sessionHandle.getUserName()).orElseGet(null));
            postComments.setUser(user);
            postComments.setPost(postRepository.findById(post.getId()).get());
            postComments.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
            postComments.setComment(commentText);
            repository.create(postComments);

            generalViewTools.showPopUpMessage("Comment added",
                    user.getUsername() + "comment: " + generalViewTools.shortenText(commentText, 10),
                    FacesMessage.SEVERITY_INFO);
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public List<PostComments> getAllCommentsByPost(Post post) {
        List<PostComments> resultList = null;
        try {
            resultList = ((PostCommentsRepository)repository).findbyPost(post).get();
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return resultList;
    }
    
    public void removeComment(PostComments comment){
        try {
            repository.delete(comment);
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }  
    }

    public List<PostComments> getAllUserComments(){
        List<PostComments> resultList = null;
        try {
            User user = (User) (userRepository.findByName(sessionHandle.getUserName()).orElseGet(null));
            resultList = ((PostCommentsRepository)repository).findbyUser(user);
        } catch (RepositoryException | NoResultException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return resultList;
    }
    
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

}
