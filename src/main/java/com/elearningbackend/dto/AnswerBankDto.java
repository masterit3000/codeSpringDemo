package com.elearningbackend.dto;

import com.elearningbackend.utility.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class AnswerBankDto {
    private String answerCode;

    @NotBlank(message = "Answer content cannot null or empty!")
    private String answerContent;

    @NotBlank(message = "Creation Date cannot null or empty!")
    private Timestamp creationDate;

    @NotBlank(message = "Update Date cannot null or empty!")
    private Timestamp lastUpdateDate;

    @NotBlank(message = "User name create cannot null or empty!")
    private String creatorUsername;

    @NotBlank(message = "User name last update cannot null or empty!")
    private String lastUpdaterUsername;

    public AnswerBankDto(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }
}
