package com.github.jjestyy.JavaSIS320.unit11.data;

import com.github.jjestyy.JavaSIS320.unit11.entity.Journal;
import org.springframework.data.repository.CrudRepository;

public interface JournalRepository extends CrudRepository<Journal, String> {
}
