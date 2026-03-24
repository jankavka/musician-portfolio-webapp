package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;

public record PhotoDto (
        Long id,

        @NotBlank
        String name,

        String url,

        AlbumDto album,

        ProjectDto project
) {
}
