package com.taiarima.contentcalendar;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiarima.contentcalendar.model.Content;
import com.taiarima.contentcalendar.repository.ContentCollectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;


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
        testContent = new Content(1, "Test Date", "Just putting some test data", Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "www.google.com");
    }
    @Test
    public void givenExistentId_whenFindById_thenStatusOk() throws Exception {

        when(repository.findById(1)).thenReturn(Optional.of(testContent));

        // Act & Assert
        mockMvc.perform(get("/api/content/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void givenNonExistentId_whenFindById_thenStatusNotFound() throws Exception {
        // Given
        Integer id = 0;
        given(repository.findById(id)).willReturn(Optional.empty());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/content/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenFindAll_thenStatusOk() throws Exception {

        when(repository.findAll()).thenReturn(List.of(testContent));

        mockMvc.perform(get("/api/content"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(testContent))));
    }

    @Test
    void whenCreate_thenStatusCreated() throws Exception {
        Content newContent = new Content(2, "New Date", "Creating new content", Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "www.example.com");

        mockMvc.perform(
                        post("/api/content")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(newContent))
                )
                .andExpect(status().isCreated());

    }

    @Test
    void givenExistentId_whenUpdate_thenStatusOk() throws Exception {
        Content updatedContent = new Content(1, "Test Data", "Just updating some test data", Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "www.google.com");

        mockMvc.perform(
                        put("/api/content/1")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updatedContent))
                )
                .andExpect(status().isOk());
    }

    @Test
    void givenNonExistentId_whenUpdate_thenStatusNotFound() throws Exception {
        Content updatedContent = new Content(1, "Test Data", "Just updating some test data", Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "www.google.com");

    }
}
