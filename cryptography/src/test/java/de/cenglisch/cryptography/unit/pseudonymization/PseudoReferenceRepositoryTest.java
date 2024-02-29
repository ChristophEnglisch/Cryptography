package de.cenglisch.cryptography.unit.pseudonymization;

import de.cenglisch.cryptography.environment.AbsencesEntity;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceEntity;
import de.cenglisch.cryptography.pseudonymization.PseudoReferenceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PseudoReferenceRepositoryTest {

  @InjectMocks
  private PseudoReferenceRepository pseudoReferenceRepository;

  @Mock
  private EntityManager entityManager;

  @Mock
  private PlatformTransactionManager transactionManager;

  @Mock
  private TransactionStatus transactionStatus;

  @Mock
  private Query query;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSave() {
    when(entityManager.createNativeQuery(anyString())).thenReturn(query);
    when(query.setParameter(anyInt(), any())).thenReturn(query);

    pseudoReferenceRepository.save(AbsencesEntity.class, "cryptedValue");

    verify(entityManager, times(1)).createNativeQuery(anyString());
    verify(query, times(3)).setParameter(anyInt(), any());
  }

  @Test
  void testFindAll() {
    //Given
    AbsencesEntity entity1 = new AbsencesEntity();
    AbsencesEntity entity2 = new AbsencesEntity();
    when(entityManager.createNativeQuery(anyString(), eq(PseudoReferenceEntity.class))).thenReturn(query);
    when(query.getResultList()).thenReturn(List.of(entity1, entity2));

    //When
    Collection<PseudoReferenceEntity> result = pseudoReferenceRepository.findAll(AbsencesEntity.class);

    //Then
    verify(entityManager, times(1)).createNativeQuery(anyString(), eq(PseudoReferenceEntity.class));
    assertEquals(2, result.size());
  }

}
