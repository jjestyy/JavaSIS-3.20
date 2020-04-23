package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.Unit11TestConfiguration;
import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.SelectedAnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.SessionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.AnsweredQuestionDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.SessionDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.SessionQuestionAnswerDTO;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.AssertTrue;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@Import(Unit11TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class SessionServiceImplIntTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SelectedAnswerRepository selectedAnswerRepository;

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

    @ParameterizedTest
    @MethodSource("addSessionParams")
    void addSession(int occasion, int countSelected, double expectedPercent) {
        long countSessionBefore = sessionRepository.count();
        long countSelectedAnswersBefore = selectedAnswerRepository.count();

        List<SessionDTO> dtos = List.of(TestData.getSessionDTO("madman", questionList, answerList, true, false),
                                        TestData.getSessionDTO("fuzzyman", questionList, answerList, false, false),
                                        TestData.getSessionDTO("okman", questionList, answerList, true, true),
                                        TestData.getSessionDTO("okman1", questionList, answerList, false, true));

        Double percent = sessionService.addSession(dtos.get(occasion));
        //check session exists
        assertEquals(sessionRepository.count(), countSessionBefore + 1);
        //check check selected answers exist
        assertEquals(selectedAnswerRepository.count(), countSelectedAnswersBefore + countSelected);
        //check percentage
        assertEquals(expectedPercent, percent);
    }
    private static Stream<Arguments> addSessionParams() {
        return Stream.of(
                Arguments.of(0, 9, 25),
                Arguments.of(1, 0, 0),
                Arguments.of(2, 5, 37.5),
                Arguments.of(3, 4, 50));
    }
}
