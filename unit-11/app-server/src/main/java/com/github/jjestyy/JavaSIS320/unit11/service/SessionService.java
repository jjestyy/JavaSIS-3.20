package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.SessionDTO;

import java.util.List;

public interface SessionService {
    List<QuestionsItemDTO> getRandomQuestionsList(int size);

    void addSession(SessionDTO dto);
}
