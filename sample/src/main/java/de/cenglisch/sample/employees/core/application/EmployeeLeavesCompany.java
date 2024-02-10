package de.cenglisch.sample.employees.core.application;

import de.cenglisch.sample.employees.api.domain.EmployeeId;

public record EmployeeLeavesCompany(EmployeeId employeeId) {
    public static EmployeeLeavesCompany of(String id) {
        return new EmployeeLeavesCompany(new EmployeeId(id));
    }
}
