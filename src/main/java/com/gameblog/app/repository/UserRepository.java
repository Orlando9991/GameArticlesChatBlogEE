/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.repository;

import com.gameblog.app.model.User;
import com.gameblog.app.repository.IRepository;
import java.io.Serializable;
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
public class UserRepository implements IRepository<User>,Serializable{
    
    @PersistenceContext(unitName = "gameblogPU")
    private EntityManager em;
    
    @Override
    public void create(@Valid User user){
       em.persist(user); 
    }  

    @Override
    public void update(@Valid User user) {
        em.merge(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }
    
    @Override
    public Optional<User> findByName(String username) {
        Query findByNameQuery = em.createNamedQuery("User.findByUsername");
        findByNameQuery.setParameter("username", username);
        return Optional.ofNullable((User)findByNameQuery.getSingleResult());
    }

    @Override
    public void delete(User user) {
        em.remove(user);
    }

    @Override
    public List<User> findAll() {
        Query findAllQuery = em.createNamedQuery("User.findAll");
        return (List<User>)findAllQuery.getResultList();
    }
}
