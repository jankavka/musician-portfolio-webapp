package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;

public record ForgottenPasswordDto(
        @NotBlank(message = "Toto pole nesmí být prázdné")
        String username
) {
}
