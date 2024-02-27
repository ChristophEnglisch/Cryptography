package de.cenglisch.cryptography.performance;

import de.cenglisch.cryptography.config.PostgresTestcontainerConfig;
import de.cenglisch.cryptography.environment.AbsenceJpaRepository;
import de.cenglisch.cryptography.environment.AbsencesEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = {PostgresTestcontainerConfig.class})
class SavePerformanceTest {

    @Autowired
    private AbsenceJpaRepository absencesRepository;

    @Test
    void testSaveAndFindPerformance() {
        String employeeId = UUID.randomUUID().toString();
        String reason = "Personal";

        AbsencesEntity absencesEntity = new AbsencesEntity();
        absencesEntity.setId(UUID.randomUUID().toString());
        absencesEntity.setEmployeeId(employeeId);
        absencesEntity.setReason(reason);

        absencesRepository.save(absencesEntity);

        AbsencesEntity foundEntity = absencesRepository.findById(absencesEntity.getId()).orElse(null);

        assertThat(foundEntity).isNotNull();
        assertThat(foundEntity.getEmployeeId()).isEqualTo(employeeId);
        assertThat(foundEntity.getReason()).isEqualTo(reason);
    }
}
