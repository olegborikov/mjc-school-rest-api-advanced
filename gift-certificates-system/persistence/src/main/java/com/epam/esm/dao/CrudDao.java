package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    T add(T t);

    List<T> findAll();

    Optional<T> findById(long id);

    boolean update(T t);

    boolean remove(long id);
}
