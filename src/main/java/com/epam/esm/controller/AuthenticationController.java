package com.epam.esm.controller;

import com.epam.esm.model.dto.AuthenticationRequestDTO;
import com.epam.esm.model.dto.AuthenticationResultDTO;
import com.epam.esm.service.impl.AuthenticationServiceImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@Valid
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login")
    public AuthenticationResultDTO login(@RequestBody @Valid AuthenticationRequestDTO requestDto) {
        return authenticationService.getAuthenticationResult(requestDto);
    }
}
