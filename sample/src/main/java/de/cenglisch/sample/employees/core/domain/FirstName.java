package de.cenglisch.sample.employees.core.domain;

import de.cenglisch.sample.common.domain.ValidationException;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record FirstName(String value) {
    public FirstName {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("First name cannot be empty");
        }
        if (value.length() < 2) {
            throw new ValidationException("First name must be at least 2 characters long");
        }
        if (!value.matches("[A-Za-z-]+")) {
            throw new ValidationException("First name can only contain letters and hyphens");
        }
    }
}
