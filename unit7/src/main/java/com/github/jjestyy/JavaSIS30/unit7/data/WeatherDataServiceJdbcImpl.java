package com.github.jjestyy.JavaSIS30.unit7.data;

import com.github.jjestyy.JavaSIS30.unit7.model.WeatherRecord;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WeatherDataServiceJdbcImpl implements WeatherDataService {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public int save(WeatherRecord weatherRecord) {
        return jdbcTemplate.update("INSERT into weather (city, temp, date) VALUES (?,?,?)", weatherRecord.getCity(), weatherRecord.getWeather(), weatherRecord.getDate());
    }

    @Override
    public List<WeatherRecord> findAll() {
        return jdbcTemplate.query("SELECT city, temp, date from weather",
                (rs, rowNum) -> new WeatherRecord(rs.getString("city"),rs.getFloat("temp"), rs.getDate("date")));
    }

    @Override
    public void prepareDB() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS weather (city TEXT, temp REAL, date DATE)");
    }
}
