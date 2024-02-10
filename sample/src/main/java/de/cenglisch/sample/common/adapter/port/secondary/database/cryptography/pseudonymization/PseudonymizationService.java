package de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.pseudonymization;

import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.CryptographyHelper;
import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.encryption.Encrypter;
import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.storage.Storage;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PseudonymizationService {
    private final CryptographyHelper cryptographyHelper;
    private final Storage storage;
    private final Encrypter encrypter;

    public PseudonymizationService(CryptographyHelper cryptographyHelper, Storage storage, Encrypter encrypter) {
        this.cryptographyHelper = cryptographyHelper;
        this.storage = storage;
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

    public void processField(Object entity, Field field) {
        if (!cryptographyHelper.fieldMustBePseudomized(field)) {
            return;
        }


        cryptographyHelper.determineFieldValue(entity, field).ifPresent(
                fieldValue -> cryptographyHelper.fieldSetValue(
                        entity,
                        field,
                        determinePseudomizedId(
                                cryptographyHelper.determineReferencedEntity(field),
                                fieldValue
                        )
                )
        );
    }

    public String determinePseudomizedId(Class<?> referencedEntity, String referenceId) {
        if (!storage.hasReference(referencedEntity, referenceId)) {
            storage.save(referencedEntity, referenceId, encrypter.encrypter(referenceId));
        }

        return storage.getByReferenceId(referencedEntity, referenceId)
                .orElseThrow(RuntimeException::new)
                .pseudoReferenceEntity()
                .getId();
    }
}
