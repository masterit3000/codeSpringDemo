package com.elearningbackend.dto;

import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.entity.QuestionBank;
import com.elearningbackend.entity.SystemResultId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class SystemResultDto {
    private SystemResultId systemResultId;
    private QuestionBank questionBank;
    private AnswerBank answerBank;
    private int systemResultPosition;
    private int systemResultIsCorrect;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private String lastUpdaterUsername;
}
