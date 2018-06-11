package com.elearningbackend.dto;

import com.elearningbackend.entity.LessionReportId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
public class LessionReportDto implements Comparable<LessionReportDto> {
    private LessionReportId lessionReportId;
    private String questionContent;
    private int questionType;
    private String questionParentCode;
    private String subcategoryCode;
    private Double questionPoint;
    private List<AnswerDto> answers;
    private List<AnswerDto> userAnswers;
    @JsonIgnore
    private LessionDto mappedLessionDto;
    private Double userPoint;

    @JsonIgnore
    private List<AnswerDto> correctAnswers;
    @JsonIgnore
    private List<AnswerDto> incorrectAnswers;

    @Override
    public int compareTo(LessionReportDto o) {
        return this.getLessionReportId().getLessionReportQuestionCode().compareTo(o.getLessionReportId().getLessionReportQuestionCode());
    }
}
