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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name="post")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p")
    , @NamedQuery(name = "Post.findById", query = "SELECT p FROM Post p WHERE p.id = :id")
    , @NamedQuery(name = "Post.findByTitle", query = "SELECT p FROM Post p WHERE p.title = :title")
    , @NamedQuery(name = "Post.findByAutor", query = "SELECT p FROM Post p WHERE p.user = :autor_id")
    , @NamedQuery(name = "Post.findByDate", query = "SELECT p FROM Post p WHERE p.date = :date")
    , @NamedQuery(name = "Post.findByCategory", query = "SELECT p FROM Post p WHERE p.category = :category")})


public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull
    @Size(min = 1, max = 200)
    private String title;
    
    @ManyToOne
    @JoinColumn(name="autor_id")
    private User user;
    
    @NotNull
    private String text;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @NotNull
    @Lob
    private byte[] image;
    
    @NotNull
    @Size(min = 1, max = 13)
    private String category;
    
    public Post() {}

    public Post(Long id, String title, User autor, Date date, byte[] image, String category) {
        this.id = id;
        this.title = title;
        this.user = autor;
        this.date = date;
        this.image = image;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category.replace("/", "_").toUpperCase();
    }
    
}

