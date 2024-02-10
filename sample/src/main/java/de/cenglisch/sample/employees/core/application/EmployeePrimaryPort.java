package de.cenglisch.sample.employees.core.application;

import de.cenglisch.sample.common.domain.EventPublisher;
import de.cenglisch.sample.employees.api.domain.EmployeeLeftCompany;
import de.cenglisch.sample.employees.core.domain.*;

public class EmployeePrimaryPort {
    private final EmployeeRepository employeeRepository;
    private final EventPublisher eventPublisher;

    public EmployeePrimaryPort(EmployeeRepository employeeRepository, EventPublisher eventPublisher) {
        this.employeeRepository = employeeRepository;
        this.eventPublisher = eventPublisher;
    }

    public Employee hire(HireEmployee hireEmployee) {
        Employee employee = new Employee(
            hireEmployee.firstName(),
            hireEmployee.lastName(),
            hireEmployee.birthday(),
            hireEmployee.role()
        );
        employeeRepository.save(employee);
        return employee;
    }

    public void leaveCompany(EmployeeLeavesCompany employeeLeavesCompany) {
        employeeRepository.delete(employeeLeavesCompany.employeeId());
        eventPublisher.publish(EmployeeLeftCompany.of(employeeLeavesCompany));
    }
}
