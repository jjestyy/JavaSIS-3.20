package com.github.jjestyy.JavaSIS30.unit7.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeatherRecord {

    private String city;

    private float weather;

    private LocalDate date;
}
