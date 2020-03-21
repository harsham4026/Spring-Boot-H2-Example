package com.springboot.h2.inmemory.db.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * Custom exception for handling when record is not found in the database.
 *
 * @author Harsha Mandadi
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable t) {
        super(message, t);
    }
}
