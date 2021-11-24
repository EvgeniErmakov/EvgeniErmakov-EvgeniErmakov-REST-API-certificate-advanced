package com.epam.esm.service;

import com.epam.esm.model.entity.Page;

import java.util.List;

public interface CommonService<T, K> {

    List<T> findAll(Page page);

    T findById(K k);

    T create(T t);

    void delete(K k);
}
