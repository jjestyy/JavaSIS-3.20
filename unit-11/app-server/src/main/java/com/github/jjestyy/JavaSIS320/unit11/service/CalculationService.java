package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.dto.AnsweredQuestionDTO;

public interface CalculationService {
    public Double getPoints(AnsweredQuestionDTO question);
}
