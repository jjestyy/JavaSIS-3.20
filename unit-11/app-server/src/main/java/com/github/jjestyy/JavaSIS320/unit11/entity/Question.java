package com.github.jjestyy.JavaSIS320.unit11.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Question extends BaseEntity <Long> {
    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "Question{" +
                "name='" + name + '\'' +
                "id='" + this.getId() + '\'' +
                '}';
    }
}
