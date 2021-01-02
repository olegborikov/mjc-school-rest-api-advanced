package com.epam.esm.dao;

import java.util.List;

public interface CrudDao<T> {
    boolean add(T t);

    List<T> findAll();

    boolean update(T t);

    boolean remove(long id);
}
