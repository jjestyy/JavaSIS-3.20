package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.controller.JournalType;
import com.github.jjestyy.JavaSIS320.unit11.data.JournalRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalItemDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDto;
import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SessionService sessionService;

    @Override
    public Journal getJournal(String id) {
        return journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find journal with id %s", id)));
    }

    @PostConstruct
    private void initData() {
        if(!journalRepository.existsById(JournalType.QUESTIONS_JOURNAL_ID)) {
            Journal journal = new Journal();
            journal.setId(JournalType.QUESTIONS_JOURNAL_ID);
            journal.setName("Вопросы");
            journal.setDefaultPageSize(15L);
            journalRepository.save(journal);
        }
        if(!journalRepository.existsById(JournalType.SESSIONS_JOURNAL_ID)) {
            Journal journal = new Journal();
            journal.setId(JournalType.SESSIONS_JOURNAL_ID);
            journal.setName("Сессии");
            journal.setDefaultPageSize(15L);
            journalRepository.save(journal);
        }
    }

    @Override
    public List<? extends JournalItemDto> getJournalRows(String id, JournalRowsRequestDto req) {
        switch (id) {
            case JournalType.QUESTIONS_JOURNAL_ID:
                return questionService.getQuestions(req);
            case JournalType.SESSIONS_JOURNAL_ID:
                return sessionService.getSessions(req);
            default:
                throw new RuntimeException();
        }
    }
}
