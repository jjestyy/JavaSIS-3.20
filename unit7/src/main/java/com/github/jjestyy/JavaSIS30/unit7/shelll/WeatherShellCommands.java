package com.github.jjestyy.JavaSIS30.unit7.shelll;

import com.github.jjestyy.JavaSIS30.unit7.data.WeatherDataService;
import com.github.jjestyy.JavaSIS30.unit7.model.WeatherRecord;
import com.github.jjestyy.JavaSIS30.unit7.web.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Date;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class WeatherShellCommands {

    @Autowired
    private final WeatherService weatherService;

    @Autowired
    private final WeatherDataService weatherDataService;

    @ShellMethod("Get weather")
    public String weather(@ShellOption(defaultValue = "Krasnoyarsk") String city) {
        WeatherRecord weatherRecord = new WeatherRecord(city, Float.parseFloat(weatherService.getWeather(city)), new Date());
        weatherDataService.save(weatherRecord);
        return weatherRecord.toString();
    }

    @ShellMethod("Show all")
    public String showAll(){
        return weatherDataService.findAll().stream().map(WeatherRecord::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
