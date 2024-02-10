package de.cenglisch.cryptography.storage;

import de.cenglisch.cryptography.decryption.Decrypter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class StorageInitalizer {

    private final PseudonymizeJpaRepository pseudonymizeJpaRepository;
    private final Decrypter decrypter;

    public StorageInitalizer(PseudonymizeJpaRepository pseudonymizeJpaRepository, Decrypter decrypter) {
        this.pseudonymizeJpaRepository = pseudonymizeJpaRepository;
        this.decrypter = decrypter;
    }

    public Collection<PseudoReferenceDto> initalize(Class<?> entityClass) {
        return pseudonymizeJpaRepository.findAll(entityClass)
                .stream()
                .map(entity -> new PseudoReferenceDto(decrypter.decrypt(entity.getPseudonymizedReference()), entity))
                .collect(Collectors.toList());
    }

}
