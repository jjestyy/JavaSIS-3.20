package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDTO;

import java.util.List;

public interface QuestionService {
    List<QuestionsItemDTO> getQuestions(JournalRowsRequestDTO req);
    QuestionsItemDTO createQuestion(QuestionsItemDTO dto);
    QuestionsItemDTO editQuestion(QuestionsItemDTO dto);
}
