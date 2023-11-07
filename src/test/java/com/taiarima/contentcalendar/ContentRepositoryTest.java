package com.taiarima.contentcalendar;

import com.taiarima.contentcalendar.model.Content;
import com.taiarima.contentcalendar.model.Status;
import com.taiarima.contentcalendar.model.Type;
import com.taiarima.contentcalendar.repository.ContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class ContentRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ContentRepository contentRepository;

    private Integer contentId1;
    private Integer contentId2;

    private Integer initialDataSetupCount;


    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        jdbcTemplate.update("DELETE FROM content");

        // Set up some initial objects in the database
        KeyHolder keyHolder = new GeneratedKeyHolder();


        KeyHolder keyHolder1 = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO content (title, descr, status, content_type, date_created, url) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "Title One");
            ps.setString(2, "Description One");
            ps.setString(3, Status.COMPLETED.name());
            ps.setString(4, Type.ARTICLE.name());
            ps.setObject(5, LocalDateTime.now());
            ps.setString(6, "http://example.com/1");
            return ps;
        }, keyHolder1);
        contentId1 = keyHolder1.getKey().intValue();

        KeyHolder keyHolder2 = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO content (title, descr, status, content_type, date_created, url) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "Title Two");
            ps.setString(2, "Description Two");
            ps.setString(3, Status.IDEA.name());
            ps.setString(4, Type.VIDEO.name());
            ps.setObject(5, LocalDateTime.now());
            ps.setString(6, "http://example.com/2");
            return ps;
        }, keyHolder2);
        contentId2 = keyHolder2.getKey().intValue();
        initialDataSetupCount = 2;
    }

    @Test
    public void whenCount_thenReturnCorrectNumberOfEntries() {
        long count = contentRepository.count();
        assertThat(count).isEqualTo(initialDataSetupCount);
    }

    @Test
    public void whenFindByTitleContainsIgnoreCase_thenReturnContent() {
        // when
        List<Content> found = contentRepository.findAllByTitleContainsIgnoreCase("title");

        // then
        assertThat(found).hasSize(initialDataSetupCount).extracting(Content::title).containsExactlyInAnyOrder("Title One", "Title Two");
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

    @Test
    public void givenData_whenFindAll_thenReturnData() {
        List<Content> found = contentRepository.findAll();
        assertThat(found).hasSize(initialDataSetupCount);
    }

    @Test
    public void givenNoData_whenFindAll_thenReturnEmptyList() {
        jdbcTemplate.update("DELETE FROM content");
        List<Content> found = contentRepository.findAll();
        assertThat(found).isEmpty();
    }

    @Test
    public void givenExistingId_whenFindById_thenReturnCorrespondingContent() {
        Optional<Content> found = contentRepository.findById(contentId1);
        assertThat(found).isPresent();
        assertThat(found.get().title()).isEqualTo("Title One");
    }

    @Test
    public void givenNonExistentId_whenFindById_thenReturnNothing() {
        Optional<Content> found = contentRepository.findById(99);
        assertThat(found).isNotPresent();
    }

    @Test
    public void givenExistingId_whenExistsById_thenReturnTrue() {
        Content content = contentRepository.findById(contentId1).orElse(null);
        assertThat(content).isNotNull();
        assertThat(content.title()).isEqualTo("Title One");
    }

    @Test
    public void givenNonExistentId_whenExistsById_thenReturnFalse() {
        boolean exists = contentRepository.existsById(Integer.MAX_VALUE);
        assertThat(exists).isFalse();
    }

    @Test
    public void givenNewContent_whenSave_thenAddContentToContentList() {
        Content newContent = new Content(null, "New Title", "New Description", Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "http://newexample.com");
        Content savedContent = contentRepository.save(newContent);
        assertThat(savedContent).isNotNull();
        assertThat(contentRepository.existsById(savedContent.id())).isTrue();
    }

    @Test
    public void givenExistingContent_whenSave_thenUpdateContent() {
        Content updatedContent = new Content(contentId1, "Updated Title", "Updated Description", Status.COMPLETED, Type.ARTICLE, LocalDateTime.now(), null, "http://example.com/1");
        contentRepository.save(updatedContent);
        Optional<Content> found = contentRepository.findById(contentId1);
        assertThat(found).isPresent();
        assertThat(found.get().title()).isEqualTo("Updated Title");
    }

    @Test
    public void givenExistingId_whenDelete_thenRemoveFromContentList() {
        assertThat(contentRepository.existsById(contentId1)).isTrue();
        contentRepository.deleteById(contentId1);
        assertThat(contentRepository.existsById(contentId1)).isFalse();
    }
}