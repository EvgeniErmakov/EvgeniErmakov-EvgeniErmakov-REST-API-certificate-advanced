package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Sql(scripts = "classpath:/data_test_H2.sql")
class TagDAOImplTest {

    @Autowired
    private TagDAO tagDAO;

    @Test
    void findAllTags() {
        Page page = new Page();
        List<Tag> tagList = tagDAO.findAll(page);
        Assertions.assertEquals(5, tagList.size());
    }

    @Test
    void findTagById() {
        Long id = 5L;
        Optional<Tag> tag = tagDAO.findById(id);
        Assertions.assertEquals("fitness", tag.get().getName());
    }

    @Test
    void findByTagName() {
        Optional<Tag> actual = tagDAO.findByName("SPA");
        Assertions.assertEquals(1, actual.get().getId());
    }

    @Test
    void findOrCreateTag() {
        Tag tag = Tag.builder().name("Epam").build();
        Tag actual = tagDAO.findOrCreate(tag);
        Assertions.assertEquals("Epam", actual.getName());
    }

    @Test
    void findMostPopularTag() {
        String actual = tagDAO.findMostPopularTag().getName();
        Assertions.assertEquals("massage", actual);
    }
}
