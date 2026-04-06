package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;

public record VideoDto(
        Long id,

        @NotBlank(message = "Toto pole nesmí být prázdné")
        String url,

        String videoYoutubeId
) {
}
