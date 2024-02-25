package de.cenglisch.cryptography.environment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceJpaRepository extends JpaRepository<AbsencesEntity, String> {
}
