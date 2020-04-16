package com.github.jjestyy.JavaSIS320.unit11.dto;

import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class QuestionsItemDto extends JournalItemDTO {
    private String name;
    private List<AnswerItemDTO> answers;

    public QuestionsItemDto(Question question, List<Answer> answers) {
        this.id = question.getId().toString();
        this.name = question.getName();
        this.answers = answers.stream().map(AnswerItemDTO::new).collect(Collectors.toList());
    }
}
