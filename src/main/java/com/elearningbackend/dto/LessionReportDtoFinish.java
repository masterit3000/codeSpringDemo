package com.elearningbackend.dto;

import com.elearningbackend.entity.LessionReportId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LessionReportDtoFinish implements Comparable<LessionReportDtoFinish>{
    private LessionReportId lessionReportId;
    private String questionContent;
    private int questionType;
    private String questionParentCode;
    private String subcategoryCode;
    private Double questionPoint;
    private List<AnswerDto> userAnswers;
    @JsonIgnore
    private LessionDto mappedLessionDto;
    private Double userPoint;

    private List<AnswerDto> correctAnswers;
    private List<AnswerDto> incorrectAnswers;

    @Override
    public int compareTo(LessionReportDtoFinish o) {
        return this.getLessionReportId().getLessionReportQuestionCode().compareTo(o.getLessionReportId().getLessionReportQuestionCode());
    }
}
