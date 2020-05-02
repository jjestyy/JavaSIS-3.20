package com.github.jjestyy.JavaSIS320.unit11.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Answer extends BaseEntity <Long> {
    @Column
    String name;

    @Column
    Boolean isCorrect;

    @JoinColumn(name = "question_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Question question;

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + this.getId() + ", " + "name='" + name + '\'' +
                ", isCorrect=" + isCorrect +
                ", question=" + question +
                '}';
    }
}
