package de.cenglisch.cryptography;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "de.cenglisch.cryptography")
@EntityScan(basePackages = "de.cenglisch.cryptography.configuration")
public class CryptographyAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptographyAutoConfiguration.class);

    @Value("${cryptography.gdpr-encryption.active}")
    private boolean gdprEncryptionIsActive;

    @Value("${cryptography.pseudonymize.active}")
    private boolean pseudonymizationIsActive;

    @PostConstruct
    public void init() {
        LOGGER.info("gdpr-encryption: {}, PSEUDONYMIZATION: {}", gdprEncryptionIsActive, pseudonymizationIsActive);
    }
}
