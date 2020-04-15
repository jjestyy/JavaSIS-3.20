package com.github.jjestyy.JavaSIS320.unit11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JournalRowsResultDTO {
    private int total;
    private List<? extends JournalItemDTO> items;

}
