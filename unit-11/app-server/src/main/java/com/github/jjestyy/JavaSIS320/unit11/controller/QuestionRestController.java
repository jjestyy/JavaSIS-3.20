package com.github.jjestyy.JavaSIS320.unit11.controller;

import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDto;
import com.github.jjestyy.JavaSIS320.unit11.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/question")
public class QuestionRestController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("create")
    public QuestionsItemDto create(@RequestBody QuestionsItemDto dto) {
        return questionService.createQuestion(dto);
    }

    @PutMapping("edit")
    public QuestionsItemDto edit(@RequestBody QuestionsItemDto dto) {
        return questionService.editQuestion(dto);
    }
}