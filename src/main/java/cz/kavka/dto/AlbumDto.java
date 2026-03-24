package cz.kavka.dto;

import cz.kavka.entity.PhotoEntity;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AlbumDto(
        Long id,

        @NotBlank(message = "Jméno nesmí být prázdné")
        String name,

        @NotBlank(message = "Popis nesmí být prázdný")
        String description,

        String albumPath,

        List<PhotoEntity> photos
) {
}
