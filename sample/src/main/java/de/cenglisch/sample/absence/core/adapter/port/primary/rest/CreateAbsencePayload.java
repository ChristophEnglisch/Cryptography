package de.cenglisch.sample.absence.core.adapter.port.primary.rest;

import de.cenglisch.sample.absence.core.domain.TypeOfAbsence;

public record CreateAbsencePayload(String employeeId, TypeOfAbsence typeOfAbsence, String reason) {
}
