package com.mathworldofex.football_quiz.model.repository;

import com.mathworldofex.football_quiz.model.entity.Category;
import com.mathworldofex.football_quiz.model.entity.Question;
import com.mathworldofex.football_quiz.model.entity.SubCategory;
import com.mathworldofex.football_quiz.model.payload.response.QuestionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT q.question, q.id FROM question q WHERE q.subcategory_id = :subCategoryId AND q.category_id = :categoryId", nativeQuery = true)
    List<QuestionProjection> findAllByCategoryIdAndSubCategoryId(@Param("categoryId") Long categoryId, @Param("subCategoryId") Long subCategoryId);

    @Query(value = "SELECT id from question", nativeQuery = true)
    List<Long> getAllId();

    @Query(value = "SELECT q.answer_id, q.id, q.question, q.question_classification_id, q.question_option_id FROM question q \n"
                + " WHERE q.question_classification_id = :id", nativeQuery = true)
    List<Question> getAllByQuestionClassificationId(@Param("id") long id);

    List<Question> findAllByCategoryAndSubCategory(Category category, SubCategory subCategory);

}
