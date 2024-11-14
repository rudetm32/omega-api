package com.omega.server.infra.exception.validation;

import com.omega.server.infra.exception.CustomException;

public interface CustomValidation {
    void validate() throws CustomException;
}
