package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDto;

import java.util.List;

public interface QuestionService {
    List<QuestionsItemDto> getQuestions(JournalRowsRequestDTO req);
}
