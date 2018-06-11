package com.elearningbackend.service;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Pager;

import java.io.Serializable;

public interface IAbstractService<D, K extends Serializable> {
    Pager<D> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction);
    D getOneByKey(K key) throws ElearningException;
    D add(D object) throws ElearningException;
    D edit(D object) throws ElearningException;
    D delete(K key) throws ElearningException;
}
