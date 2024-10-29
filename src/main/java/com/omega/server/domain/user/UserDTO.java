package com.omega.server.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.*;



public record UserDTO(
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        String firstName,

        @NotBlank(message = "El apellido no puede estar vacío")
        String lastName,

        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Email(message = "El formato del correo electrónico no es válido")
        String email,

        @NotBlank(message = "El teléfono no puede estar vacío")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "El teléfono debe tener entre 10 y 15 dígitos, incluyendo el código de país si es necesario")
        String telephone,

        @NotNull(message = "El ID de la compañía no puede estar vacío")
        @Positive(message = "El ID de la compañía debe ser un número positivo")
        Long companyId,
        @NotNull(message = "El rol del usuario no puede estar vacío")
        Rol rol,

        @JsonProperty(defaultValue = "false")
        Boolean isDeleted,

        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String username,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "La contraseña debe tener al menos 8 caracteres, " +
                        "incluir una letra mayúscula, " +
                        "una letra minúscula, " +
                        "un número y un carácter especial"
        )
        String password
) {
        public UserDTO {
                if (isDeleted == null) {
                        isDeleted = false;
                }
        }
}
