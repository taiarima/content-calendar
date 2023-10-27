package com.taiarima.contentcalendar;

import com.taiarima.contentcalendar.model.Content;
import com.taiarima.contentcalendar.model.Status;
import com.taiarima.contentcalendar.model.Type;
import com.taiarima.contentcalendar.repository.ContentCollectionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ContentCollectionRepositoryTests {

    private Content testContent;


    @Test
    public void givenData_whenFindAll_thenReturnData() {
        // Arrange
        ContentCollectionRepository repository = new ContentCollectionRepository();
        Content testContent = new Content(1, "Test Date", "Just putting some test data", Status.IDEA,
                Type.ARTICLE, null, null, "www.google.com");
        repository.save(testContent);

        // Act
        List<Content> foundContentList = repository.findAll();

        // Assert
        assertEquals(1, foundContentList.size());
        assertEquals(testContent, foundContentList.get(0));
    }
    @Test
    public void givenNoData_whenFindAll_thenReturnEmptyList() {

    }

    @Test
    public void givenExistingId_whenFindById_thenReturnCorrespondingContent() {

    }

    @Test
    public void givenNonExistentId_whenFindById_thenReturnNothing() {

    }

    @Test
    public void givenExistingId_whenExistsById_thenReturnTrue() {

    }

    @Test
    public void givenNonExistentId_whenExistsById_thenReturnFalse() {

    }

    @Test
    public void givenNewContent_whenSave_thenAddContentToContentList() {

    }

    @Test
    public void givenExistingContent_whenSave_thenUpdateContent() {

    }

    @Test
    public void givenExistingId_whenDelete_thenRemoveFromContentList() {

    }
}
