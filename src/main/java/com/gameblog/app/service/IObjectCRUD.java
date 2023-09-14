/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gameblog.app.service;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

/**
 *
 * @author orlan
 * @param <T>
 */

public interface IObjectCRUD <T> {
    void create(@Valid T obj);
    void update(@Valid T obj);
    Optional<T> findById(Long id);
    Optional<T> findByName(String value);
    void delete(T obj);
    List<T> findAll();
}
