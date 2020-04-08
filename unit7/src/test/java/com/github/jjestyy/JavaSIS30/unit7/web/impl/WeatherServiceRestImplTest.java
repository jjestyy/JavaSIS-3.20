package com.github.jjestyy.JavaSIS30.unit7.web.impl;

import com.github.jjestyy.JavaSIS30.unit7.web.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest (properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
class WeatherServiceRestImplTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    void getWeather() {
        String responseString = weatherService.getWeather("Krasnoyarsk");
        Assertions.assertNotNull(responseString);
        System.out.println(responseString);
    }
}