package com.elearningbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerDto {
    private AnswerBankDto answerBankDto;
    private SystemResultDto systemResultDto;
}
