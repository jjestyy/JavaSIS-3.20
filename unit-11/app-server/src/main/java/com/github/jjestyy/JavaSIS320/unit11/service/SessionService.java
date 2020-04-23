package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.dto.*;

import java.util.List;

public interface SessionService {
    List<QuestionsItemDTO> getRandomQuestionsList(int size);

    double addSession(SessionDTO dto);

    List<SessionsItemDTO> getSessions(JournalRowsRequestDTO req);
}
