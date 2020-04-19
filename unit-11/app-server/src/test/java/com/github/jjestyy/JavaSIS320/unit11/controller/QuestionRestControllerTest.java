package com.github.jjestyy.JavaSIS320.unit11.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.dto.AnswerItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.service.JournalService;
import com.github.jjestyy.JavaSIS320.unit11.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuestionRestController.class)
class QuestionRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    QuestionService questionService;

    ObjectMapper mapper = new ObjectMapper();
    @Test
    void test() throws Exception {
        for(QuestionsItemDTO dto : TestData.getQuestionsItemDtos()) {
            when(questionService.createQuestion(dto)).thenReturn(dto);
            when(questionService.editQuestion(dto)).thenReturn(dto);

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/question/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(dto)));
        }
    }

}