/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gameblog.app.repository;

import com.gameblog.app.utils.RepositoryException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

/**
 *
 * @author orlan
 * @param <T>
 */

public interface RepositoryDAO <T> {
    void create(@Valid T obj) throws RepositoryException;
    void update(@Valid T obj) throws RepositoryException;
    Optional<T> findById(Long id) throws RepositoryException;
    Optional<T> findByName(String value) throws RepositoryException;
    void delete(T obj) throws RepositoryException;
    List<T> findAll() throws RepositoryException;
}
