package com.elearningbackend.entity;

import javax.persistence.*;

@Entity 
@Table(name = "lession_report", schema = "e_learning", catalog = "")
public class LessionReport {

    private static final long serialVersionUID = 1L;
    private LessionReportId lessionReportId;
    private String questionContent;
    private int questionType;
    private String questionParentCode;
    private String subcategoryCode;
    private Double questionPoint;
    private Double userPoint;
    private String userAnswer;
    private Lession mappedLession;
    private String incorrectAnswers;
    private String correctAnswers;

    public LessionReport(LessionReportId lessionReportId, String questionContent, String questionParentCode, String subcategoryCode,
            Double questionPoint, Double userPoint, String userAnswer, Lession mappedLession, String incorrectAnswers,
            String correctAnswers) {
        this.lessionReportId = lessionReportId;
        this.questionContent = questionContent;
        this.questionParentCode = questionParentCode;
        this.subcategoryCode = subcategoryCode;
        this.questionPoint = questionPoint;
        this.userPoint = userPoint;
        this.userAnswer = userAnswer;
        this.mappedLession = mappedLession;
        this.incorrectAnswers = incorrectAnswers;
        this.correctAnswers = correctAnswers;
    }

    public LessionReport() {
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "lessionReportLessionCode", column = @Column(name = "lession_report_lession_code", nullable = false, length = 100)),
        @AttributeOverride(name = "lessionReportQuestionCode", column = @Column(name = "lession_report_question_code", nullable = false, length = 100)) })
    public LessionReportId getLessionReportId() {
        return lessionReportId;
    }

    public void setLessionReportId(LessionReportId lessionReportId) {
        this.lessionReportId = lessionReportId;
    }
    
    @Column(name = "question_content")
    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    @Column(name = "quesion_type")
    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    @Column(name = "question_parent_code")
    public String getQuestionParentCode() {
        return questionParentCode;
    }

    public void setQuestionParentCode(String questionParentCode) {
        this.questionParentCode = questionParentCode;
    }

    @Column(name = "subcategory_code")
    public String getSubcategoryCode() {
        return subcategoryCode;
    }

    public void setSubcategoryCode(String subcategoryCode) {
        this.subcategoryCode = subcategoryCode;
    }

    @Column(name = "question_point")
    public Double getQuestionPoint() {
        return questionPoint;
    }

    public void setQuestionPoint(Double questionPoint) {
        this.questionPoint = questionPoint;
    }

    @Column(name = "user_point")
    public Double getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(Double userPoint) {
        this.userPoint = userPoint;
    }

    @Column(name = "user_answer")
    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Column(name = "correct_answers")
    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    @Column(name = "incorrect_answers")
    public String getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(String incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    @ManyToOne
    @JoinColumn(name = "lession_report_lession_code", referencedColumnName = "lession_code", nullable = false, insertable = false, updatable = false)
    public Lession getMappedLession() {
        return mappedLession;
    }

    public void setMappedLession(Lession lessionByLessionReportLessionCode) {
        this.mappedLession = lessionByLessionReportLessionCode;
    }
}
