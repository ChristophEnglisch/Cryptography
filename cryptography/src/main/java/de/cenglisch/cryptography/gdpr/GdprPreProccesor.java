package de.cenglisch.cryptography.gdpr;

import de.cenglisch.cryptography.ReflectionHelper;
import de.cenglisch.cryptography.processor.PreProcessor;
import de.cenglisch.cryptography.encryption.Encrypter;

import java.lang.reflect.Field;
import java.util.Arrays;

public class GdprPreProccesor implements PreProcessor {

    private final ReflectionHelper reflectionHelper;
    private final Encrypter encrypter;

    public GdprPreProccesor(ReflectionHelper reflectionHelper, Encrypter encrypter) {
        this.reflectionHelper = reflectionHelper;
        this.encrypter = encrypter;
    }

    public void processEntity(Object entity) {
        Arrays.stream(entity.getClass().getDeclaredFields())
                .forEach(field -> processField(entity, field));
    }

    private void processField(Object entity, Field field) {
        if (!reflectionHelper.fieldIsGdprRelevant(field)) {
            return;
        }

        reflectionHelper.determineFieldValue(entity, field).ifPresent(
          fieldValue -> reflectionHelper.fieldSetValue(
            entity,
            field,
            encrypter.encrypter(fieldValue)
          )
        );
    }
}
