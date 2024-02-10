package de.cenglisch.sample.employees.api.domain;

import java.util.UUID;

public record EmployeeId(String id) {
    public static EmployeeId gen() {
        return new EmployeeId(UUID.randomUUID().toString());
    }
}
