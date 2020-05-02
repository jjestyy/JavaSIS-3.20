package com.github.jjestyy.JavaSIS320.unit11.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class BaseEntity <T> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private T id;

}
