package de.cenglisch.cryptography.gdpr;

public enum ProtectionLevel {
    LOW,
    HIGH,
    VERY_HIGH,
    EXTREME;


    public boolean needsProtection(ProtectionLevel other) {
        return this.ordinal() >= other.ordinal();
    }

}
