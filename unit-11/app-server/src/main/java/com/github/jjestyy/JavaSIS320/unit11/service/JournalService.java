package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.dto.JournalItemDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDto;
import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;

import java.util.List;

public interface JournalService {
    Journal getJournal(String id);

    List<? extends JournalItemDto> getJournalRows(String id, JournalRowsRequestDto req);
}
