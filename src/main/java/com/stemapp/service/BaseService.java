package com.stemapp.service;

import javax.ws.rs.NotFoundException;

/**
 * Created by Jamie on 27-3-2016.
 */
public class BaseService<T> {

    public T requireResult(T model) {
        if(model == null) {
            throw new NotFoundException();
        }

        return model;
    }
}
