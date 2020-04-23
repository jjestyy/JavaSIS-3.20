package com.github.jjestyy.JavaSIS320.unit11.controller;

import com.github.jjestyy.JavaSIS320.unit11.dto.JournalEntityDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsResultDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDTO;
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
    JournalService journalService;

    @GetMapping("{id}")
    public JournalEntityDTO getJournalEntity(@PathVariable String id){
        return new JournalEntityDTO(journalService.getJournal(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(RuntimeException.class)
    public JournalEntityDTO handleNotFound(RuntimeException e) {
        return null;
    }

    @PutMapping("{id}/rows")
    public JournalRowsResultDTO getRows(@PathVariable String id, @RequestBody JournalRowsRequestDTO req) {
        List<? extends JournalItemDTO> collect = journalService.getJournalRows(id, req);
        return new JournalRowsResultDTO(collect.size(), collect);
    }
}
