package com.stemapp.persistence;

import java.util.List;

/**
 * Created by Jamie on 7-4-2016.
 */
public interface DAO<T> {

    List<T> getAll();

    T get(int id);

    void add(T model);

    void update(int id, T model);

    void delete(int id);
}
