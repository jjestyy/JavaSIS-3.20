package com.github.jjestyy.JavaSIS320.unit11.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SessionDTO {
    private String name;
    private List<AnsweredQuestionDTO> questionsList;
}