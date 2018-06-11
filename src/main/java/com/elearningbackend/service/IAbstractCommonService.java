package com.elearningbackend.service;

import com.elearningbackend.customexception.ElearningException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IAbstractCommonService<D, K> {
    List<D> add(List<D> object) throws ElearningException;
    D getOneByKey(K key) throws ElearningException;
    D edit(D object) throws ElearningException;
    D delete(K key) throws ElearningException;
}
