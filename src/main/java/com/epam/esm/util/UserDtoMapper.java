package com.epam.esm.util;

import com.epam.esm.model.dto.Role;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;
import javax.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDtoMapper(ModelMapper modelMapper,
                         PasswordEncoder passwordEncoder) {
        this.mapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setPassword)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(m-> m.skip(User::setOrders)).setPostConverter(toEntityConverter());
    }

    public Converter<User, UserDTO> toDtoConverter() {
        return context -> {
            UserDTO destination = context.getDestination();
            mapSpecificFields(destination);
            return context.getDestination();
        };
    }

    public Converter<UserDTO, User> toEntityConverter() {
        return context -> {
            UserDTO source = context.getSource();
            User destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public void mapSpecificFields(UserDTO destination) {
        destination.setPassword(null);
    }

    public void mapSpecificFields(UserDTO source, User destination) {
        String password = source.getPassword();
        if(password != null) {
            destination.setPassword(passwordEncoder.encode(password));
        }
        destination.setRole(Role.ROLE_USER);
    }
}
