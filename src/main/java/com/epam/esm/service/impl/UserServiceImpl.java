package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.exception.EntityNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    public final UserDAO userDAO;
    public final MapperDTO mapperDTO;

    @Override
    public List<UserDTO> findAll(Page page) {
        return userDAO.findAll(page)
                .stream()
                .map(mapperDTO::convertUserToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        return mapperDTO.convertUserToDTO(userDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        throw new UnsupportedOperationException("method not worked");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("method not worked");
    }
}
