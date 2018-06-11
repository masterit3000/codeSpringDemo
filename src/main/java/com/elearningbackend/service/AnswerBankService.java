package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.AnswerBankDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.repository.IAnswerBankRepository;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AnswerBankService extends AbstractCustomService<AnswerBankDto, String, AnswerBank>{

    @Autowired
    public AnswerBankService(IAnswerBankRepository repository) {
        super(repository, new Paginator<>(AnswerBankDto.class));
    }

    @Override
    public Pager<AnswerBankDto> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction) {
        Page<AnswerBank> pager = getAnswerRepository().findAll(Paginator.getValidPageRequest(currentPage, noOfRowInPage,
            ServiceUtils.proceedSort(sortBy, direction)));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public AnswerBankDto getOneByKey(String key) throws ElearningException {
        AnswerBank answer = getAnswerRepository().findOne(key);
        if (answer == null) {
            throw new ElearningException(Errors.ANSWER_NOT_EXITS.getId(), Errors.ANSWER_NOT_EXITS.getMessage());
        }
        return mapper.map(answer, AnswerBankDto.class);
    }

    @Override
    public Pager<AnswerBankDto> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage) {
        Page<AnswerBank> pager = getAnswerRepository().fetchAnswerByCreator(
            creatorUsername, new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public AnswerBankDto addOrGetExists(AnswerBankDto answerBankDto) {
        try {
            return getOneByKey(answerBankDto.getAnswerCode());
        } catch (ElearningException e) {
            if(hasAnswerByContent(answerBankDto.getAnswerContent()))
                return mapper.map(getAnswerRepository().fetchAnswerByContent(answerBankDto.getAnswerContent()).get(Constants.ZERO)
                        , AnswerBankDto.class);
        }
        saveAnswer(answerBankDto);
        return answerBankDto;
    }

    @Override
    public AnswerBankDto add(AnswerBankDto answer) throws ElearningException {
        if (getAnswerRepository().findOne(answer.getAnswerCode()) != null
                || hasAnswerByContent(answer.getAnswerContent()))
            throw new ElearningException(Errors.ANSWER_EXIST.getId(), Errors.ANSWER_EXIST.getMessage());
        saveAnswer(answer);
        return answer;
    }

    @Override
    public AnswerBankDto edit(AnswerBankDto answer) throws ElearningException {
        getOneByKey(answer.getAnswerCode());
        saveAnswer(answer);
        return answer;
    }

    @Override
    public AnswerBankDto delete(String key) throws ElearningException {
        AnswerBankDto answerByCode = getOneByKey(key);
        getAnswerRepository().delete(key);
        return answerByCode;
    }

    private boolean hasAnswerByContent(String content){
        return getAnswerRepository().fetchAnswerByContent(content).size() >0;
    }

    private IAnswerBankRepository getAnswerRepository() {
        return (IAnswerBankRepository) getRepository();
    }

    private void saveAnswer(AnswerBankDto answer) {
        getAnswerRepository().save(mapper.map(answer, AnswerBank.class));
    }
}
