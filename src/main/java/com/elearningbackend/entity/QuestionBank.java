package com.elearningbackend.entity;

import com.elearningbackend.utility.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "question_bank", schema = "e_learning")
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;
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
    private Set<SystemResult> systemResults;

    public QuestionBank() {
    }

    public QuestionBank(String questionCode) {
            this.questionCode = questionCode;
    }

    @Id
    @Column(name = "question_code", unique = true, nullable = false, length = 100)
    public String getQuestionCode() {
        return this.questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    @Column(name = "question_type")
    public int getQuestionType() {
            return this.questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    @Column(name = "question_content", length = 65535)
    public String getQuestionContent() {
        return this.questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    @Column(name = "question_parent_code", length = 100)
    public String getQuestionParentCode() {
        return this.questionParentCode;
    }

    public void setQuestionParentCode(String questionParentCode) {
        this.questionParentCode = questionParentCode;
    }

    @Column(name = "creation_date")
    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "last_update_date")
    public Timestamp getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "creator_username", nullable = false)
    public String getCreatorUsername() {
        return this.creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    @Column(name = "last_updater_username")
    public String getLastUpdaterUsername() {
        return this.lastUpdaterUsername;
    }

    public void setLastUpdaterUsername(String lastUpdaterUsername) {
        this.lastUpdaterUsername = lastUpdaterUsername;
    }

    @Column(name = "point")
    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        QuestionBank that = (QuestionBank) o;

        if (point != null ? !point.equals(that.point) : that.point != null)
            return false;

        return true;
    }

    @ManyToOne
    @JoinColumn(name = "subcategory_code", nullable = false)
    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    @OneToMany(mappedBy = "questionBank", cascade = CascadeType.ALL)
    public Set<SystemResult> getSystemResults(){
        return this.systemResults;
    }

    public void setSystemResults(Set<SystemResult> systemResults){
        this.systemResults = systemResults;
    }
}
