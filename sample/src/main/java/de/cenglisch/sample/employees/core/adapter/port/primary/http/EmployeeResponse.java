package de.cenglisch.sample.employees.core.adapter.port.primary.http;

import de.cenglisch.sample.common.domain.Role;
import de.cenglisch.sample.employees.core.domain.Employee;

import java.time.LocalDate;

public record EmployeeResponse(String employeeId, String firstName, String lastName, LocalDate birthday, Role role) {
    public static EmployeeResponse of(Employee employee) {
        return new EmployeeResponse(
            employee.getEmployeeId().id(),
            employee.getFirstName().value(),
            employee.getLastName().value(),
            employee.getBirthday().localDate(),
            employee.getRole()
        );
    }
}
