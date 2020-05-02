package com.github.jjestyy.JavaSIS320.unit11.controller;

import com.github.jjestyy.JavaSIS320.unit11.data.JournalRepository;
import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JournalRestControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JournalRepository journalRepository;

    private static Journal journal = new Journal();

    @BeforeEach
    private void prepareDB() {
        journal.setId("testquestions");
        journal.setName("testQuestions");
        journal.setDefaultPageSize(15L);
        journalRepository.save(journal);
    }

    @AfterEach
    private void clearDB() {
        journalRepository.delete(journal);
    }

    @ParameterizedTest
    @MethodSource("createUrlsAndStatuses")
    void getJournalEntity(String url, HttpStatus status) {
        // no such entity
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + url, String.class);
        assertEquals(response.getStatusCode(), status);
    }

    private static Stream<Arguments> createUrlsAndStatuses() {
        return Stream.of(
                Arguments.of("/api/journal/questions11", HttpStatus.NO_CONTENT),
                Arguments.of("/api/journal/questions/some/absurd", HttpStatus.NOT_FOUND),
                Arguments.of("/api/journal/testquestions", HttpStatus.OK));
    }

}