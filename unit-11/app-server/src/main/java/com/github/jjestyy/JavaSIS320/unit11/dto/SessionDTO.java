package com.github.jjestyy.JavaSIS320.unit11.dto;

import lombok.Data;

import java.util.List;

@Data
public class SessionDTO {
    private String name;
    private List<SelectedAnswerDTO> answers;
}
