package de.cenglisch.cryptography;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CryptographyCondition implements Condition {

    @Value("${cryptography.dsgvo-encryption.active}")
    private boolean dsgvoEncryptionIsActive;

    @Value("${cryptography.pseudonymize.active}")
    private boolean pseudonymizationIsActive;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return dsgvoEncryptionIsActive || pseudonymizationIsActive;
    }
}
