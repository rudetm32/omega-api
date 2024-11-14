package com.omega.server.infra.exception.validation;


import java.time.LocalDateTime;

public class DateRangeValidator implements CustomValidation {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public DateRangeValidator(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void validate() throws DateValidationException { // Cambia a lanzar DateValidationException
        if (from.isAfter(to)) {
            throw new DateValidationException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
    }
}
