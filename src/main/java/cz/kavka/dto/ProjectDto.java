package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectDto(
        Long id,

        @NotBlank(message = "Jméno nesmí být prázdné")
        String name,

        @NotBlank(message = "popis nesmí být prázdný")
        @Size(max = 2000, message = "Maximální délka popisu je 2000 znaků")
        String description,

        PhotoDto photo
) {
}
