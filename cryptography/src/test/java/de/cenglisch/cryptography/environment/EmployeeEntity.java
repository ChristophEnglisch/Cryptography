package de.cenglisch.cryptography.environment;

import de.cenglisch.cryptography.gdpr.GdprRelevant;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EmployeeEntity {
    @Id
    private String id;
    @GdprRelevant
    private String firstName;
    @GdprRelevant
    private String lastName;
}
