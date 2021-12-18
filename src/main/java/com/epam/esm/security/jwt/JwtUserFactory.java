package com.epam.esm.security.jwt;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.dto.Role;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
            user.getId(),
            user.getLogin(),
            user.getPassword(),
            mapToGrantedAuthorities(user.getRole()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role userRole) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole.toString()));
        return authorities;
    }
}
