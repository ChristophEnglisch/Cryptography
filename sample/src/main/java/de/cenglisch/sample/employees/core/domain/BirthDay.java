package de.cenglisch.sample.employees.core.domain;

import de.cenglisch.sample.common.domain.ValidationException;
import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;
import java.time.Period;

@ValueObject
public record BirthDay(LocalDate localDate) {
    public BirthDay {
        if (localDate == null) {
            throw new ValidationException("Birth date cannot be null");
        }
        if (Period.between(localDate, LocalDate.now()).getYears() < 14) {
            throw new ValidationException("User must be at least 14 years old");
        }
    }
}
