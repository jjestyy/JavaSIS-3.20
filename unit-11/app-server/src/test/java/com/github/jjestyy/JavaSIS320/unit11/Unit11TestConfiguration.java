package com.github.jjestyy.JavaSIS320.unit11;

import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.service.*;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class Unit11TestConfiguration {
    @Bean
    public SessionService sessionService() {
        return new SessionServiceImpl();
    }
    @Bean
    public QuestionService questionService() {
        return new QuestionServiceImpl();
    }
    @Bean
    public JournalService journalService() {
        return new JournalServiceImpl();
    }
}
