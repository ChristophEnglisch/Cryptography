package de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.pseudonymization;

import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.CryptographyHelper;
import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.storage.Storage;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;

@Service
public class DissolvePseudonimizationService {
    private final CryptographyHelper cryptographyHelper;
    private final Storage storage;

    public DissolvePseudonimizationService(CryptographyHelper cryptographyHelper, Storage storage) {
        this.cryptographyHelper = cryptographyHelper;
        this.storage = storage;
    }

    public void processEntity(Object entity) {
        Arrays.stream(entity.getClass().getDeclaredFields())
                .forEach(field -> processField(entity, field));
    }

    private void processField(Object entity, Field field) {
        if (!cryptographyHelper.fieldMustBePseudomized(field)) {
            return;
        }

        Class<?> referencedEntity = cryptographyHelper.determineReferencedEntity(field);

        cryptographyHelper.determineFieldValue(entity, field).ifPresent(
                pseudomizedId -> {
                    String referenceId = storage.getByPseudoId(referencedEntity, pseudomizedId)
                            .orElseThrow(RuntimeException::new)
                            .decryptedReferenceId();

                    cryptographyHelper.fieldSetValue(entity, field, referenceId);
                }
        );
    }
}
