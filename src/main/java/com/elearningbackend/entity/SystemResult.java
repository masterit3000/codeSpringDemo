package com.elearningbackend.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "system_result", schema = "e_learning", catalog = "")
public class SystemResult {
    private static final long serialVersionUID = 1L;

    private SystemResultId systemResultId;
    private QuestionBank questionBank;
    private AnswerBank answerBank;
    private int systemResultPosition;
    private int systemResultIsCorrect;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private String lastUpdaterUsername;

    public SystemResult(SystemResultId systemResultId, int systemResultPosition, int systemResultIsCorrect, Timestamp creationDate, Timestamp lastUpdateDate, String lastUpdaterUsername) {
        this.systemResultId = systemResultId;
        this.systemResultPosition = systemResultPosition;
        this.systemResultIsCorrect = systemResultIsCorrect;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdaterUsername = lastUpdaterUsername;
    }

    public SystemResult() {
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "systemResultQuestionCode", column = @Column(name = "system_result_question_code", nullable = false, length = 100)),
        @AttributeOverride(name = "systemResultAnswerCode", column = @Column(name = "system_result_answer_code", nullable = false, length = 100)) })
    public SystemResultId getSystemResultId() {
        return systemResultId;
    }

    public void setSystemResultId(SystemResultId systemResultId) {
        this.systemResultId = systemResultId;
    }

    @Column(name = "system_result_position", nullable = false)
    public int getSystemResultPosition() {
        return systemResultPosition;
    }

    public void setSystemResultPosition(int systemResultPosition) {
        this.systemResultPosition = systemResultPosition;
    }

    @Column(name = "system_result_is_correct", nullable = false)
    public int getSystemResultIsCorrect() {
        return systemResultIsCorrect;
    }

    public void setSystemResultIsCorrect(int systemResultIsCorrect) {
        this.systemResultIsCorrect = systemResultIsCorrect;
    }

    @Column(name = "creation_date", nullable = true)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "last_update_date", nullable = true)
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_updater_username", nullable = true, length = 255)
    public String getLastUpdaterUsername() {
        return lastUpdaterUsername;
    }

    public void setLastUpdaterUsername(String lastUpdaterUsername) {
        this.lastUpdaterUsername = lastUpdaterUsername;
    }

    @ManyToOne
    @JoinColumn(name = "system_result_question_code", nullable = false, insertable = false, updatable = false)
    public QuestionBank getQuestionBank() {
        return this.questionBank;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    @ManyToOne
    @JoinColumn(name = "system_result_answer_code", nullable = false, insertable = false, updatable = false)
    public AnswerBank getAnswerBank() {
        return this.answerBank;
    }

    public void setAnswerBank(AnswerBank answerBank) {
        this.answerBank = answerBank;
    }

}
