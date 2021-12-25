package com.epam.esm.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class AuthenticationRequestDTO {

    @NonNull
    @NotBlank
    @Size(min = 1, max = 15)
    private String username;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String password;
}
