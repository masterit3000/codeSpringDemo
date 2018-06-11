/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.service;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.utility.Paginator;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author c1508l3694
 */
public abstract class AbstractUserService<D, K extends Serializable, E> extends AbstractService<D, K, E>{
    public AbstractUserService(JpaRepository<E, K> repository, Paginator<E, D> paginator) {
        super(repository, paginator);
    }

    public abstract UserDto getOneByEmail(String email);

    public abstract boolean active(UserDto userDto) throws ElearningException;

    public abstract UserDto updateAvatar(String avatarUrl, String username) throws ElearningException;

    public abstract UserDto updateRole(String key, String role) throws ElearningException;
}
