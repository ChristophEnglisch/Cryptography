package de.cenglisch.sample.absence.core.adapter.port.primary.rest;

import de.cenglisch.sample.absence.core.application.AbsencePrimaryPort;
import de.cenglisch.sample.absence.core.application.CreateAbsence;
import de.cenglisch.sample.absence.core.domain.AbsenceRepository;
import de.cenglisch.sample.employees.api.domain.EmployeeId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("absences")
public class AbsenceRestAdapter {

    private final AbsencePrimaryPort absencePrimaryPort;
    private final AbsenceRepository absenceRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AbsenceResponse post(@RequestBody CreateAbsencePayload createAbsencePayload) {
        return AbsenceResponse.of(absencePrimaryPort.createAbsence(CreateAbsence.of(createAbsencePayload)));
    }

    @GetMapping
    public Collection<AbsenceResponse> get() {
        return absenceRepository.findAll().stream().map(AbsenceResponse::of).toList();
    }

    @GetMapping(params = "employee-id")
    public Collection<AbsenceResponse> getByEmployeeId(@RequestParam("employee-id") String employeeId) {
        return absenceRepository.findByEmployeeId(new EmployeeId(employeeId)).stream().map(AbsenceResponse::of).toList();
    }

    @DeleteMapping(params = "employee-id")
    public void deleteByEmployeeId(@RequestParam("employee-id") String employeeId) {
        absenceRepository.deleteByEmployeeId(new EmployeeId(employeeId));
    }
}
