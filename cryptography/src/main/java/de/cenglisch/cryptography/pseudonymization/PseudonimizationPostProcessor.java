package de.cenglisch.cryptography.pseudonymization;

import de.cenglisch.cryptography.ReflectionHelper;
import de.cenglisch.cryptography.processor.PostProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;

public class PseudonimizationPostProcessor implements PostProcessor {
    private final ReflectionHelper reflectionHelper;
    private final PseudonymizationStorage pseudonymizationStorage;

    public PseudonimizationPostProcessor(ReflectionHelper reflectionHelper, PseudonymizationStorage pseudonymizationStorage) {
        this.reflectionHelper = reflectionHelper;
        this.pseudonymizationStorage = pseudonymizationStorage;
    }

    public void processEntity(Object entity) {
        Arrays.stream(entity.getClass().getDeclaredFields())
                .forEach(field -> processField(entity, field));
    }

    private void processField(Object entity, Field field) {
        if (!reflectionHelper.fieldMustBePseudomized(field)) {
            return;
        }

        Class<?> referencedEntity = reflectionHelper.determineReferencedEntity(field);

        reflectionHelper.determineFieldValue(entity, field).ifPresent(
                pseudomizedId -> {
                    String referenceId = pseudonymizationStorage.getByPseudoId(referencedEntity, pseudomizedId)
                            .orElseThrow(RuntimeException::new)
                            .decryptedReferenceId();

                    reflectionHelper.fieldSetValue(entity, field, referenceId);
                }
        );
    }
}
