package com.taiarima.contentcalendar.repository;

import com.taiarima.contentcalendar.model.Content;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends ListCrudRepository<Content, Integer> {

}
