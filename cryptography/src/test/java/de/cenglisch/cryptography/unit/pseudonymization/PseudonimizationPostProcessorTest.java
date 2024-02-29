package de.cenglisch.cryptography.unit.pseudonymization;

import de.cenglisch.cryptography.ReflectionHelper;
import de.cenglisch.cryptography.environment.AbsencesEntity;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceDto;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceEntity;
import de.cenglisch.cryptography.pseudonymization.PseudonimizationPostProcessor;
import de.cenglisch.cryptography.pseudonymization.PseudonymizationStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PseudonimizationPostProcessorTest {

  @Test
  void testProcessEntity() {
    //Given
    PseudonymizationStorage pseudonymizationStorage = Mockito.mock(PseudonymizationStorage.class);
    ReflectionHelper reflectionHelper = new ReflectionHelper();
    PseudonimizationPostProcessor pseudonimizationPostProcessor = new PseudonimizationPostProcessor(
      reflectionHelper, pseudonymizationStorage
    );

    String id = UUID.randomUUID().toString();
    String reason = "reason";

    AbsencesEntity absencesEntity = new AbsencesEntity();
    absencesEntity.setId(id);
    absencesEntity.setEmployeeId(UUID.randomUUID().toString());
    absencesEntity.setReason(reason);

    when(pseudonymizationStorage.getByPseudoId(any(), any())).thenReturn(
      Optional.of(new PseudoReferenceDto("decrypted id", new PseudoReferenceEntity()))
    );

    //When
    pseudonimizationPostProcessor.processEntity(absencesEntity);

    //Then
    assertThat(absencesEntity.getEmployeeId()).isEqualTo("decrypted id");
    assertThat(absencesEntity.getReason()).isEqualTo(reason);
    assertThat(absencesEntity.getId()).isEqualTo(id);
  }


}