package com.github.jjestyy.JavaSIS30.unit7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
@AllArgsConstructor
public class WeatherRecord {

    private String city;

    private float weather;

    private Date date;
}
