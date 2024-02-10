package de.cenglisch.sample.absence.core.application;

import de.cenglisch.sample.absence.core.adapter.port.primary.rest.CreateAbsencePayload;
import de.cenglisch.sample.absence.core.domain.AbsenceReason;
import de.cenglisch.sample.absence.core.domain.TypeOfAbsence;
import de.cenglisch.sample.employees.api.domain.EmployeeId;

public record CreateAbsence(EmployeeId employeeId, TypeOfAbsence typeOfAbsence, AbsenceReason absenceReason) {
    public static CreateAbsence of(CreateAbsencePayload createAbsencePayload) {
        return new CreateAbsence(
            new EmployeeId(createAbsencePayload.employeeId()),
            createAbsencePayload.typeOfAbsence(),
            new AbsenceReason(createAbsencePayload.reason())
        );
    }
}
