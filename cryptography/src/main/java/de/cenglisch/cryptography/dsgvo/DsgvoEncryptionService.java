package de.cenglisch.cryptography.dsgvo;

import de.cenglisch.cryptography.CryptographyHelper;
import de.cenglisch.cryptography.processor.PreProcessor;
import de.cenglisch.cryptography.encryption.Encrypter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DsgvoEncryptionService implements PreProcessor {

    private final CryptographyHelper cryptographyHelper;
    private final Encrypter encrypter;

    public DsgvoEncryptionService(CryptographyHelper cryptographyHelper, Encrypter encrypter) {
        this.cryptographyHelper = cryptographyHelper;
        this.encrypter = encrypter;
    }

    public void processEntity(Object entity) {
        Arrays.stream(entity.getClass().getDeclaredFields())
                .forEach(field -> processField(entity, field));
    }

    private void processField(Object entity, Field field) {
        if (!cryptographyHelper.fieldIsDsgvoRelevant(field)) {
            return;
        }

        cryptographyHelper.determineFieldValue(entity, field).ifPresent(
          fieldValue -> cryptographyHelper.fieldSetValue(
            entity,
            field,
            encrypter.encrypter(fieldValue)
          )
        );
    }
}
