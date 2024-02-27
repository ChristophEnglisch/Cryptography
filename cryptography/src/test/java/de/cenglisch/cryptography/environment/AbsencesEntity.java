package de.cenglisch.cryptography.environment;

import de.cenglisch.cryptography.gdpr.GdprRelevant;
import de.cenglisch.cryptography.pseudonymization.Pseudonymize;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AbsencesEntity {
    @Id
    private String id;
    @Pseudonymize(entity = EmployeeEntity.class)
    private String employeeId;
    @GdprRelevant
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
