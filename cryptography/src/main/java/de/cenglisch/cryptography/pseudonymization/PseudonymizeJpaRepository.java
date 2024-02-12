package de.cenglisch.cryptography.pseudonymization;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;

@Component
public class PseudonymizeJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public PseudoReferenceEntity save(Class<?> entityClass, String cryptedValue) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute(status -> {
            String tableName = determineTableNameByEntityClass(entityClass);
            PseudoReferenceEntity entity = new PseudoReferenceEntity(cryptedValue);
            entityManager.createNativeQuery("INSERT INTO " + tableName + " (id, pseudonymized_reference, crypted_timestamp) VALUES (?, ?, ?)")
                    .setParameter(1, entity.getId())
                    .setParameter(2, entity.getPseudonymizedReference())
                    .setParameter(3, entity.getCryptedTimestamp())
                    .executeUpdate();
            return entity;
        });
    }

    public Collection<PseudoReferenceEntity> findAll(Class<?> entityClass) {
        String tableName = determineTableNameByEntityClass(entityClass);
        Query query = entityManager.createNativeQuery("SELECT * FROM " + tableName, PseudoReferenceEntity.class);
        return (Collection<PseudoReferenceEntity>) query.getResultList();
    }

    private String determineTableNameByEntityClass(Class<?> entityClass) {
        return entityClass.getAnnotation(Table.class).name() + "_ref_pseudo";
    }
}
