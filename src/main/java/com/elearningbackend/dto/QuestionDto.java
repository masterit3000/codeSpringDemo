package com.elearningbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    /**
     * Class dto for user add question + answer and backend auto add system result
     */
    private QuestionBankDto questionBankDto;
    private List<AnswerDto> answerDtos;
}
