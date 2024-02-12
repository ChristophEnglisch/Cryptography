package de.cenglisch.cryptography.dsgvo;

public enum Schutzbedarfsstufe {
    GERING,
    HOCH,
    SEHR_HOCH,
    EXTREM;


    public boolean istZuSchuetzen(Schutzbedarfsstufe other) {
        return this.ordinal() >= other.ordinal();
    }

}
