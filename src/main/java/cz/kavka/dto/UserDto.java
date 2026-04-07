package cz.kavka.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(
        Long id,

        @NotBlank(message = "Pole nesmí být prázdné")
        @Email(message = "Formát musí odpovídat emailu")
        String email,

        @NotBlank(message = "Pole nesmé být prázdné")
        @Size(min = 6, message = "Heslo musí obsahovat minimálně 6 znaků")
        String password,

        @NotBlank(message = "Pole nesmé být prázdné")
        String confirmPassword
) {

}
