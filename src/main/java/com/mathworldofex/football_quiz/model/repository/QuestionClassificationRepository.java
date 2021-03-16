package com.mathworldofex.football_quiz.model.repository;

import com.mathworldofex.football_quiz.model.entity.QuestionClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionClassificationRepository extends JpaRepository<QuestionClassification, Long> {

    @Query(value =
            "SELECT qc.id, qc.category, qc.sub_category FROM question_classification qc \n" +
                    "WHERE (category, sub_category) IN ((:category, :subcategory))",
            nativeQuery = true
    )
    List<QuestionClassification> findQuestionClassificationByCategoryAndSubCategory(
            @Param("category") String category,
            @Param("subcategory") String subCategory
    );
}
