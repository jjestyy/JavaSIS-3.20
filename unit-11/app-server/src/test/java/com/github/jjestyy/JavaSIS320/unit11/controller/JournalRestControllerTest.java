package com.github.jjestyy.JavaSIS320.unit11.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jjestyy.JavaSIS320.unit11.TestData;
import com.github.jjestyy.JavaSIS320.unit11.dto.*;
import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import com.github.jjestyy.JavaSIS320.unit11.service.JournalService;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(JournalRestController.class)
class JournalRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JournalService journalService;

    ObjectMapper mapper = new ObjectMapper();

    @ParameterizedTest
    @MethodSource("dataForGetJournalEntity")
    void getJournalEntity(String url, HttpStatus status) throws Exception {

        Journal journal = new Journal();
        journal.setId("testquestions");
        journal.setName("testquestions");
        journal.setDefaultPageSize(15L);

        when(journalService.getJournal(any(String.class))).thenReturn(null);
        when(journalService.getJournal("testquestions")).thenReturn(journal);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(status.value(), result.getResponse().getStatus());
        if(status == HttpStatus.OK) {
            assertEquals(result.getResponse().getContentAsString(),
                    mapper.writeValueAsString(new JournalEntityDTO(journal)));
        }
        if(status != HttpStatus.NOT_FOUND) {
            verify(journalService).getJournal(any(String.class));
        }
}

    private static Stream<Arguments> dataForGetJournalEntity() {
        return Stream.of(
                Arguments.of("/api/journal/questions11", HttpStatus.NO_CONTENT),
                Arguments.of("/api/journal/questions/some/absurd", HttpStatus.NOT_FOUND),
                Arguments.of("/api/journal/testquestions", HttpStatus.OK));
    }

    @Test
    void getRows() throws Exception {
        JournalRowsRequestDTO journalRowsRequestDTO = TestData.getJournalRowsRequestDTO(true, true, true);
        JournalRowsResultDTO journalRowsResultDTO = TestData.getJournalRowsResultDTO();
        doReturn(null).when(journalService).getJournalRows(any(String.class), any(JournalRowsRequestDTO.class));
        doReturn(TestData.getQuestionsItemDtos()).when(journalService).getJournalRows("questions", journalRowsRequestDTO);
        JournalRowsRequestDTO emptyJournalRowsRequestDTO = TestData.getJournalRowsRequestDTO(false, false, false);
        //empty request
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/journal/questions/rows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(emptyJournalRowsRequestDTO)))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
        //normal request
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/journal/questions/rows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(journalRowsRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(journalRowsResultDTO)));
    }


}