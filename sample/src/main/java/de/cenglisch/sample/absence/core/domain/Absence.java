package de.cenglisch.sample.absence.core.domain;

import de.cenglisch.sample.common.domain.Entity;
import de.cenglisch.sample.employees.api.domain.EmployeeId;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;

@AggregateRoot
@Getter
public class Absence extends Entity {
    private AbsenceId absenceId;
    private EmployeeId employeeId;
    private TypeOfAbsence typeOfAbsence;
    private AbsenceReason reason;

    public Absence(EmployeeId employeeId, TypeOfAbsence typeOfAbsence, AbsenceReason reason) {
        setAbsenceId(AbsenceId.gen());
        setEmployeeId(employeeId);
        setTypeOfAbsence(typeOfAbsence);
        setReason(reason);
    }

    public Absence(AbsenceId absenceId, EmployeeId employeeId, TypeOfAbsence typeOfAbsence, AbsenceReason reason) {
        setAbsenceId(absenceId);
        setEmployeeId(employeeId);
        setTypeOfAbsence(typeOfAbsence);
        setReason(reason);
    }

    private void setAbsenceId(AbsenceId absenceId) {
        if (absenceId == null) {
            throw new IllegalArgumentException("Cannot provide null value for absenceId");
        }
        if (this.absenceId != null) {
            return;
        }
        this.absenceId = absenceId;
    }

    private void setEmployeeId(EmployeeId employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Cannot provide null value for employeeId");
        }
        if (this.employeeId != null) {
            return;
        }
        this.employeeId = employeeId;
    }

    public void setTypeOfAbsence(TypeOfAbsence typeOfAbsence) {
        if (typeOfAbsence == null) {
            throw new IllegalArgumentException("Cannot provide null value for typeOfAbsence");
        }
        this.typeOfAbsence = typeOfAbsence;
    }

    public void setReason(AbsenceReason absenceReason) {
        if (absenceReason == null) {
            throw new IllegalArgumentException("Cannot provide null value for absenceReason");
        }
        this.reason = absenceReason;
    }
}
