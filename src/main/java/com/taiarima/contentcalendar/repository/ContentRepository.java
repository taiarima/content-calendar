package com.taiarima.contentcalendar.repository;

import com.taiarima.contentcalendar.model.Content;
import com.taiarima.contentcalendar.model.Status;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends ListCrudRepository<Content, Integer> {

//    @Query(value = "SELECT * FROM Content WHERE LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    @Query("""
            SELECT * FROM Content
            WHERE LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Content> findAllByTitleContainsIgnoreCase(@Param("keyword") String keyword);

    @Query("""
            SELECT * FROM Content
            WHERE status = :status
            """)
    List<Content> listByStatus(@Param("status") Status status);

}
