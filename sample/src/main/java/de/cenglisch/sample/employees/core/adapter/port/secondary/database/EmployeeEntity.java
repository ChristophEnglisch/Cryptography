package de.cenglisch.sample.employees.core.adapter.port.secondary.database;

import de.cenglisch.sample.common.domain.DsgvoRelevant;
import de.cenglisch.sample.common.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeeEntity {
    @Id
    private String id;
    @DsgvoRelevant
    private String firstName;
    @DsgvoRelevant
    private String lastName;
    //@DsgvoRelevant
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Role role;
}
