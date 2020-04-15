package com.github.jjestyy.JavaSIS320.unit11.controller;

import com.github.jjestyy.JavaSIS320.unit11.dto.JournalEntityDTO;
import com.github.jjestyy.JavaSIS320.unit11.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
