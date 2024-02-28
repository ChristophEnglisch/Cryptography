package de.cenglisch.cryptography.unit.pseudonymization;

import de.cenglisch.cryptography.environment.AbsencesEntity;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceDto;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceEntity;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceRepository;
import de.cenglisch.cryptography.pseudonymization.PseudonymizationStorage;
import de.cenglisch.cryptography.pseudonymization.PseudonymizationStorageInitalizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PseudonymizationStorageTest {


  @Mock
  private PseudoReferenceRepository pseudoReferenceRepository;

  @Mock
  private PseudonymizationStorageInitalizer pseudonymizationStorageInitalizer;

  @InjectMocks
  private PseudonymizationStorage pseudonymizationStorage;

  @Test
  void save() {
    //Given
    Collection<PseudoReferenceDto> emptyCollection = new LinkedList<>();
    String referenceId = UUID.randomUUID().toString();
    String cryptedReferenceId = UUID.randomUUID().toString();

    when(pseudonymizationStorageInitalizer.initalize(AbsencesEntity.class)).thenReturn(emptyCollection);

    //When
    pseudonymizationStorage.save(AbsencesEntity.class, referenceId, cryptedReferenceId);

    //Then
    assertThat(pseudonymizationStorage.hasReference(AbsencesEntity.class, referenceId)).isTrue();
    verify(pseudoReferenceRepository).save(AbsencesEntity.class, cryptedReferenceId);
  }

  @Test
  void hasReference_returnsFalse_whenNotFound() {
    //Given
    Collection<PseudoReferenceDto> emptyCollection = new LinkedList<>();
    String referenceId = UUID.randomUUID().toString();

    when(pseudonymizationStorageInitalizer.initalize(AbsencesEntity.class)).thenReturn(emptyCollection);

    //When
    var result = pseudonymizationStorage.hasReference(AbsencesEntity.class, referenceId);

    //Then
    assertThat(result).isFalse();
  }

  @Test
  void hasReference_returnsTrue_whenFound() {
    //Given
    String referenceId = UUID.randomUUID().toString();
    Collection<PseudoReferenceDto> pseudoReferenceDtos = new LinkedList<>();
    pseudoReferenceDtos.add(new PseudoReferenceDto(referenceId, new PseudoReferenceEntity()));

    when(pseudonymizationStorageInitalizer.initalize(AbsencesEntity.class)).thenReturn(pseudoReferenceDtos);

    //When
    var result = pseudonymizationStorage.hasReference(AbsencesEntity.class, referenceId);

    //Then
    assertThat(result).isTrue();
  }

  @Test
  void getByReferenceId_returnsEmpty_whenNotFound() {
    //Given
    Collection<PseudoReferenceDto> emptyCollection = new LinkedList<>();
    String referenceId = UUID.randomUUID().toString();

    when(pseudonymizationStorageInitalizer.initalize(AbsencesEntity.class)).thenReturn(emptyCollection);

    //When
    var result = pseudonymizationStorage.getByReferenceId(AbsencesEntity.class, referenceId);

    //Then
    assertThat(result).isEmpty();
  }

  @Test
  void getByReferenceId_returnsPseudoReferenceDto_whenFound() {
    //Given
    String referenceId = UUID.randomUUID().toString();
    Collection<PseudoReferenceDto> pseudoReferenceDtos = new LinkedList<>();
    PseudoReferenceEntity pseudoReferenceEntity = new PseudoReferenceEntity();
    pseudoReferenceDtos.add(new PseudoReferenceDto(referenceId, pseudoReferenceEntity));

    when(pseudonymizationStorageInitalizer.initalize(AbsencesEntity.class)).thenReturn(pseudoReferenceDtos);

    //When
    var result = pseudonymizationStorage.getByReferenceId(AbsencesEntity.class, referenceId);

    //Then
    assertThat(result).isPresent();
    assertThat(result.get().decryptedReferenceId()).isEqualTo(referenceId);
    assertThat(result.get().pseudoReferenceEntity()).isEqualTo(pseudoReferenceEntity);
  }

  @Test
  void getByPseudoId_returnsEmpty_whenNotFound() {
    //Given
    Collection<PseudoReferenceDto> emptyCollection = new LinkedList<>();
    String pseudoId = UUID.randomUUID().toString();

    when(pseudonymizationStorageInitalizer.initalize(AbsencesEntity.class)).thenReturn(emptyCollection);

    //When
    var result = pseudonymizationStorage.getByPseudoId(AbsencesEntity.class, pseudoId);

    //Then
    assertThat(result).isEmpty();
  }

  @Test
  void getByPseudoId_returnsPseudoReferenceDto_whenFound() {
    //Given
    Collection<PseudoReferenceDto> pseudoReferenceDtos = new LinkedList<>();
    PseudoReferenceEntity pseudoReferenceEntity = new PseudoReferenceEntity(UUID.randomUUID().toString());
    pseudoReferenceDtos.add(new PseudoReferenceDto(UUID.randomUUID().toString(), pseudoReferenceEntity));

    when(pseudonymizationStorageInitalizer.initalize(AbsencesEntity.class)).thenReturn(pseudoReferenceDtos);

    //When
    var result = pseudonymizationStorage.getByPseudoId(AbsencesEntity.class, pseudoReferenceEntity.getId());

    //Then
    assertThat(result).isPresent();
  }

}
