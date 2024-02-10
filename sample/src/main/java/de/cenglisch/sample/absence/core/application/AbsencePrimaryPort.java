package de.cenglisch.sample.absence.core.application;

import de.cenglisch.sample.absence.core.domain.Absence;
import de.cenglisch.sample.absence.core.domain.AbsenceRepository;
import de.cenglisch.sample.employees.api.domain.EmployeeId;

import java.util.Collection;

public class AbsencePrimaryPort {
    private final AbsenceRepository absenceRepository;

    public AbsencePrimaryPort(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

    public Absence createAbsence(CreateAbsence createAbsence) {
        Absence absence = new Absence(
            createAbsence.employeeId(),
            createAbsence.typeOfAbsence(),
            createAbsence.absenceReason()
        );
        absenceRepository.save(absence);
        return absence;
    }

    public void deleteAllAbsencesForEmployee(EmployeeId employeeId) {
        absenceRepository.deleteByEmployeeId(employeeId);
    }
}
