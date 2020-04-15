package com.github.jjestyy.JavaSIS320.unit11.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Session extends EntityWithLongId {

    @Column
    private String fio;

    @Column
    private Double percent;

    @Column
    private LocalDate insertDate;

}
