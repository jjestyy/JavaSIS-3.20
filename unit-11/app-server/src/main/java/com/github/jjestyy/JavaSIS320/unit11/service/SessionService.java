package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.dto.*;

import java.util.List;

public interface SessionService {
    List<QuestionsItemDto> getRandomQuestionsList(int size);

    double addSession(SessionDto dto);

    List<SessionsItemDto> getSessions(JournalRowsRequestDto req);
}
