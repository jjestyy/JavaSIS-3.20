package com.github.jjestyy.JavaSIS320.unit11.dto;

import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionResult {

    private Question question;
    private int countOfAllAnswers;
    private int countOfCorrectAnswers;
    private int countOfAllCorrectAnswers;
    private int countOfWrongAnswers;

    public void addAnswer(Boolean isRight) {
        if (isRight) {
            countOfCorrectAnswers++;
        } else {
            countOfWrongAnswers++;
        }
    }

    public QuestionResult(Question question, int countOfAllAnswers, int countOfAllCorrectAnswers) {
        this.question = question;
        this.countOfAllAnswers = countOfAllAnswers;
        this.countOfAllCorrectAnswers = countOfAllCorrectAnswers;
    }
}
