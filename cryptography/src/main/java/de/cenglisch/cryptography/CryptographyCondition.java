package de.cenglisch.cryptography;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CryptographyCondition implements Condition {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptographyCondition.class);

    @Value("${cryptography.dsgvo-encryption.active}")
    private boolean dsgvoEncryptionIsActive;

    @Value("${cryptography.pseudonymize.active}")
    private boolean pseudonymizationIsActive;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        LOGGER.info("DSGVO-ENCRYPTION: {}, PSEUDONYMIZATION: {}", dsgvoEncryptionIsActive, pseudonymizationIsActive);
        return dsgvoEncryptionIsActive || pseudonymizationIsActive;
    }
}
