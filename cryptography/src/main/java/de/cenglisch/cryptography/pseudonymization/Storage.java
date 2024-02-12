package de.cenglisch.cryptography.pseudonymization;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Storage {

    private final PseudonymizeJpaRepository pseudonymizeJpaRepository;
    private final StorageInitalizer storageInitalizer;
    private final Map<Class<?>, Collection<PseudoReferenceDto>> storage = new ConcurrentHashMap<>();

    public Storage(PseudonymizeJpaRepository pseudonymizeJpaRepository, StorageInitalizer storageInitalizer) {
        this.pseudonymizeJpaRepository = pseudonymizeJpaRepository;
        this.storageInitalizer = storageInitalizer;
    }

    public void save(Class<?> referencedEntity, String referenceId, String cryptedReferenceId) {
        fillStorage(referencedEntity);

        storage.get(referencedEntity).add(new PseudoReferenceDto(
                referenceId,
                pseudonymizeJpaRepository.save(referencedEntity, cryptedReferenceId)
        ));
    }

    public boolean hasReference(Class<?> referencedEntity, String referenceId) {
        fillStorage(referencedEntity);

        return storage.get(referencedEntity)
                .stream()
                .anyMatch(pseudoReferenceDto -> pseudoReferenceDto.decryptedReferenceId().equals(referenceId));
    }

    private void fillStorage(Class<?> referencedEntity) {
        storage.computeIfAbsent(referencedEntity, storageInitalizer::initalize);
    }

    public Optional<PseudoReferenceDto> getByReferenceId(Class<?> referencedEntity, String referenceId) {
        fillStorage(referencedEntity);

        return storage.get(referencedEntity).stream().filter(pseudoReferenceDto -> pseudoReferenceDto.decryptedReferenceId().equals(referenceId)).findFirst();
    }

    public Optional<PseudoReferenceDto> getByPseudoId(Class<?> referencedEntity, String pseudoId) {
        fillStorage(referencedEntity);

        return storage.get(referencedEntity).stream().filter(pseudoReferenceDto -> pseudoReferenceDto.pseudoReferenceEntity().getId().equals(pseudoId)).findFirst();
    }
}
