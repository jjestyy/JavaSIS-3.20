package com.github.jjestyy.JavaSIS320.unit11.dto;

import lombok.Data;

import java.util.List;

@Data
public class JournalRowsRequestDto {
    private String search;
    private int page;
    private int pageSize;
    private List<FilterDto> filters;
}
