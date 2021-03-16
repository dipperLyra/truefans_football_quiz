package com.mathworldofex.football_quiz.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    @ManyToOne
    @JoinColumn(name="subcategory_id", nullable=false)
    private SubCategory subCategory;

    @OneToOne(cascade = {CascadeType.ALL})
    private Answer answer;

    @OneToOne(cascade = {CascadeType.ALL})
    private QuestionOption questionOption;

    public Question(String question, Answer answer, QuestionOption questionOption) {
        this.question = question;
        this.answer = answer;
        this.questionOption = questionOption;
    }

    public Question(String question, Category category, SubCategory subCategory, Answer answer, QuestionOption questionOption) {
        this.question = question;
        this.category = category;
        this.subCategory = subCategory;
        this.answer = answer;
        this.questionOption = questionOption;
    }

    //NB: only for debug purposes
    @Override
    public String toString () {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
