package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class QuestionServiceImplIntTest {
    @Autowired
    private TestEntityManager entityManager;
    @TestConfiguration
    static class QuestionServiceImplTestContextConfiguration {
        @Bean
        public QuestionService questionService() {
            return new QuestionServiceImpl();
        }
    }
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void createQuestion() {
        long countBefore = questionRepository.count();
        QuestionsItemDTO dto = TestData.getQuestionsItemDtos().get(1);
        questionService.createQuestion(dto);
        Long countAfter = questionRepository.count();
        Assertions.assertEquals(countAfter, countBefore+1L);
    }

    @Test
    void editQuestion() {
        Question question = TestData.getTestQuestionsList().get(1);
        questionRepository.save(question);
        QuestionsItemDTO dto = TestData.getQuestionsItemDtos().get(1);
        dto.setId(question.getId().toString());
        Assertions.assertEquals(  questionService.editQuestion(dto).getName(),
                questionRepository.findById(Long.parseLong(dto.getId())).get().getName());
    }
}