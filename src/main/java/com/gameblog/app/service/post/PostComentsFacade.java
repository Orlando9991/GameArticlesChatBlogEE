/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.service.post;

import com.gameblog.app.model.PostComents;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import com.gameblog.app.service.IObjectCRUD;

/**
 *
 * @author orlan
 */
public class PostComentsFacade implements IObjectCRUD<PostComents>{

    @PersistenceContext(unitName = "gameblogPU")
    private EntityManager em;
    
    @Override
    public void create(@Valid PostComents postComents) {
        em.persist(postComents);
    }

    @Override
    public void update(@Valid PostComents postComents) {
        em.merge(postComents);
    }

    @Override
    public Optional<PostComents> findById(Long id) {
        return Optional.ofNullable(em.find(PostComents.class, id));
    }
    
    public Optional<PostComents> findbyUserId(Integer id) {
        Query findByNameQuery = em.createNamedQuery("PostComents.findByUserId");
        findByNameQuery.setParameter("userId", id);
        return Optional.ofNullable((PostComents)findByNameQuery.getSingleResult());
    }
    
    public Optional<PostComents> findbyPostId(Integer id) {
        Query findByNameQuery = em.createNamedQuery("PostComents.findByPostId");
        findByNameQuery.setParameter("postId", id);
        return Optional.ofNullable((PostComents)findByNameQuery.getSingleResult());
    }
    
    @Deprecated
    @Override
    public Optional<PostComents> findByName(String value) {
       throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public void delete(PostComents postComents) {
        em.remove(postComents);
    }

    @Override
    public List<PostComents> findAll() {
        Query findAllQuery = em.createNamedQuery("PostComents.findAll");
        return (List<PostComents>)findAllQuery.getResultList();
    }
    
}
