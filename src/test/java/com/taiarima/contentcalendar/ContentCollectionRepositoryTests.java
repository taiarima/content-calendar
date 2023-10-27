package com.taiarima.contentcalendar;

import com.taiarima.contentcalendar.model.Content;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContentCollectionRepositoryTests {

    // Methods to test:
    // 1. findAll
    // 2. findById
    // 3. save
    // 4. existsById
    // 5. delete
    private Content testContent;

    @BeforeEach
    public void setup() {

    }
    @Test
    public void givenData_whenFindAll_thenReturnData() {

    }
    @Test
    public void givenNoData_whenFindAll_thenReturnEmptyArray() {

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
    public void givenExitingId_whenDelete_thenRemoveFromContentList() {

    }
}
