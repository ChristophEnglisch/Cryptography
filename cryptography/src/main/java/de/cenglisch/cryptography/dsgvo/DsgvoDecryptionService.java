package de.cenglisch.cryptography.dsgvo;

import de.cenglisch.cryptography.CryptographyHelper;
import de.cenglisch.cryptography.processor.PostProcessor;
import de.cenglisch.cryptography.decryption.Decrypter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DsgvoDecryptionService implements PostProcessor {
    private final CryptographyHelper cryptographyHelper;
    private final Decrypter decrypter;

    public DsgvoDecryptionService(CryptographyHelper cryptographyHelper, Decrypter decrypter) {
        this.cryptographyHelper = cryptographyHelper;
        this.decrypter = decrypter;
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
                fieldValue -> cryptographyHelper.fieldSetValue(entity, field, decrypter.decrypt(fieldValue))
        );
    }
}
