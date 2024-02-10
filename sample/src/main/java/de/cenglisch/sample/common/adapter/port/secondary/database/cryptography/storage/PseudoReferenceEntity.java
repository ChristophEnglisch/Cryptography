package de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.storage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class PseudoReferenceEntity {
    @Id
    private String id;

    private String pseudonymizedReference;

    private LocalDateTime cryptedTimestamp;

    public PseudoReferenceEntity() {
    }

    public PseudoReferenceEntity(String pseudonymizedReference) {
        this.id = UUID.randomUUID().toString();
        this.pseudonymizedReference = pseudonymizedReference;
        this.cryptedTimestamp = LocalDateTime.now();
    }
}
