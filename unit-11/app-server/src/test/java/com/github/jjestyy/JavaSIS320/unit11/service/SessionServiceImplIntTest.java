package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class SessionServiceImplIntTest {

    @Autowired
    private TestEntityManager entityManager;

    @TestConfiguration
    static class SessionServiceImplTestContextConfiguration {
        @Bean
        public SessionService sessionService() {
            return new SessionServiceImpl();
        }
    }

    @Autowired
    private SessionService sessionService;

    List<Question> questionList = TestData.getTestQuestionsList();
    List<Answer> answerList = TestData.getTestAnswersList(questionList);

    @BeforeEach
    void setUp() {
        for(Question question: questionList) {
            entityManager.persistAndGetId(question);
            entityManager.flush();
        }

        for(Answer answer: answerList){
            entityManager.persistAndGetId(answer);
            entityManager.flush();
        }
    }

    @Test
    void getRandomQuestionsList() {
        answerList = answerList.stream().peek(answer -> answer.setIsCorrect(false)).collect(Collectors.toList());
        List<QuestionsItemDTO> expectedQuestionsItemDTOList =
                TestData.getQuestionsItemDtosFromQuestionsAndAnswers(questionList, answerList);
        //case 1 repository more than session size
        List <QuestionsItemDTO> randomQuestionsList = sessionService.getRandomQuestionsList(3);
        assertEquals(randomQuestionsList.size() , 3);
        System.out.println(expectedQuestionsItemDTOList.toString());
        System.out.println(randomQuestionsList.toString());
        randomQuestionsList.forEach(question -> assertTrue(expectedQuestionsItemDTOList.contains(question)));
//        assertThat(expectedQuestionsItemDTOList, containsInAnyOrder(randomQuestionsList));
        //case 2 repository less or equal than session size
        List<QuestionsItemDTO> randomQuestionsList1 = sessionService.getRandomQuestionsList(10);
        assertEquals(randomQuestionsList1.size(), expectedQuestionsItemDTOList.size());
        System.out.println(randomQuestionsList1.toString());
        randomQuestionsList1.forEach(question -> assertTrue(expectedQuestionsItemDTOList.contains(question)));
        expectedQuestionsItemDTOList.forEach(question -> assertTrue(randomQuestionsList1.contains(question)));
//        assertThat(expectedQuestionsItemDTOList, containsInAnyOrder(randomQuestionsList1));
//        assertThat(randomQuestionsList1, containsInAnyOrder(expectedQuestionsItemDTOList));
    }
}
