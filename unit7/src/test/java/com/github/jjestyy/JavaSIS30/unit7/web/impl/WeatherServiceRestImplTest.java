package com.github.jjestyy.JavaSIS30.unit7.web.impl;

import com.github.jjestyy.JavaSIS30.unit7.web.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WeatherServiceRestImplTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    void getWeather() {
        String responceString = weatherService.getWeather();
        Assertions.assertNotNull(responceString);
        System.out.println(responceString);
    }
}