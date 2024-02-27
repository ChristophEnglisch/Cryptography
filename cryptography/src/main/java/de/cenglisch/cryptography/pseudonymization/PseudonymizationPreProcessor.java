package de.cenglisch.cryptography.pseudonymization;

import de.cenglisch.cryptography.ReflectionHelper;
import de.cenglisch.cryptography.encryption.Encrypter;
import de.cenglisch.cryptography.processor.PreProcessor;
import de.cenglisch.cryptography.processor.QueryProcessor;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class PseudonymizationPreProcessor implements PreProcessor, QueryProcessor {
    private final ReflectionHelper reflectionHelper;
    private final PseudonymizationStorage pseudonymizationStorage;
    private final Encrypter encrypter;

    public PseudonymizationPreProcessor(ReflectionHelper reflectionHelper, PseudonymizationStorage pseudonymizationStorage, Encrypter encrypter) {
        this.reflectionHelper = reflectionHelper;
        this.pseudonymizationStorage = pseudonymizationStorage;
        this.encrypter = encrypter;
    }

    public Object[] processQuery(Object[] args, MethodSignature methodSignature) {
        for (int i = 0; i < args.length; i++) {
            Optional<Annotation> optionalAnnotation = Arrays.stream(methodSignature.getMethod().getParameterAnnotations()[i])
                    .filter(Pseudonymize.class::isInstance)
                    .findFirst();

            if (optionalAnnotation.isEmpty()) {
                continue;
            }

            Pseudonymize pseudonymizeAnnotation = (Pseudonymize) optionalAnnotation.get();
            args[i] = determinePseudomizedId(pseudonymizeAnnotation.entity(), args[i].toString());
        }

        return args;
    }

    public void processEntity(Object entity) {
        Arrays.stream(entity.getClass().getDeclaredFields())
                .forEach(field -> processField(entity, field));
    }

    private void processField(Object entity, Field field) {
        if (!reflectionHelper.fieldMustBePseudomized(field)) {
            return;
        }


        reflectionHelper.determineFieldValue(entity, field).ifPresent(
                fieldValue -> reflectionHelper.fieldSetValue(
                        entity,
                        field,
                        determinePseudomizedId(
                                reflectionHelper.determineReferencedEntity(field),
                                fieldValue
                        )
                )
        );
    }

    private String determinePseudomizedId(Class<?> referencedEntity, String referenceId) {
        if (!pseudonymizationStorage.hasReference(referencedEntity, referenceId)) {
            pseudonymizationStorage.save(referencedEntity, referenceId, encrypter.encrypter(referenceId));
        }

        return pseudonymizationStorage.getByReferenceId(referencedEntity, referenceId)
                .orElseThrow(RuntimeException::new)
                .pseudoReferenceEntity()
                .getId();
    }
}
