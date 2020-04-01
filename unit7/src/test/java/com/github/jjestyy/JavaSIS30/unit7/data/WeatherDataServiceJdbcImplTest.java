package com.github.jjestyy.JavaSIS30.unit7.data;

import com.github.jjestyy.JavaSIS30.unit7.model.WeatherRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})

class WeatherDataServiceJdbcImplTest {

    @BeforeTestExecution
    private void prepareDB() {
        weatherDataService.prepareDB();
    }

    @Autowired
    private WeatherDataService weatherDataService;

    @Test
    void save() {
        WeatherRecord weatherRecord = new WeatherRecord("Красноярск", (float) 5, new Date());
        assertTrue(weatherDataService.save(weatherRecord) > 0);
    }

    @Test
    void findAll() {
        List<WeatherRecord> fromDB = weatherDataService.findAll();
        assertTrue(fromDB.size()>0);
    }
}