# TODOs
## Implementierungen
* Datentypunabhängige Pseudonimiserung
* Datentypunabhängige Ver- und Entschlüsselung
* Caching für Encryption und Decryption implementieren
* Storage Einträge limitieren (`PseudonymizationStorageInitalizer` so implementieren, dass dieser nur begrenzt einträge speichert, damit hier der heap nicht vollläuft)

## Tests
* Testen der Application mit deaktivierung der Pseudonymisierung
* Unittests
* Integrationstests

# Nice To Have
* Re-keying implementieren
* Ermöglichen Custom Encrypter und Decrypter zu implementieren und verwenden
* Pseudo-Referenzhandling für `@OneToMany` & `@ManyToMany`


# Known issues
* Entity Detection funktioniert nicht richtig: `@EntityScans` müssen definiert werden

---

# Doku

# Verschlüsselung
## Unterstütze Datentypen
Bisher sind folgende Datentypen unterstützt.
* String

Damit Columns richtig verschlüsselt werden können, müssen die Datenbankspalten den Typ `varchar` haben.

## Verschlüsselungsmethoden und Datenbankspaltenlänge
Durch die Verschlüsselung vergrößert sich der zu speichernde Wert. Das resultat der maximalen gesamtlänge kann aus folgender Formel kalkuliert werden.
```
n = Zeichenlänge des zu verschlüsselnden Werts
Maximale Länge = (n + 12 + 16) * (4/3)
```
Bei einer eingabe von 50 Zeichen resultieren daraus maximal 104 Zeichen
`104 = (50 + 12 + 16) * (4/3)`

# Pseudonymisierung

## One to Many

## Many to Many

## Unterstütze Datentypen
Bisher sind folgende Datentypen unterstützt.
* String

