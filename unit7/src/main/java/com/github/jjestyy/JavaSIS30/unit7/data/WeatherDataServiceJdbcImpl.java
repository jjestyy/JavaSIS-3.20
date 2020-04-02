package com.github.jjestyy.JavaSIS30.unit7.data;

import com.github.jjestyy.JavaSIS30.unit7.model.WeatherRecord;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                (rs, rowNum) -> new WeatherRecord(rs.getString("city"), rs.getFloat("temp"), rs.getDate("date").toLocalDate()));
    }
    @Override
    public List<WeatherRecord> findWithConditions(String city, String periodStart, String periodEnd) {
        String sql = getSQLString(city, periodStart, periodEnd);
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    return new WeatherRecord(
                            rs.getString("city"),
                            rs.getFloat("temp"),
                            LocalDate.parse(
                                    rs.getString("year") + "-" + rs.getString("month") + "-01",
                                    DateTimeFormatter.ofPattern("yyyy-M-d")));
                });
    }

    private String getSQLString(String city, String periodStart, String periodEnd) {
        String sql = "SELECT " +
                "YEAR(date) as year, MONTH(date) as month, city, AVG(temp) as temp " +
                "FROM WEATHER ";
        List<String> conditions = new ArrayList<>();
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-mm-dd");
        if(!periodStart.equals("")) {
            try {
                formatter.parse(periodStart);
                conditions.add(" date > '" + periodStart + "' ");
            } catch (Exception e) {
                throw new RuntimeException("Bad date at start");
            }
        }
        if(!periodEnd.equals("")) {
            try {
                formatter.parse(periodEnd);
                conditions.add(" date < '" + periodEnd + "' ");
            } catch (Exception e) {
                throw new RuntimeException("Bad date at end");
            }
        }
        if (!city.equals("")) {
            conditions.add(" city = '" + city + "'");
        }
        if(conditions.size()>0) {
            sql+= " WHERE ";
            sql+= String.join(" AND ", conditions);
        }
        sql +=" GROUP BY MONTH(date), YEAR(date), city "+
        " ORDER BY MONTH(date), YEAR(date), city ";
        return sql;
    }

    @Override
    public void prepareDB() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS weather (city TEXT, temp REAL, date DATE)");
    }
}
