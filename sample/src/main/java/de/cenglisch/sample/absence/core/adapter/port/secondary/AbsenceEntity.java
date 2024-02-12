package de.cenglisch.sample.absence.core.adapter.port.secondary;

import de.cenglisch.cryptography.dsgvo.DsgvoRelevant;
import de.cenglisch.cryptography.pseudonymization.Pseudonymize;
import de.cenglisch.sample.absence.core.domain.TypeOfAbsence;
import de.cenglisch.sample.employees.core.adapter.port.secondary.database.EmployeeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "absences")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AbsenceEntity {
    @Id
    private String id;
    @Pseudonymize(entity = EmployeeEntity.class)
    private String employeeId;
    @Enumerated(EnumType.STRING)
    private TypeOfAbsence typeOfAbsence;
    @DsgvoRelevant
    private String reason;
}
