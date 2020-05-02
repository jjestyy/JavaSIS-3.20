package com.github.jjestyy.JavaSIS320.unit11.dto;

import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AnswerItemDto {
    private String id;
    private String answerText;
    private Boolean isCorrect;

    public AnswerItemDto(Answer answer) {
        this.id = answer.getId().toString();
        this.answerText = answer.getName();
        this.isCorrect = answer.getIsCorrect();
    }
}
