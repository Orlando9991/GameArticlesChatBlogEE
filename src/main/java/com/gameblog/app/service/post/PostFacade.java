/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.post;

import com.gameblog.app.model.Post;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import com.gameblog.app.service.IObjectCRUD;

/**
 *
 * @author orlan
 */
@RequestScoped
public class PostFacade implements IObjectCRUD<Post>{
    
    @PersistenceContext(unitName = "gameblogPU")
    private EntityManager em;

    @Override
    public void create(@Valid Post post) {
        em.persist(post);
    }

    @Override
    public void update(@Valid Post post) {
        em.merge(post);
    }

    @Override
    public Optional<Post> findById(Long id) {
       return Optional.ofNullable((Post)em.find(Post.class, id));
    }

    @Override
    public Optional<Post> findByName(String title) {
        Query findByNameQuery = em.createNamedQuery("Post.findByTitle");
        findByNameQuery.setParameter("title", title);
        return Optional.ofNullable((Post)findByNameQuery.getSingleResult());
    }

    @Override
    public void delete(Post post) {
        em.remove(post);
    }

    @Override
    public List<Post> findAll() {
        Query findAllQuery = em.createNamedQuery("Post.findAll");
        return (List<Post>)findAllQuery.getResultList();
    }
    
    public List<Post> findAllByCategory(String category){
        Query findAllCategoryQuery = em.createNamedQuery("Post.findByCategory");
        findAllCategoryQuery.setParameter("category", category);
        return (List<Post>)findAllCategoryQuery.getResultList();
    }
    
}
