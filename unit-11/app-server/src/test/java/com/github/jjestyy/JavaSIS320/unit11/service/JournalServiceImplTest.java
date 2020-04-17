package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.data.JournalRepository;
import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class JournalServiceImplTest {

    @TestConfiguration
    static class JournalServiceImplTestContextConfiguration {

        @Bean
        public JournalService journalService() {
            return new JournalServiceImpl();
        }
    }

    @Autowired
    private JournalService journalService;

    @MockBean
    private JournalRepository journalRepository;

    @MockBean
    private QuestionService questionService;


    @Test
    void getJournal() {
        Journal journal = new Journal();
        journal.setId("testquestions");
        journal.setName("testquestions");
        journal.setDefaultPageSize(15L);
        when(journalRepository.findById("testquestions")).thenReturn(java.util.Optional.of(journal));
        Assertions.assertEquals(journal, journalService.getJournal("testquestions"));
    }

    @Test
    void getJournalRows() {
        when(questionService.getQuestions(TestData.getJournalRowsRequestDTO(true, true, true)))
                .thenReturn(TestData.getQuestionsItemDtos());

        Assertions.assertEquals(TestData.getQuestionsItemDtos(),
                journalService.getJournalRows("questions", TestData.getJournalRowsRequestDTO(true, true, true)));
    }
}