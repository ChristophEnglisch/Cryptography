package de.cenglisch.cryptography.gdpr;

import de.cenglisch.cryptography.ReflectionHelper;
import de.cenglisch.cryptography.decryption.Decrypter;
import de.cenglisch.cryptography.processor.PostProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;

public class GdprPostProcessor implements PostProcessor {
    private final ReflectionHelper reflectionHelper;
    private final Decrypter decrypter;

    public GdprPostProcessor(ReflectionHelper reflectionHelper, Decrypter decrypter) {
        this.reflectionHelper = reflectionHelper;
        this.decrypter = decrypter;
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
                fieldValue -> reflectionHelper.fieldSetValue(entity, field, decrypter.decrypt(fieldValue))
        );
    }
}
