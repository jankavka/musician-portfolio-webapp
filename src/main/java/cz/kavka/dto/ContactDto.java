package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;

public record ContactDto(

        @NotBlank(message = "Vyplňte telefonní číslo")
        String telNumber,

        @NotBlank(message = "Vyplňte email")
        String email
) {
}
