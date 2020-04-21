package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.JournalRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDTO;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class QuestionServiceImplTest {
    @TestConfiguration
    static class QuestionServiceImplTestContextConfiguration {
        @Bean
        public QuestionService questionService() {
            return new QuestionServiceImpl();
        }
    }
    @Autowired
    private QuestionService questionService;
    @MockBean
    private QuestionRepository questionRepository;
    @MockBean
    private AnswerRepository answerRepository;

    List<Question> questionList = TestData.getTestQuestionsList();
    List<Answer> answerList = TestData.getTestAnswersList(questionList);

    @Test
    void getQuestions() {
        for (int i = 0; i< questionList.size(); i++ ) {
            questionList.get(i).setId((long) i+1);
        }
        for (int i = 0; i< answerList.size(); i++ ) {
            answerList.get(i).setId((long) i+1);
        }
        JournalRowsRequestDTO req = TestData.getJournalRowsRequestDTO(false,false,false);
        JournalRowsRequestDTO req2 = TestData.getJournalRowsRequestDTO(false,false,true);

        PageRequest pageRequest = PageRequest.of(req.getPage()-1, req.getPageSize(), Sort.by(Sort.Direction.ASC, "id"));
        when(questionRepository.findByNameContainingIgnoreCase(req.getSearch(), pageRequest))
            .thenReturn(questionList);
        when(questionRepository.findByNameContainingIgnoreCase(req2.getSearch(), pageRequest))
            .thenReturn(questionList);

        when(answerRepository.findByQuestion(questionList.get(0))).thenReturn(answerList.subList(0,3));
        when(answerRepository.findByQuestion(questionList.get(1))).thenReturn(answerList.subList(2,4));
        when(answerRepository.findByQuestion(questionList.get(2))).thenReturn(answerList.subList(5,8));
        when(answerRepository.findByQuestion(questionList.get(3))).thenReturn(answerList.subList(8,9));

        assertEquals(questionService.getQuestions(req), TestData.getQuestionsItemDtos());
        assertEquals(questionService.getQuestions(req2), TestData.getQuestionsItemDtos().subList(1,2));

    }
}