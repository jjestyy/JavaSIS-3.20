package com.github.jjestyy.JavaSIS320.unit11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JournalRowsResultDto {
    private int total;
    private List<? extends JournalItemDto> items;
}
