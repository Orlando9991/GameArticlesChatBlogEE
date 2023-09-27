/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.repository;

import com.gameblog.app.model.User;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import com.gameblog.app.utils.RepositoryException;

/**
 *
 * @author orlan
 */

@RequestScoped
public class UserRepository implements RepositoryDAO<User>,Serializable{
    
    @PersistenceContext(unitName = "gameblogPU")
    private EntityManager em;
    
    @Override
    public void create(@Valid User user) throws RepositoryException{
       em.persist(user); 
    }  

    @Override
    public void update(@Valid User user) throws RepositoryException {
        em.merge(user);
    }

    @Override
    public Optional<User> findById(Long id) throws RepositoryException {
        return Optional.ofNullable(em.find(User.class, id));
    }
    
    @Override
    public Optional<User> findByName(String username) throws RepositoryException {
        Query findByNameQuery = em.createNamedQuery("User.findByUsername");
        findByNameQuery.setParameter("username", username);
        return Optional.ofNullable((User)findByNameQuery.getSingleResult());
    }

    @Override
    public void delete(User user) throws RepositoryException {
        em.remove(user);
    }

    @Override
    public List<User> findAll() throws RepositoryException {
        Query findAllQuery = em.createNamedQuery("User.findAll");
        return (List<User>)findAllQuery.getResultList();
    }
}
