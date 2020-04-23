package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.Unit11TestConfiguration;
import com.github.jjestyy.JavaSIS320.unit11.data.JournalRepository;
import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.Mockito.when;

@Import(Unit11TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class JournalServiceImplTest {

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