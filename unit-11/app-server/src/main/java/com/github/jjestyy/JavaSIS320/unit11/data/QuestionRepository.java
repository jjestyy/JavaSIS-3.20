package com.github.jjestyy.JavaSIS320.unit11.data;

import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long>, PagingAndSortingRepository <Question, Long> {
    List<Question> findByNameContainingIgnoreCase(String search, Pageable pageable);
}
