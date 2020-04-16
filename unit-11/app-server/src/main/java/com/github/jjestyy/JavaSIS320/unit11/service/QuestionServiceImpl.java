package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public List<QuestionsItemDto> getQuestions(JournalRowsRequestDTO req) {
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getPageSize(), Sort.Direction.ASC);
        return questionRepository.findByNameContainingIgnoreCase(req.getSearch(), pageRequest)
            .stream()
            .map(question -> new QuestionsItemDto(question, answerRepository.findByQuestion(question)))
            .collect(Collectors.toList());
    }
}