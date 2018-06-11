package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.AnswerBankDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.dto.SystemResultDto;
import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.entity.SystemResult;
import com.elearningbackend.entity.SystemResultId;
import com.elearningbackend.repository.ISystemResultRepository;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SystemResultService extends AbstractSystemResultService<SystemResultDto, SystemResultId, SystemResult> {

    @Autowired
    public SystemResultService(JpaRepository<SystemResult, SystemResultId> repository) {
        super(repository, new Paginator<>(SystemResultDto.class));
    }

    @Autowired
    @Qualifier("questionBankService")
    private IAbstractService<QuestionBankDto, String> abstractQuestionBankSercive;

    @Autowired
    @Qualifier("answerBankService")
    private AbstractCustomService<AnswerBankDto, String, AnswerBank> abstracAnswerBankService;

    @Override
    public Pager<SystemResultDto> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage) {
        return null;
    }

    @Override
    public SystemResultDto addOrGetExists(SystemResultDto object) throws ElearningException {
        checkQuestionAndAnswerCode(object);
        SystemResultId systemResultId = getSystemResultId(object);
        List<SystemResult> systemResults = duplicateSystemResult(systemResultId,object);
        if (systemResults.size()>0)
            return mapper.map(systemResults.get(Constants.ZERO),SystemResultDto.class);
        object.setSystemResultId(systemResultId);
        saveSystemResult(object);
        return object;
    }

    @Override
    public Pager<SystemResultDto> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction) {
        Page<SystemResult> page = getSystemResultRepository().findAll(
                Paginator.getValidPageRequest(currentPage, noOfRowInPage, ServiceUtils.proceedSort(sortBy, direction)));
        return paginator.paginate(currentPage,page,noOfRowInPage,mapper);
    }

    @Override
    public SystemResultDto getOneByKey(SystemResultId key) throws ElearningException {
        SystemResult systemResult = getSystemResultRepository().findOne(key);
        if (systemResult == null){
            throw new ElearningException(Errors.SYSTEM_RESULT_NOT_EXITS.getId(),Errors.SYSTEM_RESULT_NOT_EXITS.getMessage());
        }
        return mapper.map(systemResult, SystemResultDto.class);
    }

    @Override
    public SystemResultDto add(SystemResultDto object) throws ElearningException {
        checkQuestionAndAnswerCode(object);
        SystemResultId systemResultId = getSystemResultId(object);
        if (duplicateSystemResult(systemResultId,object).size()>0)
            throw new ElearningException(Errors.SYSTEM_RESULT_ID_EXIST.getId(),
                    Errors.SYSTEM_RESULT_ID_EXIST.getMessage());
        object.setSystemResultId(systemResultId);
        saveSystemResult(object);
        return object;
    }

    @Override
    public SystemResultDto edit(SystemResultDto object) throws ElearningException {
        checkQuestionAndAnswerCode(object);
        SystemResultId systemResultId = getSystemResultId(object);
        getOneByKey(systemResultId);
        saveSystemResult(object);
        return object;
    }

    @Override
    public SystemResultDto delete(SystemResultId key) throws ElearningException {
        SystemResultDto systemResultDto = getOneByKey(key);
        getSystemResultRepository().delete(key);
        return systemResultDto;
    }

    @Override
    public List<SystemResultDto> getSystemResultByQuestionCode(String questionCode){
        List<SystemResult> systemResults = getSystemResultRepository().fetchSystemResultByQuestionCode(questionCode);
        if(systemResults==null)
            systemResults = new ArrayList();
        return systemResults.stream().map(e -> mapper.map(e,SystemResultDto.class)).collect(Collectors.toList());
    }

    private ISystemResultRepository getSystemResultRepository() {
        return (ISystemResultRepository) getRepository();
    }

    private void saveSystemResult(SystemResultDto systemResultDto){
        getSystemResultRepository().save(mapper.map(systemResultDto, SystemResult.class));
    }

    private void checkQuestionAndAnswerCode(SystemResultDto object) throws ElearningException {
        try{
            /**
             * getOneByKey method is method throw exception if not exists
             * => this step only  use try catch and throw exception if child method occurs exception
             */
            abstractQuestionBankSercive.getOneByKey(object.getSystemResultId().getSystemResultQuestionCode());
            abstracAnswerBankService.getOneByKey(object.getSystemResultId().getSystemResultAnswerCode());
        }catch(ElearningException e){
            throw new ElearningException(Errors.ANSWER_OR_QUESTION_NOT_EXITS.getId(),
                    Errors.ANSWER_OR_QUESTION_NOT_EXITS.getMessage());
        }
    }

    private List<SystemResult> duplicateSystemResult(SystemResultId systemResultId,SystemResultDto object){
        return getSystemResultRepository().uniqueSystemResult(systemResultId.getSystemResultQuestionCode()
                ,systemResultId.getSystemResultAnswerCode(),object.getSystemResultPosition());
    }

    private SystemResultId getSystemResultId(SystemResultDto object) {
        return new SystemResultId(object.getSystemResultId().getSystemResultQuestionCode(),
                object.getSystemResultId().getSystemResultAnswerCode());
    }
}
