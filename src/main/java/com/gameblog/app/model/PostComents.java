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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "post_coments")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "PostComents.findAll", query = "SELECT p FROM PostComents p")
    , @NamedQuery(name = "PostComents.findById", query = "SELECT p FROM PostComents p WHERE p.id = :id")
    , @NamedQuery(name = "PostComents.findByPostId", query = "SELECT p FROM PostComents p WHERE p.postId = :postId")
    , @NamedQuery(name = "PostComents.findByUserId", query = "SELECT p FROM PostComents p WHERE p.userId = :userId")
    , @NamedQuery(name = "PostComents.findByComent", query = "SELECT p FROM PostComents p WHERE p.coment = :coment")
    , @NamedQuery(name = "PostComents.findByDate", query = "SELECT p FROM PostComents p WHERE p.date = :date")})


public class PostComents implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "post_id")
    private long postId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private long userId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String coment;
    
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    public PostComents() {
        this.id = 0L;
    }

    public PostComents(Long id) {
        this.id = id;
    }

    public PostComents(Long id, long postId, long userId, String coment, Date date) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.coment = coment;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PostComents)) {
            return false;
        }
        PostComents other = (PostComents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.basic.model.PostComents[ id=" + id + " ]";
    }
    
}
