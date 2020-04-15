package com.github.jjestyy.JavaSIS320.unit11.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class EntityWithLongId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
