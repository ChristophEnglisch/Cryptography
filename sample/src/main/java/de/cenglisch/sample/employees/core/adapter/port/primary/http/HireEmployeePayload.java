package de.cenglisch.sample.employees.core.adapter.port.primary.http;

import de.cenglisch.sample.common.domain.Role;

import java.time.LocalDate;

public record HireEmployeePayload(String firstName, String lastName, LocalDate birthday, Role role) {
}
