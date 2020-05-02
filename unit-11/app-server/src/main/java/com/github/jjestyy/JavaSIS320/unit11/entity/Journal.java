package com.github.jjestyy.JavaSIS320.unit11.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Journal extends BaseEntity <String>{
    @Column(name = "name")
    private String name;

    @Column(name = "default_page_size")
    private Long defaultPageSize;
}
