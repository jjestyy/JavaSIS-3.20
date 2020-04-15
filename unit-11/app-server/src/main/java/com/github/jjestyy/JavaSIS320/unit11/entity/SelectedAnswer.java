package com.github.jjestyy.JavaSIS320.unit11.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SelectedAnswer extends EntityWithLongId {
    @JoinColumn(name = "answer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Answer answer;

    @JoinColumn(name = "session_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Session session;
}
