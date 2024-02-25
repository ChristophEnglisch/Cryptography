package de.cenglisch.cryptography.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationVerifier {

    private final ConfigurationSettingRepository repository;

    @Value("${cryptography.pseudonymize.active}")
    private boolean pseudonymizeActive;

    @Value("${cryptography.gdpr-encryption.active}")
    private boolean gdprEncryptionActive;

    public ConfigurationVerifier(ConfigurationSettingRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void verifyConfiguration() {
        verifySetting("cryptography.pseudonymize.active", pseudonymizeActive);
        verifySetting("cryptography.gdpr-encryption.active", gdprEncryptionActive);
    }

    private void verifySetting(String key, boolean expectedValue) {
        ConfigurationSetting setting = repository.find(key)
                .orElseGet(() -> repository.save(new ConfigurationSetting(key, expectedValue)));

        if (setting.getValue() != expectedValue) {
            throw new IllegalStateException("Konfigurationswert stimmt nicht überein: " + key);
        }
    }
}
