package com.github.jjestyy.JavaSIS320.unit11.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Answer extends EntityWithLongId {
    @Column
    String name;

    @Column
    Boolean isCorrect;

    @JoinColumn(name = "question_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Question question;
}
