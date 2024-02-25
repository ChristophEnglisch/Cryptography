package de.cenglisch.cryptography.pseudonymization;

import de.cenglisch.cryptography.decryption.Decrypter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class PseudonymizationStorageInitalizer {

    private final PseudoReferenceRepository pseudoReferenceRepository;
    private final Decrypter decrypter;

    public PseudonymizationStorageInitalizer(PseudoReferenceRepository pseudoReferenceRepository, Decrypter decrypter) {
        this.pseudoReferenceRepository = pseudoReferenceRepository;
        this.decrypter = decrypter;
    }

    public Collection<PseudoReferenceDto> initalize(Class<?> entityClass) {
        return pseudoReferenceRepository.findAll(entityClass)
                .stream()
                .map(entity -> new PseudoReferenceDto(decrypter.decrypt(entity.getPseudonymizedReference()), entity))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
