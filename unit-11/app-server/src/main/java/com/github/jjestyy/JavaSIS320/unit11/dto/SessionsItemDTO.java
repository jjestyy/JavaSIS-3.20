package com.github.jjestyy.JavaSIS320.unit11.dto;

import com.github.jjestyy.JavaSIS320.unit11.entity.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class SessionsItemDTO extends JournalItemDTO {
    private String name;
    private LocalDate insertDate;
    private Double result;

    public SessionsItemDTO(Session session) {
        this.name = session.getFio();
        this.insertDate = session.getInsertDate();
        this.result = session.getPoints();
    }
}
