/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.dto;

import com.elearningbackend.entity.Subcategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 *
 * @author c1508l3694
 */
@Data
@NoArgsConstructor
public class QuestionBankDto {
    private String questionCode;
    private int questionType;
    private String questionContent;
    private String questionParentCode;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private String creatorUsername;
    private String lastUpdaterUsername;
    private Double point;
    private Subcategory subcategory;

    public QuestionBankDto(String questionCode, String questionContent) {
        this.questionCode = questionCode;
        this.questionContent = questionContent;
    }

    public QuestionBankDto(String questionCode, int questionType, String questionContent, String questionParentCode, Timestamp creationDate, Timestamp lastUpdateDate, String creatorUsername, String lastUpdaterUsername, Double point, Subcategory subcategory) {
        this.questionCode = questionCode;
        this.questionType = questionType;
        this.questionContent = questionContent;
        this.questionParentCode = questionParentCode;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.creatorUsername = creatorUsername;
        this.lastUpdaterUsername = lastUpdaterUsername;
        this.point = point;
        this.subcategory = subcategory;
    }
}
