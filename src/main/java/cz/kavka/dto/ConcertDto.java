package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConcertDto(
        Long id,

        @NotBlank(message = "Zadejte jméno události")
        String name,

        @NotBlank(message = "Zadejte jméno kapely")
        String band,

        @NotNull(message = "Zadejte začátek události")
        LocalDateTime startDateTime,

        String linkToEvent
) {

}
