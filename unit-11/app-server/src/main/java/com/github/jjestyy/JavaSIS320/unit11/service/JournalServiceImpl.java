package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.JournalRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDTO;
import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class JournalServiceImpl implements JournalService {

    public static final String QUESTIONS_JOURNAL_ID = "questions";

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private QuestionService questionService;

    @Override
    public Journal getJournal(String id) {
        return journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find journal with id %s", id)));
    }

    @Override
    public List<? extends JournalItemDTO> getJournalRows(String id, JournalRowsRequestDTO req) {
        switch (id) {
            case QUESTIONS_JOURNAL_ID:
                return questionService.getQuestions(req);
            default:
                throw new RuntimeException();
        }
    }
}
