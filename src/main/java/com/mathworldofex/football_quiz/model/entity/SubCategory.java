package com.mathworldofex.football_quiz.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor

public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subCategory;

    @OneToMany(mappedBy="subCategory")
    private Set<Question> questions;

    public SubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
