package com.epam.esm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResultDTO {

    private String token;
    private long tokenValidity;
}
