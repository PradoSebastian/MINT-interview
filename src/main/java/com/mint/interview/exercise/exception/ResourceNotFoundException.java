package com.mint.interview.exercise.exception;

import com.mint.interview.exercise.constants.CommonMessages;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(Object... args) {
        super(HttpStatus.NOT_FOUND, CommonMessages.RESOURCE_NOT_FOUND, args);
    }
}
