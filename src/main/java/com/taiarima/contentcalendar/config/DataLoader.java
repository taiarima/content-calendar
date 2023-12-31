package com.taiarima.contentcalendar.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiarima.contentcalendar.model.Content;
import org.springframework.boot.CommandLineRunner;
import com.taiarima.contentcalendar.repository.ContentRepository;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ContentRepository repository;
    private final ObjectMapper objectMapper;

    public DataLoader(ContentRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/content.json")) {
            if (repository.count() == 0) {
                repository.saveAll(objectMapper.readValue(inputStream,new TypeReference<List<Content>>(){}));
            }
        }
    }

}