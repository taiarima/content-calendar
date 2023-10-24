package com.taiarima.contentcalendar;

import com.taiarima.contentcalendar.model.Content;
import com.taiarima.contentcalendar.model.Status;
import com.taiarima.contentcalendar.model.Type;
import com.taiarima.contentcalendar.repository.ContentCollectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentCollectionRepository repository;

    @Test
    public void findById_NotFound() throws Exception {
        // Given
        Integer id = 0;
        given(repository.findById(id)).willReturn(Optional.empty());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/content/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindById_existingId_shouldReturn200() throws Exception {
        // Arrange
        Content c = new Content(1, "Test Date", "Just putting some test data", Status.IDEA, Type.ARTICLE, LocalDateTime.now(), null, "www.google.com");
        when(repository.findById(1)).thenReturn(Optional.of(c));

        // Act & Assert
        mockMvc.perform(get("/api/content/1"))
                .andExpect(status().isOk());
    }
}
