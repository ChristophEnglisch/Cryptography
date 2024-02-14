
In der Datenbank sollte gespeichert werden, ob sie verschlüsselt ist oder nicht. Dann muss geprüft werden ob die Anwendung im richtigen Modus gestartet wurde
Beim starten der Anwendung soll geprüft werden, ob es Annotations Conflicts gibt(einem Field sind die Annotations Pseudomize und DsgvoRelevant zugeordnet)
PseudonymizeCondition wieder einbauen

## Lasttests
    Nach implementierung des Lasttests soll ein Caching für die Encryption und Decryption entwickelt werden.
    Storage initalizer so implementieren, dass dieser nur begrentzt einträge speichert, damit hier der heap nicht volläuft

## After Lasttests
Aktuell ist es nur möglich string values zu ver und entschlüsseln

## Tests
Testfälle schreiben
Testen der Application mit deaktivierung der Pseudonymisierung

Verschlüsselung erneuern, wenn timestamp abgelaufen
alle möglichen datentypen für die reference id erlauben, bisher nur für string möglich
Loslösung als Libary
Doku
Ermöglichen Custom Encrypter und Decrypter zu implementieren und verwenden
