package com.github.jjestyy.JavaSIS30.unit7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Unit7Application {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Unit7Application.class, args);
	}

	@Bean
    public RestTemplate getRestTemplate () {
	    return new RestTemplate();
    }

    @PostConstruct
	private void createTable() {
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS weather (city TEXT, temp REAL, date DATE)");
	}
}
