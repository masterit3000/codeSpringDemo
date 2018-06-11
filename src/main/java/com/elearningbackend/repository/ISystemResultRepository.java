package com.elearningbackend.repository;

import com.elearningbackend.entity.SystemResult;
import com.elearningbackend.entity.SystemResultId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ISystemResultRepository extends JpaRepository<SystemResult, SystemResultId>{
    @Query("select a from SystemResult a where a.questionBank.questionCode like ?1 and a.answerBank.answerCode like ?2 and a.systemResultPosition = ?3")
    List<SystemResult> uniqueSystemResult(String questionCode, String answerCode,int position);

    @Query("select a from SystemResult a where a.systemResultId.systemResultQuestionCode like ?1")
    List<SystemResult> fetchSystemResultByQuestionCode(String questionCode);

    @Query("select a from SystemResult a where a.systemResultId.systemResultAnswerCode like ?1")
    List<SystemResult> fetchSystemResultByAnswerCode(String answerCode);
}
