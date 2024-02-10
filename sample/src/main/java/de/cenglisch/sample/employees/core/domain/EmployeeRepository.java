package de.cenglisch.sample.employees.core.domain;

import de.cenglisch.sample.employees.api.domain.EmployeeId;

import java.util.Collection;
import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> find(EmployeeId id);
    void save(Employee employee);

    void delete(EmployeeId employeeId);

    Collection<Employee> findAll();
}
