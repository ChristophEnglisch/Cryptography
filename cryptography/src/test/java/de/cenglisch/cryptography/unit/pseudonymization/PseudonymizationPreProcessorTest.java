package de.cenglisch.cryptography.unit.pseudonymization;

import de.cenglisch.cryptography.ReflectionHelper;
import de.cenglisch.cryptography.encryption.Encrypter;
import de.cenglisch.cryptography.environment.AbsencesEntity;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceDto;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceEntity;
import de.cenglisch.cryptography.pseudonymization.PseudonymizationPreProcessor;
import de.cenglisch.cryptography.pseudonymization.PseudonymizationStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PseudonymizationPreProcessorTest {


  @Test
  void processEntity_whenPseudonymizationStorageHasReference() {
    //Given
    ReflectionHelper reflectionHelper = new ReflectionHelper();
    PseudonymizationStorage pseudonymizationStorage = Mockito.mock(PseudonymizationStorage.class);
    Encrypter encrypter = Mockito.mock(Encrypter.class);

    PseudonymizationPreProcessor pseudonymizationPreProcessor = new PseudonymizationPreProcessor(
      reflectionHelper, pseudonymizationStorage, encrypter
    );

    String id = UUID.randomUUID().toString();
    String reason = "reason";

    AbsencesEntity absencesEntity = new AbsencesEntity();
    absencesEntity.setId(id);
    absencesEntity.setEmployeeId(UUID.randomUUID().toString());
    absencesEntity.setReason(reason);

    PseudoReferenceDto pseudoReferenceDto = new PseudoReferenceDto("decrypted id", new PseudoReferenceEntity("1"));

    when(pseudonymizationStorage.hasReference(any(), any())).thenReturn(true);
    when(pseudonymizationStorage.getByReferenceId(any(), any())).thenReturn(Optional.of(pseudoReferenceDto));

    //When
    pseudonymizationPreProcessor.processEntity(absencesEntity);

    //Then
    assertThat(absencesEntity.getEmployeeId()).isEqualTo(pseudoReferenceDto.pseudoReferenceEntity().getId());
    assertThat(absencesEntity.getReason()).isEqualTo(reason);
    assertThat(absencesEntity.getId()).isEqualTo(id);
  }

  @Test
  void processEntity_whenPseudonymizationStorageNotHasReference() {
    //Given
    ReflectionHelper reflectionHelper = new ReflectionHelper();
    PseudonymizationStorage pseudonymizationStorage = Mockito.mock(PseudonymizationStorage.class);
    Encrypter encrypter = Mockito.mock(Encrypter.class);

    PseudonymizationPreProcessor pseudonymizationPreProcessor = new PseudonymizationPreProcessor(
      reflectionHelper, pseudonymizationStorage, encrypter
    );

    String id = UUID.randomUUID().toString();
    String employeeId = UUID.randomUUID().toString();
    String reason = "reason";

    AbsencesEntity absencesEntity = new AbsencesEntity();
    absencesEntity.setId(id);
    absencesEntity.setEmployeeId(employeeId);
    absencesEntity.setReason(reason);

    PseudoReferenceDto pseudoReferenceDto = new PseudoReferenceDto("decrypted id", new PseudoReferenceEntity("1"));

    when(pseudonymizationStorage.hasReference(any(), any())).thenReturn(false);
    when(pseudonymizationStorage.getByReferenceId(any(), any())).thenReturn(Optional.of(pseudoReferenceDto));

    //When
    pseudonymizationPreProcessor.processEntity(absencesEntity);

    //Then
    assertThat(absencesEntity.getEmployeeId()).isEqualTo(pseudoReferenceDto.pseudoReferenceEntity().getId());
    assertThat(absencesEntity.getReason()).isEqualTo(reason);
    assertThat(absencesEntity.getId()).isEqualTo(id);
    verify(pseudonymizationStorage).save(any(), any(), any());
    verify(encrypter).encrypter(employeeId);
  }

}

