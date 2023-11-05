package com.taiarima.contentcalendar;

import com.taiarima.contentcalendar.model.Content;
import com.taiarima.contentcalendar.model.Status;
import com.taiarima.contentcalendar.model.Type;
import com.taiarima.contentcalendar.repository.ContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@Import(ContentRepository.class)
public class ContentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContentRepository contentRepository;

    @BeforeEach
    public void setUp() {
        // Set up some initial objects in the database
        Content content1 = new Content(null, "Title One", "Description One", Status.COMPLETED, Type.ARTICLE, LocalDateTime.now(), null, "http://example.com/1");
        Content content2 = new Content(null, "Title Two", "Description Two", Status.IDEA, Type.VIDEO, LocalDateTime.now(), null, "http://example.com/2");
        entityManager.persist(content1);
        entityManager.persist(content2);
        entityManager.flush();
    }

    @Test
    public void whenFindByTitleContainsIgnoreCase_thenReturnContent() {
        // when
        List<Content> found = contentRepository.findAllByTitleContainsIgnoreCase("title");

        // then
        assertThat(found).hasSize(2).extracting(Content::title).containsExactlyInAnyOrder("Title One", "Title Two");
    }

    @Test
    public void whenListByStatus_thenReturnCOMPLETEDContent() {
        // when
        List<Content> found = contentRepository.listByStatus(Status.COMPLETED);

        // then
        assertThat(found).hasSize(1).extracting(Content::status).containsExactly(Status.COMPLETED);
    }

    @Test
    public void whenListByStatus_thenReturnIDEAContent() {
        // when
        List<Content> found = contentRepository.listByStatus(Status.IDEA);

        // then
        assertThat(found).hasSize(1).extracting(Content::status).containsExactly(Status.IDEA);
    }
}