package de.cenglisch.cryptography.dsgvo;

import de.cenglisch.cryptography.CryptographyHelper;
import de.cenglisch.cryptography.encryption.Encrypter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class DsgvoEncryptionService {

    private final CryptographyHelper cryptographyHelper;
    private final Encrypter encrypter;

    public DsgvoEncryptionService(CryptographyHelper cryptographyHelper, Encrypter encrypter) {
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
