package com.epam.esm.dao;

import com.epam.esm.model.entity.User;
import java.util.Optional;

public interface UserDAO extends CommonDAO<User, Long> {

    Optional<User> findUserByLogin(String username);
}
