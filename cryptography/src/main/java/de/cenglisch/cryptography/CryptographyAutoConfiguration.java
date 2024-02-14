package de.cenglisch.cryptography;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "de.cenglisch.cryptography")
public class CryptographyAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptographyAutoConfiguration.class);

    @Value("${cryptography.dsgvo-encryption.active}")
    private boolean dsgvoEncryptionIsActive;

    @Value("${cryptography.pseudonymize.active}")
    private boolean pseudonymizationIsActive;

    @PostConstruct
    public void init() {
        LOGGER.info("DSGVO-ENCRYPTION: {}, PSEUDONYMIZATION: {}", dsgvoEncryptionIsActive, pseudonymizationIsActive);
    }
}
