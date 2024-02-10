package de.cenglisch.sample.employees.core.adapter.port.primary.http;

import de.cenglisch.sample.employees.core.application.EmployeeLeavesCompany;
import de.cenglisch.sample.employees.core.application.EmployeePrimaryPort;
import de.cenglisch.sample.employees.core.application.HireEmployee;
import de.cenglisch.sample.employees.core.domain.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("employees")
public class EmployeeRestAdapter {

    private final EmployeePrimaryPort employeePrimaryPort;
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public Collection<EmployeeResponse> get() {
        return employeeRepository.findAll().stream().map(EmployeeResponse::of).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse post(@RequestBody HireEmployeePayload hireEmployeePayload) {
        return EmployeeResponse.of(employeePrimaryPort.hire(HireEmployee.of(hireEmployeePayload)));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) {
        employeePrimaryPort.leaveCompany(EmployeeLeavesCompany.of(id));
    }
}
