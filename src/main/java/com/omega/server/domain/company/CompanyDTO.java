package com.omega.server.domain.company;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.omega.server.domain.address.AddressDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record CompanyDTO(
        Long id,

        @NotBlank(message= "El nombre de la compañia no puede estar vacio")
        String name,

        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Email(message = "El formato del correo electrónico no es válido")
        String email,

        @NotBlank(message = "El teléfono no puede estar vacío")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "El teléfono debe tener entre 10 y 15 dígitos, incluyendo el código de país si es necesario")
        String telephone,

        @NotBlank(message = "El nombre del contacto no puede estar vacío")
        String contactName,

        @JsonProperty(defaultValue = "false")
        Boolean isDeleted,

        AddressDTO address

) {
    public CompanyDTO {
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

}
