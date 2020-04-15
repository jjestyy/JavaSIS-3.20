package com.github.jjestyy.JavaSIS320.unit11.dto;

import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;

public class JournalEntityDTO {

    public String id;
    public String name;
    public Long defaultPageSize;

    public JournalEntityDTO(Journal journal) {
        id = journal.getId();
        name = journal.getName();
        defaultPageSize = journal.getDefaultPageSize();
    }
}
