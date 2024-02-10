package de.cenglisch.sample.employees.core.adapter.port.secondary.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, String> {

}
