package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.dto.JournalItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDTO;
import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;

import java.util.List;

public interface JournalService {
    Journal getJournal(String id);

    List<? extends JournalItemDTO> getJournalRows(String id, JournalRowsRequestDTO req);
}
