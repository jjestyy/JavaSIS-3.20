package com.github.jjestyy.JavaSIS30.unit7.data;

import com.github.jjestyy.JavaSIS30.unit7.model.WeatherRecord;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})

class WeatherDataServiceJdbcImplTest {

    @BeforeTestExecution
    private void prepareDB() {
        weatherDataService.prepareDB();
    }

    @Autowired
    private WeatherDataService weatherDataService;

    @Test
    void save() throws ParseException {
        WeatherRecord weatherRecord = new WeatherRecord("Krasnoyrask", (float) 7, new Date());
        assertTrue(weatherDataService.save(weatherRecord) > 0);

        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        assertTrue(weatherDataService.save(new WeatherRecord("Moskva", (float) 5, formatter.parse("2019-05-01"))) > 0);
        assertTrue(weatherDataService.save(new WeatherRecord("Moskva", (float) 8, formatter.parse("2019-06-01"))) > 0);
        assertTrue(weatherDataService.save(new WeatherRecord("Moskva", (float) -5, formatter.parse("2019-07-01"))) > 0);
        assertTrue(weatherDataService.save(new WeatherRecord("Moskva", (float) 15, formatter.parse("2019-07-02"))) > 0);
        assertTrue(weatherDataService.save(new WeatherRecord("Moskva", (float) 18, formatter.parse("2019-05-05"))) > 0);
        assertTrue(weatherDataService.save(new WeatherRecord("Moskva", (float) -3, formatter.parse("2019-06-20"))) > 0);
        assertTrue(weatherDataService.save(new WeatherRecord("Moskva", (float) 13, formatter.parse("2019-07-24"))) > 0);
        assertTrue(weatherDataService.save(new WeatherRecord("Omsk", (float) 2, formatter.parse("2019-06-01"))) > 0);
    }

    @Test
    void findAll() {
        List<WeatherRecord> fromDB = weatherDataService.findAll();
        assertTrue(fromDB.size()>0);
    }

    @Test
    void findWithConditions() {
        List<WeatherRecord> fromDB = weatherDataService.findWithConditions("", "", "");
        assertTrue(fromDB.size()>0);
        List<WeatherRecord> fromDB1 = weatherDataService.findWithConditions("Moskva", "", "");
        assertTrue(fromDB1.size()>0);
        List<WeatherRecord> fromDB2 = weatherDataService.findWithConditions("Moskva", "2019-01-01", "");
        assertTrue(fromDB2.size()>0);
        List<WeatherRecord> fromDB3 = weatherDataService.findWithConditions("Moskva", "","2019-06-01");
        assertTrue(fromDB3.size()>0);
        List<WeatherRecord> fromDB4 = weatherDataService.findWithConditions("Moskva", "2019-05-01","2019-06-01");
        assertTrue(fromDB4.size()>0);
        assertTrue(fromDB.size() > fromDB1.size());
        assertTrue(fromDB2.size() > fromDB3.size());
        assertTrue(fromDB3.size() > fromDB4.size());
    }
}