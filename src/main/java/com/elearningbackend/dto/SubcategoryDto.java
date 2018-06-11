package com.elearningbackend.dto;

import com.elearningbackend.entity.Category;
import com.elearningbackend.entity.QuestionBank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
public class SubcategoryDto {
    private String subcategoryCode;
    private String displayName;
    private String categoryCode;
    private String subcategoryIntro;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private Set<QuestionBank> questionBanks;

    public SubcategoryDto(String subcategoryCode, Timestamp creationDate, Timestamp lastUpdateDate) {
        this.subcategoryCode = subcategoryCode;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
    }
}
