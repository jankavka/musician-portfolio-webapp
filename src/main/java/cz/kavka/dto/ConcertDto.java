package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ConcertDto(
        Long id,

        @NotBlank(message = "Zadejte jméno události")
        String name,

        @NotBlank(message = "Zadejte jméno kapely")
        String band,

        @NotNull(message = "Zadejte začátek události")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime startDateTime,

        String linkToEvent,

        String formattedTime
) {

}
