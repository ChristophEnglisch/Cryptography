package de.cenglisch.cryptography.unit.pseudonymization;

import de.cenglisch.cryptography.decryption.Decrypter;
import de.cenglisch.cryptography.environment.AbsencesEntity;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceDto;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceEntity;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceRepository;
import de.cenglisch.cryptography.pseudonymization.PseudonymizationStorageInitalizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PseudonymizationStorageInitalizerTest {

  @Mock
  private PseudoReferenceRepository pseudoReferenceRepository;

  @Mock
  private Decrypter decrypter;

  @InjectMocks
  private PseudonymizationStorageInitalizer pseudonymizationStorageInitalizer;

  @Test
  void initalize() {
    //Given
    String decryptedReferenceId = "decryptedReferenceId";
    String pseudonymizedReference = "pseudonymizedReference";
    var pseudoReferenceEntity = new PseudoReferenceEntity(pseudonymizedReference);
    when(pseudoReferenceRepository.findAll(AbsencesEntity.class)).thenReturn(List.of(pseudoReferenceEntity));
    when(decrypter.decrypt(pseudonymizedReference)).thenReturn(decryptedReferenceId);

    //When
    var result = pseudonymizationStorageInitalizer.initalize(AbsencesEntity.class);

    //Then
    assertThat(result).contains(new PseudoReferenceDto(decryptedReferenceId, pseudoReferenceEntity));
  }

}
