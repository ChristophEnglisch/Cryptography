package de.cenglisch.sample.employees.core.domain;

import de.cenglisch.sample.common.domain.ValidationException;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record LastName(String value) {
    public LastName {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("Last name cannot be empty");
        }
        if (value.length() < 2) {
            throw new ValidationException("Last name must be at least 2 characters long");
        }
        if (!value.matches("[A-Za-z-']+")) {
            throw new ValidationException("Last name can only contain letters, hyphens, and apostrophes");
        }
    }
}
