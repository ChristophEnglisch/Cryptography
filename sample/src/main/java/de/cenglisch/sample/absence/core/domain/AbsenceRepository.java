package de.cenglisch.sample.absence.core.domain;

import de.cenglisch.sample.employees.api.domain.EmployeeId;

import java.util.Collection;

public interface AbsenceRepository {

    Collection<Absence> findByEmployeeId(EmployeeId employeeId);
    void deleteByEmployeeId(EmployeeId employeeId);
    void save(Absence absence);

    Collection<Absence> findAll();
}
