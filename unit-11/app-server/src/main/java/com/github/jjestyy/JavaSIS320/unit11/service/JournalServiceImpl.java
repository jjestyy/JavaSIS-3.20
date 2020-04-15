package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.JournalRepository;
import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalServiceImpl implements JournalService {
    @Autowired
    private JournalRepository journalRepository;

    @Override
    public Journal getJournal(String id) {
        return journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find journal with id %s", id)));
    }
}
