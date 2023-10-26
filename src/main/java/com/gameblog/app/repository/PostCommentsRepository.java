/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.repository;

import com.gameblog.app.model.Post;
import com.gameblog.app.model.PostComments;
import com.gameblog.app.model.User;
import com.gameblog.app.utils.RepositoryException;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author orlan
 */
@RequestScoped
public class PostCommentsRepository implements RepositoryDAO<PostComments>{

    @PersistenceContext(unitName = "gameblogPU")
    private EntityManager em;
    
    @Override
    public void create(@Valid PostComments postComents) throws RepositoryException{
        em.persist(postComents);
    }

    @Override
    public void update(@Valid PostComments postComents) throws RepositoryException {
        em.merge(postComents);
    }

    @Override
    public Optional<PostComments> findById(Long id) throws RepositoryException {
        return Optional.ofNullable(em.find(PostComments.class, id));
    }
    
    public List<PostComments> findbyUser(User user) throws RepositoryException {
        Query findByUserQuery = em.createNamedQuery("PostComments.findByUserId");
        findByUserQuery.setParameter("user_id", user);
        return findByUserQuery.getResultList();
    }
    
    public Optional<List<PostComments>> findbyPost(Post post) throws RepositoryException {
        Query findByNameQuery = em.createNamedQuery("PostComments.findByPostId");
        findByNameQuery.setParameter("post_id", post);
        return Optional.ofNullable((List<PostComments>)findByNameQuery.getResultList());
    }
    
    @Deprecated
    @Override
    public Optional<PostComments> findByName(String value) throws RepositoryException {
       throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public void delete(PostComments postComents) throws RepositoryException {
        em.remove(em.merge(postComents));
    }

    @Override
    public List<PostComments> findAll() throws RepositoryException {
        Query findAllQuery = em.createNamedQuery("PostComments.findAll");
        return (List<PostComments>)findAllQuery.getResultList();
    }
    
}
