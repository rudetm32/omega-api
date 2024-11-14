package com.omega.server.infra.exception.validation;

import com.omega.server.infra.exception.CustomException;

public class DateValidationException extends CustomException {
    public DateValidationException(String message) {
        super(message);
    }
}
