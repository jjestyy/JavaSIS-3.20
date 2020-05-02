package com.github.jjestyy.JavaSIS320.unit11.data;

import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import com.github.jjestyy.JavaSIS320.unit11.entity.Session;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long>, PagingAndSortingRepository<Session, Long> {
    List<Session> findByFioContainingIgnoreCase(String search, Pageable pageable);
}
