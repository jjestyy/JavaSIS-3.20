package com.github.jjestyy.JavaSIS30.unit7.shelll;

import com.github.jjestyy.JavaSIS30.unit7.web.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class WeatherShellCommands {
    private final WeatherService weatherService;
    @ShellMethod("Get weather")
    public String weather() {
        return weatherService.getWeather();
    }
}
