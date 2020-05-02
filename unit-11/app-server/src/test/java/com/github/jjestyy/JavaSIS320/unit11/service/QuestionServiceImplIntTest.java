package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.Unit11TestConfiguration;
import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDto;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Import(Unit11TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class QuestionServiceImplIntTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Test
    void createQuestion() {
        long countBefore = questionRepository.count();
        long countBeforeAnwers = answerRepository.count();
        QuestionsItemDto dto = TestData.getQuestionsItemDtos().get(1);
        questionService.createQuestion(dto);
        Long countAfter = questionRepository.count();
        Long countAfterAnwers = answerRepository.count();
        Assertions.assertEquals(countAfter, countBefore+1L);
        Assertions.assertEquals(countAfterAnwers, countBeforeAnwers+2L);
    }

    @Test
    void editQuestion() {
        Question question = TestData.getTestQuestionsList().get(1);
        questionRepository.save(question);
        QuestionsItemDto dto = TestData.getQuestionsItemDtos().get(1);
        dto.setId(question.getId().toString());
        dto.setName("some name");
        Assertions.assertEquals(  questionService.editQuestion(dto).getName(),
                questionRepository.findById(Long.parseLong(dto.getId())).get().getName());
    }
}