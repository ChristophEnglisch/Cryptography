package de.cenglisch.cryptography.pseudonymization;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PseudonymizationStorage {

    private final PseudoReferenceRepository pseudoReferenceRepository;
    private final PseudonymizationStorageInitalizer pseudonymizationStorageInitalizer;
    private final Map<Class<?>, Collection<PseudoReferenceDto>> store = new ConcurrentHashMap<>();

    public PseudonymizationStorage(PseudoReferenceRepository pseudoReferenceRepository, PseudonymizationStorageInitalizer pseudonymizationStorageInitalizer) {
        this.pseudoReferenceRepository = pseudoReferenceRepository;
        this.pseudonymizationStorageInitalizer = pseudonymizationStorageInitalizer;
    }

    public void save(Class<?> referencedEntity, String referenceId, String cryptedReferenceId) {
        fillStorage(referencedEntity);

        store.get(referencedEntity).add(new PseudoReferenceDto(
                referenceId,
                pseudoReferenceRepository.save(referencedEntity, cryptedReferenceId)
        ));
    }

    public boolean hasReference(Class<?> referencedEntity, String referenceId) {
        fillStorage(referencedEntity);

        return store.get(referencedEntity)
                .stream()
                .anyMatch(pseudoReferenceDto -> pseudoReferenceDto.decryptedReferenceId().equals(referenceId));
    }

    private void fillStorage(Class<?> referencedEntity) {
        store.computeIfAbsent(referencedEntity, pseudonymizationStorageInitalizer::initalize);
    }

    public Optional<PseudoReferenceDto> getByReferenceId(Class<?> referencedEntity, String referenceId) {
        fillStorage(referencedEntity);

        return store.get(referencedEntity).stream().filter(pseudoReferenceDto -> pseudoReferenceDto.decryptedReferenceId().equals(referenceId)).findFirst();
    }

    public Optional<PseudoReferenceDto> getByPseudoId(Class<?> referencedEntity, String pseudoId) {
        fillStorage(referencedEntity);

        return store.get(referencedEntity).stream().filter(pseudoReferenceDto -> pseudoReferenceDto.pseudoReferenceEntity().getId().equals(pseudoId)).findFirst();
    }
}
