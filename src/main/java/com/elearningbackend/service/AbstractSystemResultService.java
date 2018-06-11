package com.elearningbackend.service;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.entity.SystemResult;
import com.elearningbackend.utility.Paginator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractSystemResultService<D,K extends Serializable,E> extends AbstractService<D,K,E> {
    public AbstractSystemResultService(JpaRepository<E, K> repository, Paginator<E, D> paginator) {
        super(repository, paginator);
    }

    public abstract Pager<D> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage);

    public abstract D addOrGetExists(D object) throws ElearningException;

    public abstract List<D> getSystemResultByQuestionCode(String questionCode);
}
