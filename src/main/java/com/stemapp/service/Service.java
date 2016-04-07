package com.stemapp.service;

import java.util.Collection;

/**
 * Created by Jamie on 7-4-2016.
 */
interface Service<T> {

    Collection<T> getAll();

    T get(int id);

    void add(T model);

    void update(int id, T model);

    void delete(int id);
}
