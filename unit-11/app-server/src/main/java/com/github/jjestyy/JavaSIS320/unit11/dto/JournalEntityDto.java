package com.github.jjestyy.JavaSIS320.unit11.dto;

import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import lombok.Data;

@Data
public class JournalEntityDto {

    private String id;
    private String name;
    private Long defaultPageSize;

    public JournalEntityDto(Journal journal) {
        id = journal.getId();
        name = journal.getName();
        defaultPageSize = journal.getDefaultPageSize();
    }
}
