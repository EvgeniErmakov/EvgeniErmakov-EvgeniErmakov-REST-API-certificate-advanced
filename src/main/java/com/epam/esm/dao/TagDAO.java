package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;

import java.util.Optional;

public interface TagDAO extends CommonDAO<Tag, Long> {

    Optional<Tag> findByName(String name);

    Tag find(Tag tag);

    Tag findMostPopularTag();
}
