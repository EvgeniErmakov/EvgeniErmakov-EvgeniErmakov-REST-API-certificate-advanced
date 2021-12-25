package com.epam.esm.dao;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Sql(scripts = "classpath:/data_test_H2.sql")
class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    @Transactional
    void findAllUsers() {
        Page page = new Page();
        List<User> usersList = userDAO.findAll(page);
        Assertions.assertEquals(3, usersList.size());
    }

    @Test
    @Transactional
    void findUserById() {
        Long id = 1L;
        Optional<User> actual = userDAO.findById(id);
        Assertions.assertEquals("Zhenya", actual.get().getFirstName());
    }
}
