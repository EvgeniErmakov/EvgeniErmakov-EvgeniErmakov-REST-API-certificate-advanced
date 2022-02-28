package com.epam.esm.dao;

import com.epam.esm.model.entity.Page;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CommonDAO<T, K> {

    List<T> findAll(Page page);

    Optional<T> findById(K k);

    T create(T t);

    void delete(T t);

    BigInteger getCountOfCertificate();
}
