package de.cenglisch.sample.absence.core.adapter.port.secondary;

import de.cenglisch.cryptography.pseudonymization.Pseudonymize;
import de.cenglisch.sample.employees.core.adapter.port.secondary.database.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AbsenceJpaRepository extends JpaRepository<AbsenceEntity, String> {
    Collection<AbsenceEntity> findByEmployeeId(@Pseudonymize(entity = EmployeeEntity.class) String employeeId);

    void deleteByEmployeeId(@Pseudonymize(entity = EmployeeEntity.class) String employeeId);
}