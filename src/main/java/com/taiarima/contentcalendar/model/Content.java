package com.taiarima.contentcalendar.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Content(

        @Id @NotNull @Min(1) Integer id,
        @NotBlank @Size(min = 3, max = 100) String title,
        @NotBlank @Size(min = 3, max = 500) String desc,
        @NotNull Status status,
        @NotNull Type contentType,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated,
        @URL String url
) {

}
