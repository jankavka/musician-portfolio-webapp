package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectDto(
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String description,

        PhotoDto photo
) {
}
