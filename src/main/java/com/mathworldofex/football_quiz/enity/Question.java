package com.mathworldofex.football_quiz.enity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String question;

    @OneToOne(cascade = {CascadeType.ALL})
    private Answer answer;

    @OneToOne(cascade = {CascadeType.ALL})
    private QuestionOption questionOption;
}
