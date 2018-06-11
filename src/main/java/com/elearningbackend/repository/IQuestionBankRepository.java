package com.elearningbackend.repository;

import com.elearningbackend.entity.QuestionBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuestionBankRepository extends JpaRepository<QuestionBank, String> {
    @Query("select a from QuestionBank a where a.creatorUsername like %?1%")
    Page<QuestionBank> fetchQuestionByCreator(String creatorUsername, Pageable pageable);
    @Query("select a from QuestionBank a where a.subcategory.subcategoryCode = ?1")
    Page<QuestionBank> fetchQuestionBySubcategoryCode(String subcategoryCode, Pageable pageable);
    @Query("select a from QuestionBank a where a.questionType = ?1 and a.questionContent like ?2 and a.point = ?3 and a.subcategory.subcategoryCode = ?4 ")
    List<QuestionBank> uniqueQuestion(int type,String content,double point,String subcategoryCode);
    @Query("select a from QuestionBank a where a.questionParentCode = ?1 or a.questionCode = ?1")
    List<QuestionBank> fetchParentAndSiblings(String questionParentCode);
    @Query("select count(a) from QuestionBank a where a.questionParentCode is not null and a.questionParentCode =?1")
    int countChildrenQuestionInNestedParent(String questionParentCode);

    int countAllBySubcategorySubcategoryCode(String subcategoryCode);
    int countAllByQuestionParentCode(String questionParentCode);

    @Query("select a from QuestionBank a where a.questionParentCode = ?1")
    List<QuestionBank> fetchQuestionChild(String questoinPrarentCode);
}
