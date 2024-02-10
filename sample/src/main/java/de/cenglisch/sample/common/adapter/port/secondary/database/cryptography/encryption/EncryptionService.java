package de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.encryption;

import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.CryptographyHelper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class EncryptionService {

    private final CryptographyHelper cryptographyHelper;
    private final Encrypter encrypter;

    public EncryptionService(CryptographyHelper cryptographyHelper, Encrypter encrypter) {
        this.cryptographyHelper = cryptographyHelper;
        this.encrypter = encrypter;
    }

    public void processField(Object entity, Field field) {
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
