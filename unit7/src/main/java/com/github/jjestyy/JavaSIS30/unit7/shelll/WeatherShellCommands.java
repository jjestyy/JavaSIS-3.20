package com.github.jjestyy.JavaSIS30.unit7.shelll;

import com.github.jjestyy.JavaSIS30.unit7.data.WeatherDataService;
import com.github.jjestyy.JavaSIS30.unit7.model.WeatherRecord;
import com.github.jjestyy.JavaSIS30.unit7.web.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
        try {
            WeatherRecord weatherRecord = new WeatherRecord(city, Float.parseFloat(weatherService.getWeather(city)), new Date());
            weatherDataService.save(weatherRecord);
            return weatherRecord.toString();
        } catch (Exception e) {
            return "Some bad input";
        }
    }

    @ShellMethod("Show all")
    public String showAll(){
        return weatherDataService.findAll().stream().map(WeatherRecord::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod("Show average by months for city")
    public String statistics(@ShellOption (defaultValue = "") String city,
                             @ShellOption (defaultValue = "") String periodStart,
                             @ShellOption(defaultValue = "") String periodEnd) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy MMMM", Locale.ENGLISH);
            return weatherDataService.findWithConditions(city, periodStart, periodEnd).stream()
                    .map(weatherRecord -> df.format(weatherRecord.getDate()) + " " + weatherRecord.getCity() + " | " + weatherRecord.getWeather())
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
