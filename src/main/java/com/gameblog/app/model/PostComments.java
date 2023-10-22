/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.model;

/**
 *
 * @author orlan
 */
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author orlan
 */
@Entity
@Table(name = "post_comments")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "PostComments.findAll", query = "SELECT p FROM PostComments p")
    , @NamedQuery(name = "PostComments.findById", query = "SELECT p FROM PostComments p WHERE p.id = :id")
    , @NamedQuery(name = "PostComments.findByPostId", query = "SELECT p FROM PostComments p WHERE p.post = :post_id")
    , @NamedQuery(name = "PostComments.findByUserId", query = "SELECT p FROM PostComments p WHERE p.user = :user_id")
    , @NamedQuery(name = "PostComments.findByComment", query = "SELECT p FROM PostComments p WHERE p.comment = :comment")
    , @NamedQuery(name = "PostComments.findByDate", query = "SELECT p FROM PostComments p WHERE p.date = :date")})


public class PostComments implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private long id;
    
    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;
      
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String comment;
    
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    public PostComments() {
        this.id = 0L;
    }

    public PostComments(Long id) {
        this.id = id;
    }

    public PostComments(Long id, Post post, User user, String comment, Date date) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.comment = comment;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }    
}
