package com.github.jjestyy.JavaSIS320.unit11;

import com.github.jjestyy.JavaSIS320.unit11.controller.JournalRestController;
import com.github.jjestyy.JavaSIS320.unit11.controller.QuestionRestController;
import com.github.jjestyy.JavaSIS320.unit11.controller.SessionRestController;
import com.github.jjestyy.JavaSIS320.unit11.data.*;
import com.github.jjestyy.JavaSIS320.unit11.service.JournalService;
import com.github.jjestyy.JavaSIS320.unit11.service.QuestionService;
import com.github.jjestyy.JavaSIS320.unit11.service.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(Unit11TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class Unit11ApplicationTests {

	@Autowired
	private JournalService journalService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private JournalRestController journalRestController;
	@Autowired
	private QuestionRestController questionRestController;
	@Autowired
	private SessionRestController sessionRestController;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private JournalRepository journalRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private SelectedAnswerRepository selectedAnswerRepository;
	@Autowired
	private SessionRepository sessionRepository;
	@Test
	public void contextLoads() throws Exception{
		assertNotNull(journalService);
		assertNotNull(questionService);
		assertNotNull(sessionService);
		assertNotNull(journalRestController);
		assertNotNull(questionRestController);
		assertNotNull(sessionRestController);
		assertNotNull(answerRepository);
		assertNotNull(journalRepository);
		assertNotNull(questionRepository);
		assertNotNull(selectedAnswerRepository);
		assertNotNull(sessionRepository);
	}

}
