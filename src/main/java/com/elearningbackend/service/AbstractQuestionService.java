package com.elearningbackend.service;

import com.elearningbackend.dto.AnswerDto;
import com.elearningbackend.dto.QuestionDto;
import lombok.Getter;
import org.modelmapper.ModelMapper;

import java.util.List;

public abstract class AbstractQuestionService<D,K> implements IAbstractCommonService<D,K> {

    protected final ModelMapper mapper = new ModelMapper();

    @Getter
    protected Class<D> dtoClass;

    public AbstractQuestionService(){}

    public abstract List<AnswerDto> getAnswers(String questionCode,int fetchType,int length);

    public abstract List<QuestionDto> getRandomQuestionDtos(String subcategoryCode);
}
