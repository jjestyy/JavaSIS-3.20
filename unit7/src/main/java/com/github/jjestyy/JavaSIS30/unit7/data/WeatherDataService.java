package com.github.jjestyy.JavaSIS30.unit7.data;

import com.github.jjestyy.JavaSIS30.unit7.model.WeatherRecord;

import java.util.List;
import java.util.Map;

public interface WeatherDataService {
    int save(WeatherRecord weatherRecord);
    List<WeatherRecord> findAll();
    void prepareDB();
}
