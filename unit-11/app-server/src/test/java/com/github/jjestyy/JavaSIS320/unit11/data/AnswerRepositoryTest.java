package com.github.jjestyy.JavaSIS320.unit11.data;

import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnswerRepository answerRepository;

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
    void findByQuestion() {
        List<Answer> expectedResult = answerList.subList(0, 2);
        List<Answer> actualResult = answerRepository.findByQuestion(questionList.get(0));
        for (int i = 0; i< expectedResult.size(); i++ ) {
            assertEquals(expectedResult.get(i).getName(), actualResult.get(i).getName());
        }
    }
}