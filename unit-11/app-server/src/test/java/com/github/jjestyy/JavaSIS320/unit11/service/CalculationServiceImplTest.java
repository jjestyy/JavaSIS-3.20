package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.Unit11TestConfiguration;
import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.AnsweredQuestionDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.SessionQuestionAnswerDto;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Import(Unit11TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CalculationServiceImplTest {

    @Autowired
    CalculationService calculationService;
    @MockBean
    private AnswerRepository answerRepository;
    @Test
    void getPoints() {
        //no answers
        AnsweredQuestionDto answeredQuestionDTO = new AnsweredQuestionDto();
        answeredQuestionDTO.setId("1");

        assertEquals(1, calculationService.getPoints(answeredQuestionDTO));

        //1 correct answer
        Answer answer1 = new Answer();
        answer1.setId(1L);
        answer1.setName("ответ ");
        answer1.setIsCorrect(true);
        Mockito.when(answerRepository.findById(1L)).thenReturn(Optional.of(answer1));

        answeredQuestionDTO.setAnswersList(List.of(getAnswerDto(answer1, true)));

        assertEquals(1, calculationService.getPoints(answeredQuestionDTO));

        answeredQuestionDTO.setAnswersList(List.of(getAnswerDto(answer1, false)));

        assertEquals(0, calculationService.getPoints(answeredQuestionDTO));

        //1 incorrect answer

        Answer answer2 = new Answer();
        answer2.setId(2L);
        answer2.setName("ответ ");
        answer2.setIsCorrect(false);
        Mockito.when(answerRepository.findById(2L)).thenReturn(Optional.of(answer2));

        answeredQuestionDTO.setAnswersList(List.of(getAnswerDto(answer2, false)));

        assertEquals(1, calculationService.getPoints(answeredQuestionDTO));

        answeredQuestionDTO.setAnswersList(List.of(getAnswerDto(answer2, true)));

        assertEquals(0, calculationService.getPoints(answeredQuestionDTO));

        //all answers correct
        Answer answer3 = new Answer();
        answer3.setId(3L);
        answer3.setName("ответ ");
        answer3.setIsCorrect(true);
        Mockito.when(answerRepository.findById(3L)).thenReturn(Optional.of(answer3));

        answeredQuestionDTO.setAnswersList(List.of(getAnswerDto(answer1, true), getAnswerDto(answer3, true) ));
        assertEquals(1, calculationService.getPoints(answeredQuestionDTO));

        //all answers incorrect
        Answer answer4 = new Answer();
        answer4.setId(4L);
        answer4.setName("ответ ");
        answer4.setIsCorrect(false);
        Mockito.when(answerRepository.findById(4L)).thenReturn(Optional.of(answer4));

        answeredQuestionDTO.setAnswersList(List.of(getAnswerDto(answer2, true), getAnswerDto(answer4, true) ));
        assertEquals(0, calculationService.getPoints(answeredQuestionDTO));

        //2 correct 2 incorrect
        //all checked
        answeredQuestionDTO.setAnswersList(
                List.of(getAnswerDto(answer1, true),
                        getAnswerDto(answer2, true),
                        getAnswerDto(answer3, true),
                        getAnswerDto(answer4, true)
                ));
        assertEquals(0, calculationService.getPoints(answeredQuestionDTO));
        //all unchecked
        answeredQuestionDTO.setAnswersList(
                List.of(getAnswerDto(answer1, false),
                        getAnswerDto(answer2, false),
                        getAnswerDto(answer3, false),
                        getAnswerDto(answer4, false)
                ));
        assertEquals(0, calculationService.getPoints(answeredQuestionDTO));
        //1 right checked
        answeredQuestionDTO.setAnswersList(
                List.of(getAnswerDto(answer1, true),
                        getAnswerDto(answer2, false),
                        getAnswerDto(answer3, false),
                        getAnswerDto(answer4, false)
                ));
        assertEquals(0.5, calculationService.getPoints(answeredQuestionDTO));
        //1 right checked 1 wrong checked
        answeredQuestionDTO.setAnswersList(
                List.of(getAnswerDto(answer1, true),
                        getAnswerDto(answer2, true),
                        getAnswerDto(answer3, false),
                        getAnswerDto(answer4, false)
                ));
        assertEquals(0, calculationService.getPoints(answeredQuestionDTO));
        //all right checked 1 wrong checked
        answeredQuestionDTO.setAnswersList(
                List.of(getAnswerDto(answer1, true),
                        getAnswerDto(answer2, true),
                        getAnswerDto(answer3, true),
                        getAnswerDto(answer4, false)
                ));
        assertEquals(0.5, calculationService.getPoints(answeredQuestionDTO));
        //just all right checked
        answeredQuestionDTO.setAnswersList(
                List.of(getAnswerDto(answer1, true),
                        getAnswerDto(answer2, false),
                        getAnswerDto(answer3, true),
                        getAnswerDto(answer4, false)
                ));
        assertEquals(1, calculationService.getPoints(answeredQuestionDTO));

    }

    private SessionQuestionAnswerDto getAnswerDto(Answer answer, Boolean isSelected) {
        SessionQuestionAnswerDto answerDTO = new SessionQuestionAnswerDto();
        answerDTO.setId(answer.getId().toString());
        answerDTO.setIsSelected(isSelected);
        return answerDTO;
    }
}