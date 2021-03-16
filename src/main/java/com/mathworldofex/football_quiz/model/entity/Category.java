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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String category;

    @OneToMany(mappedBy="category")
    private Set<Question> questions;

    public Category(String category) {
        this.category = category;
    }
}
