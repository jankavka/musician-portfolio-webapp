package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeDto(
        @NotBlank(message = "Pole nesmí být prázdné")
        @Size(min = 6, message = "Heslo musí mít minimálně 6 znaků")
        String password,
        String confirmPassword
) {

}
