package com.github.jjestyy.JavaSIS30.unit7.web.impl;

import com.github.jjestyy.JavaSIS30.unit7.dto.MainDto;
import com.github.jjestyy.JavaSIS30.unit7.dto.TempDto;
import com.github.jjestyy.JavaSIS30.unit7.web.WeatherService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor(staticName = "restTemplate")
public class WeatherServiceRestImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private static String URL = "https://community-open-weather-map.p.rapidapi.com/weather?units=metric&mode=json&q=Красноярск";
    private static Map<String,String> headers = Map.of("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com",
            "x-rapidapi-key","cdd96ad95amsheebcca53e65aac1p136010jsn18e30e4e6350");

    @Override
    public String getWeather() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> request = new HttpEntity<>(URL, httpHeaders);
        ResponseEntity<TempDto> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, request, TempDto.class);
        return Objects.requireNonNull(responseEntity.getBody()).getMain().getTemp();
    }
}
