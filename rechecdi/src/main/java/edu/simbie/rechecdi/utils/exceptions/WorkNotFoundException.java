package edu.simbie.rechecdi.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkNotFoundException extends RuntimeException {
    public WorkNotFoundException(Long id) {
        super("Work with ID " + id + " not found.");
    }
}
