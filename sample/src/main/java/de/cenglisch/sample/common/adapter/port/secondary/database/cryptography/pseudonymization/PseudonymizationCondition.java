package de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.pseudonymization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class PseudonymizationCondition implements Condition {

    @Value("${pseudonymize.active}")
    private boolean isActive;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return isActive;
    }
}