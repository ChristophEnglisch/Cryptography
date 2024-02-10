package de.cenglisch.sample.employees.core.adapter.port.secondary.database;

import de.cenglisch.sample.employees.api.domain.EmployeeId;
import de.cenglisch.sample.employees.core.domain.Employee;
import de.cenglisch.sample.employees.core.domain.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeePersistenceAdapter implements EmployeeRepository {

    private final EmployeeMapper employeeMapper;
    private final EmployeeJpaRepository employeeJpaRepository;

    @Override
    public Optional<Employee> find(EmployeeId id) {
        return employeeJpaRepository.findById(id.id()).map(employeeMapper::to);
    }

    @Override
    public Collection<Employee> findAll() {
        return employeeJpaRepository.findAll().stream().map(employeeMapper::to).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void save(Employee employee) {
        EmployeeEntity employeeEntity = employeeMapper.to(employee);
        employeeJpaRepository.save(employeeEntity);
    }

    @Override
    public void delete(EmployeeId employeeId) {
        employeeJpaRepository.deleteById(employeeId.id());
    }
}
