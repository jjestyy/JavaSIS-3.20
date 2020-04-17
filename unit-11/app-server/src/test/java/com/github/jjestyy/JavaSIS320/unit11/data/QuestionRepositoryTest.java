package com.github.jjestyy.JavaSIS320.unit11.data;

import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDTO;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import org.assertj.core.api.HamcrestCondition;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.hamcrest.MockitoHamcrest;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class QuestionRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        for(Question question: TestData.getTestQuestionsList()) {
            entityManager.persistAndGetId(question);
        }
    }

    @ParameterizedTest
    @MethodSource("createRequestsAndResults")
    void findByNameContainingIgnoreCase(JournalRowsRequestDTO req, List<Question> expectedResult) {
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getPageSize(), Sort.by(Sort.Direction.ASC, "id"));
        List<Question> actualResult = questionRepository.findByNameContainingIgnoreCase(req.getSearch(), pageRequest);
        for (int i = 0; i< expectedResult.size(); i++ ) {
            System.out.println(expectedResult.get(i).getName());
            assertEquals(expectedResult.get(i).getName(), actualResult.get(i).getName());
        }
    }

    private static Stream<Arguments> createRequestsAndResults() {
        return Stream.of(
                Arguments.of(
                        TestData.getJournalRowsRequestDTO(false, true, false),
                        TestData.getTestQuestionsList().subList(0,2)),
                Arguments.of(
                        TestData.getJournalRowsRequestDTO(true, true, false),
                        List.of(TestData.getTestQuestionsList().get(2))),
                Arguments.of(TestData.getJournalRowsRequestDTO(true, false, false),
                        TestData.getTestQuestionsList().subList(2,3)
                ));
    }
}