package com.github.jjestyy.JavaSIS320.unit11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.service.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionRestController.class)
class SessionRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SessionService sessionService;

    ObjectMapper mapper = new ObjectMapper();
    @Test
    void test() throws Exception {

        when(sessionService.getRandomQuestionsList(10)).thenReturn(TestData.getQuestionsItemDtos());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/session/questions-new")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(TestData.getQuestionsItemDtos())));

    }

}