package com.taiarima.contentcalendar.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

public record Content(
        @NotNull @Min(1) Integer id,
        @NotBlank @Size(min = 3, max = 100) String title,
        @NotBlank @Size(min = 3, max = 500) String desc,
        @NotNull Status status,
        @NotNull Type contentType,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated,
        @URL @NotBlank String url
) {

}
