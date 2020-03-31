package com.github.jjestyy.JavaSIS30.unit7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Unit7Application {

	public static void main(String[] args) {
		SpringApplication.run(Unit7Application.class, args);
	}

	@Bean
    public RestTemplate getRestTemplate () {
	    return new RestTemplate();
    }
}
