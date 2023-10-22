/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.repository;

import com.gameblog.app.model.Post;
import com.gameblog.app.model.User;
import com.gameblog.app.utils.RepositoryException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
/**
 *
 * @author orlan
 */
@RequestScoped
public class PostRepository implements RepositoryDAO<Post>{
    
    @PersistenceContext(unitName = "gameblogPU")
    private EntityManager em;

    @Override
    public void create(@Valid Post post) throws RepositoryException {
        em.persist(post);
    }

    @Override
    public void update(@Valid Post post) throws RepositoryException {
        em.merge(post);
    }

    @Override
    public Optional<Post> findById(Long id) throws RepositoryException {
       return Optional.ofNullable((Post)em.find(Post.class, id));
    }

    @Override
    public Optional<Post> findByName(String title) throws RepositoryException {
        Query findByNameQuery = em.createNamedQuery("Post.findByTitle");
        findByNameQuery.setParameter("title", title);
        return Optional.ofNullable((Post)findByNameQuery.getSingleResult());
    }
    
    public List<Post> findByAuthor(User user) throws RepositoryException {
        Query findByAuthorQuery = em.createNamedQuery("Post.findByAutor");
        findByAuthorQuery.setParameter("autor_id", user);
        return findByAuthorQuery.getResultList();
    }

    @Override
    public void delete(Post post) throws RepositoryException {
        em.remove(em.merge(post));
    }

    @Override
    public List<Post> findAll() throws RepositoryException {
        Query findAllQuery = em.createNamedQuery("Post.findAll");
        return (List<Post>)findAllQuery.getResultList();
    }
    
    public List<Post> findAllByCategory(String category) throws RepositoryException{
        Query findAllCategoryQuery = em.createNamedQuery("Post.findByCategory");
        findAllCategoryQuery.setParameter("category", category);
        return (List<Post>)findAllCategoryQuery.getResultList();
    }

    public List<Post> findAllByDate(Date date) throws RepositoryException{
        Query findAllCategoryQuery = em.createNamedQuery("Post.findByDate");
        findAllCategoryQuery.setParameter("date", date);
        return (List<Post>)findAllCategoryQuery.getResultList();
    }    
}
