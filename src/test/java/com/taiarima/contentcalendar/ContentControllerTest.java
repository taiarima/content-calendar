package com.taiarima.contentcalendar;

import com.taiarima.contentcalendar.constants.PathConstants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiarima.contentcalendar.model.Content;
import com.taiarima.contentcalendar.repository.ContentCollectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.taiarima.contentcalendar.model.Status;
import com.taiarima.contentcalendar.model.Type;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentCollectionRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private Content testContent;

    @BeforeEach
    public void setup() {
        testContent = new Content(1, "Test Date", "Just putting some test data", Status.IDEA, Type.ARTICLE, null, null, "www.google.com");
    }
    @Test
    public void givenExistentId_whenFindById_thenStatusOk() throws Exception {
        Integer id = testContent.id();

        when(repository.findById(id)).thenReturn(Optional.of(testContent));

        // Act & Assert
        mockMvc.perform(get(PathConstants.CONTENT_BASE_PATH + PathConstants.CONTENT_BY_ID_PATH, id))
                .andExpect(status().isOk());
    }
    @Test
    public void givenNonExistentId_whenFindById_thenStatusNotFound() throws Exception {
        // Given
        Integer id = 0;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(PathConstants.CONTENT_BASE_PATH + PathConstants.CONTENT_BY_ID_PATH, id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenFindAll_thenStatusOk() throws Exception {

        List<Content> expectedContentList = List.of(testContent);
        when(repository.findAll()).thenReturn(expectedContentList);

        mockMvc.perform(get(PathConstants.CONTENT_BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(testContent))));
    }

    @Test
    void whenCreate_thenStatusCreated() throws Exception {
        Content newContent = new Content(2, "New Date", "Creating new content", Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "www.example.com");


        mockMvc.perform(
                        post(PathConstants.CONTENT_BASE_PATH)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(newContent))
                )
                .andExpect(status().isCreated());

    }

    @Test
    void givenExistentId_whenUpdate_thenStatusNoContent() throws Exception {
        Content updatedContent = new Content(1, "Test Data", "Just updating some test data", Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "www.google.com");
        Integer id = updatedContent.id();
        when(repository.existsById(id)).thenReturn(true);

        mockMvc.perform(
                        put(PathConstants.CONTENT_BASE_PATH + PathConstants.CONTENT_BY_ID_PATH, id)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updatedContent))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void givenNonExistentId_whenUpdate_thenStatusNotFound() throws Exception {
        Content updatedContent = new Content(0, "Test Data", "Just updating some test data", Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "www.google.com");
        Integer id = updatedContent.id();
        mockMvc.perform(
                        put(PathConstants.CONTENT_BASE_PATH + PathConstants.CONTENT_BY_ID_PATH, id)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updatedContent))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void givenExistingId_whenDelete_thenStatusNoContent() throws Exception {

        Integer id = testContent.id();

        when(repository.existsById(testContent.id())).thenReturn(true);
        mockMvc.perform(
                delete(PathConstants.CONTENT_BASE_PATH + PathConstants.CONTENT_BY_ID_PATH, id)

        ).andExpect(status().isNoContent());
    }

    @Test
    void givenNonExistentId_whenDelete_thenStatusNotFound() throws Exception {
        Integer id = testContent.id();

        mockMvc.perform(
                delete(PathConstants.CONTENT_BASE_PATH + PathConstants.CONTENT_BY_ID_PATH, id)

        ).andExpect(status().isNotFound());
    }
}
