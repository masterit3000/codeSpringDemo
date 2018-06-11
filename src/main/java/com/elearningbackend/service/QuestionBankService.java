/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.entity.QuestionBank;
import com.elearningbackend.repository.IQuestionBankRepository;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author c1508l3694
 */
@Service
@Transactional
public class QuestionBankService extends AbstractCustomService<QuestionBankDto, String, QuestionBank> {

    @Autowired
    public QuestionBankService(IQuestionBankRepository repository) {
        super(repository, new Paginator<>(QuestionBankDto.class));
    }

    @Override
    public Pager<QuestionBankDto> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction) {
        Page<QuestionBank> pager = getQuestionRepository().findAll(Paginator.getValidPageRequest(currentPage, noOfRowInPage,
                ServiceUtils.proceedSort(sortBy, direction)));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public QuestionBankDto getOneByKey(String questionCode) throws ElearningException {
        QuestionBank question = getQuestionRepository().findOne(questionCode);
        if (question == null) {
            throw new ElearningException(Errors.QUESTION_NOT_FOUND.getId(), Errors.QUESTION_NOT_FOUND.getMessage());
        }
        return mapper.map(question, QuestionBankDto.class);
    }

    @Override
    public Pager<QuestionBankDto> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage) {
        Page<QuestionBank> pager = getQuestionRepository().fetchQuestionByCreator(
                creatorUsername, new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }


    @Override
    public QuestionBankDto addOrGetExists(QuestionBankDto object) {
        try {
            return getOneByKey(object.getQuestionCode());
        } catch (ElearningException e) {
            List<QuestionBank> questionBanks = getQuestionRepository().uniqueQuestion(object.getQuestionType(), object.getQuestionContent()
                    , object.getPoint(), object.getSubcategory().getSubcategoryCode());
            if (questionBanks.size() > 0)
                return mapper.map(questionBanks.get(Constants.ZERO), QuestionBankDto.class);
        }
        saveQuestion(object);
        return object;
    }

    @Override
    public QuestionBankDto add(QuestionBankDto question) throws ElearningException {
        try {
            saveQuestion(question);
            return question;
        } catch (Exception e){
            //TODO
            throw new ElearningException("Cannot add new question");
        }
    }

    @Override
    public QuestionBankDto edit(QuestionBankDto question) throws ElearningException {
        try {
            getOneByKey(question.getQuestionCode());
            saveQuestion(question);
            return question;
        } catch (ElearningException e) {
            throw new ElearningException(e.getErrorCode(), e.getMessage());
        } catch (Exception e){
            throw new ElearningException("Cannot edit question");
        }
    }

    @Override
    public QuestionBankDto delete(String questionCode) throws ElearningException {
        QuestionBankDto questionByCode = getOneByKey(questionCode);
        if (questionByCode != null) {
            List<QuestionBank> questionBanks = getQuestionRepository().fetchQuestionChild(questionCode);
            for (QuestionBank questionBank : questionBanks) {
                getQuestionRepository().delete(mapper.map(questionBank, QuestionBank.class));
            }
            getQuestionRepository().delete(mapper.map(questionByCode, QuestionBank.class));

            return questionByCode;
        }
        //TODO
        throw new ElearningException("Cannot delete question");
    }

    Pager<QuestionBankDto> getBySubcategoryCode(String subcategoryCode, int currentPage, int noOfRowInPage) {
        Page<QuestionBank> pager = getQuestionRepository().fetchQuestionBySubcategoryCode(
            subcategoryCode, new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    List<QuestionBankDto> getParentAndSiblings(String questionParentCode) {
        List<QuestionBank> questionBanks = getQuestionRepository().fetchParentAndSiblings(questionParentCode);
        return questionBanks.stream().map(e -> mapper.map(e, QuestionBankDto.class)).collect(Collectors.toList());
    }

    boolean hasChildren(String questionCode){
        return getQuestionRepository().countAllByQuestionParentCode(questionCode) > 0;
    }

    List<QuestionBankDto> getChildren(String questionCode){
        return hasChildren(questionCode) ? getQuestionRepository().fetchQuestionChild(questionCode).stream()
            .map(e -> mapper.map(e, QuestionBankDto.class)).collect(Collectors.toList()) : null;
    }

    private void saveQuestion(QuestionBankDto question) {
        getQuestionRepository().save(mapper.map(question, QuestionBank.class));
    }

    private IQuestionBankRepository getQuestionRepository() {
        return (IQuestionBankRepository) getRepository();
    }
}
