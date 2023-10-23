package com.taiarima.contentcalendar.controller;

import com.taiarima.contentcalendar.model.Content;
import com.taiarima.contentcalendar.repository.ContentCollectionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentCollectionRepository repository;

    public ContentController(ContentCollectionRepository contentCollectionRepository) {
        this.repository = contentCollectionRepository;
    }

    // Make a request and find all pieces of content in the system
    @GetMapping("")
    public List<Content> findAll() {
        return repository.findAll();
    }
}
