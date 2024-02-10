package de.cenglisch.sample.employees.api.domain;

import de.cenglisch.sample.common.domain.DomainEvent;
import de.cenglisch.sample.employees.core.application.EmployeeLeavesCompany;

public record EmployeeLeftCompany(EmployeeId employeeId) implements DomainEvent {
    public static EmployeeLeftCompany of(EmployeeLeavesCompany employeeLeavesCompany) {
        return new EmployeeLeftCompany(employeeLeavesCompany.employeeId());
    }

    @Override
    public int eventVersion() {
        return 1;
    }
}
