package com.epam.esm.service;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.entity.User;
import com.epam.esm.util.MapperDTO;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    MapperDTO mapperDTO;

    @Mock
    private UserDAO userDAO;

    private User user;

    @BeforeEach
    public void initUser() {
        user = User.builder().name("Zhenya").build();
    }

    @Test
    void findUserById() {
        Long id = 1L;
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userDAO.findById(id)).thenReturn(optionalUser);
        UserDTO actual = userService.findById(id);
        UserDTO expected = mapperDTO.convertUserToDTO(user);
        verify(userDAO).findById(id);
        verifyNoMoreInteractions(userDAO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllUsers() {
        List<User> users = new ArrayList<>();
        Mockito.when(userDAO.findAll(new Page())).thenReturn(users);
        users.add(user);
        List<UserDTO> expected = users.stream().map(mapperDTO::convertUserToDTO)
            .collect(Collectors.toList());
        List<UserDTO> actual = userService.findAll(new Page());
        verify(userDAO).findAll(new Page());
        verifyNoMoreInteractions(userDAO);
        Assertions.assertEquals(expected, actual);
    }
}
