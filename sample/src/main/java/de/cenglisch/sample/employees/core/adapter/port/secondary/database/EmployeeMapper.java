package de.cenglisch.sample.employees.core.adapter.port.secondary.database;

import de.cenglisch.sample.employees.api.domain.EmployeeId;
import de.cenglisch.sample.employees.core.domain.BirthDay;
import de.cenglisch.sample.employees.core.domain.Employee;
import de.cenglisch.sample.employees.core.domain.FirstName;
import de.cenglisch.sample.employees.core.domain.LastName;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee to(EmployeeEntity employeeEntity) {
        return new Employee(
            new EmployeeId(employeeEntity.getId()),
            new FirstName(employeeEntity.getFirstName()),
            new LastName(employeeEntity.getLastName()),
            new BirthDay(employeeEntity.getBirthday()),
            employeeEntity.getRole()
        );
    }

    public EmployeeEntity to(Employee employee) {
        return new EmployeeEntity(
            employee.getEmployeeId() != null ? employee.getEmployeeId().id() : null,
            employee.getFirstName().value(),
            employee.getLastName().value(),
            employee.getBirthday().localDate(),
            employee.getRole()
        );
    }
}