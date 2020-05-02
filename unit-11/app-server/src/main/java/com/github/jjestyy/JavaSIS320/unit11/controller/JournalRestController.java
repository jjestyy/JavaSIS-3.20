package com.github.jjestyy.JavaSIS320.unit11.controller;

import com.github.jjestyy.JavaSIS320.unit11.dto.JournalEntityDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalItemDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsResultDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDto;
import com.github.jjestyy.JavaSIS320.unit11.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/journal")
public class JournalRestController {

    @Autowired
    private JournalService journalService;

    @GetMapping("{id}")
    public JournalEntityDto getJournalEntity(@PathVariable String id){
        return new JournalEntityDto(journalService.getJournal(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(RuntimeException.class)
    public JournalEntityDto handleNotFound(RuntimeException e) {
        return null;
    }

    @PutMapping("{id}/rows")
    public JournalRowsResultDto getRows(@PathVariable String id, @RequestBody JournalRowsRequestDto req) {
        List<? extends JournalItemDto> collect = journalService.getJournalRows(id, req);
        return new JournalRowsResultDto(collect.size(), collect);
    }
}
