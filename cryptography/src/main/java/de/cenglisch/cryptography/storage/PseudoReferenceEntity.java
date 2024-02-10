package de.cenglisch.cryptography.storage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
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

    public String getId() {
        return id;
    }

    public String getPseudonymizedReference() {
        return pseudonymizedReference;
    }

    public LocalDateTime getCryptedTimestamp() {
        return cryptedTimestamp;
    }
}
