package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.dto.AuthenticationRequestDTO;
import com.epam.esm.model.dto.AuthenticationResultDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDAO userDAO;
    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
        JwtTokenProvider jwtTokenProvider,
        UserDAO userDAO) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDAO = userDAO;
    }

    public AuthenticationResultDTO getAuthenticationResult(AuthenticationRequestDTO requestDTO) {
        System.out.println(requestDTO.getUsername());
        System.out.println(requestDTO.getPassword());
        try {
            String username = requestDTO.getUsername();
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, requestDTO.getPassword()));
            User user = userDAO.findUserByLogin(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
            String token = jwtTokenProvider.createToken(username, user.getRole());
            return new AuthenticationResultDTO(token, validityInMilliseconds);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
