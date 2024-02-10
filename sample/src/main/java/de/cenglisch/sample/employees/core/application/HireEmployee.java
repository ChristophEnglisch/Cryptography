package de.cenglisch.sample.employees.core.application;

import de.cenglisch.sample.common.domain.Role;
import de.cenglisch.sample.employees.core.adapter.port.primary.http.HireEmployeePayload;
import de.cenglisch.sample.employees.core.domain.BirthDay;
import de.cenglisch.sample.employees.core.domain.FirstName;
import de.cenglisch.sample.employees.core.domain.LastName;

public record HireEmployee(FirstName firstName, LastName lastName, BirthDay birthday, Role role) {
    public static HireEmployee of(HireEmployeePayload hireEmployeePayload) {
        return new HireEmployee(
            new FirstName(hireEmployeePayload.firstName()),
            new LastName(hireEmployeePayload.lastName()),
            new BirthDay(hireEmployeePayload.birthday()),
            hireEmployeePayload.role()
        );
    }
}
