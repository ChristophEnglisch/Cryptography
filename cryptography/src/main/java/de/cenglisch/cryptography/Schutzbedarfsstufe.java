package de.cenglisch.cryptography;

public enum Schutzbedarfsstufe {
    GERING,
    HOCH,
    SEHR_HOCH,
    EXTREM;


    public boolean istZuSchuetzen(Schutzbedarfsstufe other) {
        return this.ordinal() >= other.ordinal();
    }

}
