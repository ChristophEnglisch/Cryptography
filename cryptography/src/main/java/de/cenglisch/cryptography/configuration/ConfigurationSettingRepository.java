package de.cenglisch.cryptography.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class ConfigurationSettingRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final PlatformTransactionManager transactionManager;

    public ConfigurationSettingRepository(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public ConfigurationSetting save(ConfigurationSetting configurationSetting) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute(status -> {
            entityManager.createNativeQuery("INSERT INTO configuration_setting (id, value) VALUES (?, ?)")
                    .setParameter(1, configurationSetting.getId())
                    .setParameter(2, configurationSetting.getValue())
                    .executeUpdate();
            return configurationSetting;
        });
    }

    public Optional<ConfigurationSetting> find(String key) {
        List<ConfigurationSetting> results = entityManager.createQuery("SELECT c FROM ConfigurationSetting c WHERE c.id = :id", ConfigurationSetting.class)
                .setParameter("id", key)
                .getResultList();
        return results.stream().findFirst();
    }
}
