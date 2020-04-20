package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public List<QuestionsItemDTO> getRandomQuestionsList(int size) {
        List<QuestionsItemDTO> questionsItemDTOList = new ArrayList<>();
        List<Long> idsList = new ArrayList<>();

        questionRepository.findAll().forEach(question -> idsList.add(question.getId()));
        Collections.shuffle(idsList);

        questionRepository
                .findAllById(idsList.subList(0, Math.min(idsList.size(), size)))
                .forEach(question ->
                        questionsItemDTOList.add(
                                new QuestionsItemDTO(question,
                                        answerRepository.findByQuestion(question)
                                                        .stream()
                                                        .peek(answer -> answer.setIsCorrect(false))
                                                        .collect(Collectors.toList()))));
        return questionsItemDTOList;
    }
}
