package com.mathworldofex.football_quiz.model.repository;

import com.mathworldofex.football_quiz.model.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findBySubCategory(String category);
}
