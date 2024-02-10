package de.cenglisch.sample.absence.core.domain;

import java.util.UUID;

public record AbsenceId(String id) {
    public static AbsenceId gen() {
        return new AbsenceId(UUID.randomUUID().toString());
    }
}
