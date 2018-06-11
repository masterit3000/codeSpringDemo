package com.elearningbackend.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class LessionReportId implements Serializable {

    private static final long serialVersionUID = 1L;
    private String lessionReportLessionCode;
    private String lessionReportQuestionCode;

    public LessionReportId() {
    }

    public LessionReportId(String lessionReportLessionCode, String lessionReportQuestionCode) {
        this.lessionReportLessionCode = lessionReportLessionCode;
        this.lessionReportQuestionCode = lessionReportQuestionCode;
    }

    @Column(name = "lession_report_lession_code", nullable = false, length = 100)
    public String getLessionReportLessionCode() {
        return lessionReportLessionCode;
    }

    public void setLessionReportLessionCode(String lessionReportLessionCode) {
        this.lessionReportLessionCode = lessionReportLessionCode;
    }

    @Column(name = "lession_report_question_code", nullable = false, length = 100)
    public String getLessionReportQuestionCode() {
        return lessionReportQuestionCode;
    }

    public void setLessionReportQuestionCode(String lessionReportQuestionCode) {
        this.lessionReportQuestionCode = lessionReportQuestionCode;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        LessionReportId that = (LessionReportId) o;

        if (lessionReportLessionCode != null ?
                !lessionReportLessionCode.equals(that.lessionReportLessionCode) :
                that.lessionReportLessionCode != null)
            return false;
        if (lessionReportQuestionCode != null ?
                !lessionReportQuestionCode.equals(that.lessionReportQuestionCode) :
                that.lessionReportQuestionCode != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = lessionReportLessionCode != null ? lessionReportLessionCode.hashCode() : 0;
        result = 31 * result + (lessionReportQuestionCode != null ? lessionReportQuestionCode.hashCode() : 0);
        return result;
    }
}
