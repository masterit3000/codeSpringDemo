package com.elearningbackend.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SystemResultId implements Serializable {

    private static final long serialVersionUID = 1L;
    private String systemResultQuestionCode;
    private String systemResultAnswerCode;

    public SystemResultId() {
    }

    public SystemResultId(String systemResultQuestionCode, String systemResultAnswerCode) {
        this.systemResultQuestionCode = systemResultQuestionCode;
        this.systemResultAnswerCode = systemResultAnswerCode;
    }

    @Column(name = "system_result_question_code", nullable = false, length = 100)
    public String getSystemResultQuestionCode() {
        return systemResultQuestionCode;
    }

    public void setSystemResultQuestionCode(String systemResultQuestionCode) {
        this.systemResultQuestionCode = systemResultQuestionCode;
    }

    @Column(name = "system_result_answer_code", nullable = false, length = 100)
    public String getSystemResultAnswerCode() {
        return systemResultAnswerCode;
    }

    public void setSystemResultAnswerCode(String systemResultAnswerCode) {
        this.systemResultAnswerCode = systemResultAnswerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemResultId that = (SystemResultId) o;

        if (systemResultQuestionCode != null ? !systemResultQuestionCode.equals(that.systemResultQuestionCode) : that.systemResultQuestionCode != null)
            return false;
        if (systemResultAnswerCode != null ? !systemResultAnswerCode.equals(that.systemResultAnswerCode) : that.systemResultAnswerCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = systemResultQuestionCode != null ? systemResultQuestionCode.hashCode() : 0;
        result = 31 * result + (systemResultAnswerCode != null ? systemResultAnswerCode.hashCode() : 0);
        return result;
    }
}
