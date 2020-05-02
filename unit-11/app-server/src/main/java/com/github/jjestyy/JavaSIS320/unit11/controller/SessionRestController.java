package com.github.jjestyy.JavaSIS320.unit11.controller;

import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.SessionDto;
import com.github.jjestyy.JavaSIS320.unit11.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/session")
public class SessionRestController {

    private final static int DEFAULT_SESSION_SIZE = 10;

    @Autowired
    private SessionService sessionService;

    @GetMapping("questions-new")
    public List<QuestionsItemDto> getQuestionsNew() {
        return sessionService.getRandomQuestionsList(DEFAULT_SESSION_SIZE);
    }

    @PostMapping()
    public String postSession(@RequestBody SessionDto dto) {
        return String.valueOf(sessionService.addSession(dto));
    }

}