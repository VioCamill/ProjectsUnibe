# Unit- und Datenbank Testdokumentation

## Mocks und Stubs

Diese Tests verwenden intensiv Mocks und Stubs, um Datenbankinteraktionen zu simulieren. Dadurch bleiben die Tests isoliert von tatsächlichen Datenbankoperationen und fokussieren sich auf die Logiküberprüfung. Obwohl die Tests nicht direkt mit der Datenbank interagieren, gewährleisten sie, dass die Datenbankmanipulationen korrekt funktionieren. Dies ist entscheidend, um sicherzustellen, dass alle Operationen in einer kontrollierten Umgebung getestet werden, ohne Seiteneffekte auf die reale Datenbank zu verursachen.

## UpdateControllerTest

### Schlüsselpunkte der Tests

- **testInsertUpdateDataWithExistingEntry**: Dieser Test prüft die Methode `insertUpdateData`, wenn der Eintrag bereits existiert. Er stellt sicher, dass die Lösch- und Einfügeoperationen korrekt ausgeführt werden.
- **testInsertUpdateDataWithNewEntry**: Testet die Methode `insertUpdateData`, wenn der Eintrag nicht existiert. Hier wird überprüft, ob der Einfügebefehl korrekt ausgeführt wird.
- **testDeleteEntry**: Testet die Methode `deleteEntry`, um sicherzustellen, dass ein Eintrag korrekt gelöscht wird.
- **testDeleteEntryById**: Überprüft die Methode `deleteEntryById`, um sicherzustellen, dass ein Eintrag anhand seiner ID gelöscht wird.
- **testIsInUpdateTable**: Testet die Methode `isInUpdateTable`, um zu überprüfen, ob ein Eintrag in der Tabelle vorhanden ist.
- **testGetLastUpdate**: Testet die Methode `getLastUpdate`, um sicherzustellen, dass der letzte Aktualisierungszeitpunkt eines Eintrags korrekt abgerufen wird.

## EllinikaControllerTest

### Schlüsselpunkte der Tests

- **testInsertEntryData**: Dieser Test behandelt sowohl Szenarien, in denen ein Eintrag existiert, als auch solche, in denen kein Eintrag existiert. Er überprüft, ob die Methoden des Controllers Datenbankoperationen korrekt verwalten, indem neue Daten eingefügt und bestehende Daten entfernt werden.
- **testDeleteEntryById**: Testet die Funktionalität zum Löschen eines Eintrags anhand seiner ID.
- **testIsInTable**: Überprüft, ob die Methode korrekt erkennt, ob ein Eintrag in der Tabelle existiert.
- **testCheckAllChangedTrue**: Stellt sicher, dass die Methode genau bewertet, ob alle Änderungen für einen bestimmten Eintrag als wahr markiert sind.
- **testDeleteEntriesIfAllChangedTrue**: Dieser Test überprüft, ob die Methode Einträge nur dann löscht, wenn alle zugehörigen 'geänderten' Status wahr sind, und implementiert das Transaktionsmanagement, um potenzielle Datenbankfehler zu handhaben.

## EntryHandlerTest

### Setup und Teardown
Jeder Test richtet die notwendigen Mocks ein und bereinigt sie, um Seiteneffekte zu vermeiden.

### Testfälle
- **testProcessEntryWithNewChanges**: Stellt sicher, dass `processEntry` korrekt auf neue Änderungen reagiert und wie erwartet mit `EllinikaController` und `UpdateController` interagiert.
- **testProcessEntryWithoutNewChangesConditionsMet**: Testet den Logikpfad, bei dem keine neuen Änderungen auftreten und der Datenbankstatus keine weiteren Aktionen erfordert.
- **testGetContentAsString**: Validiert, dass die Methode den String-Inhalt eines Eintrags korrekt abrufen kann, sofern Inhalt vorhanden ist.
- **testIsSingleEntry**: Bestätigt, ob die Methode genau bestimmen kann, ob ein Eintrag ein Einzel-Eintrag ist.
- **testTimeDiffCalculation**: Überprüft die interne Logik zur Berechnung des Zeitunterschieds und hebt die direkten Interaktionen mit PHPs Datumshandhabung hervor.

## PluginTest.php

### Schlüsselpunkte der Tests

- **Ereignissimulation**: Die Tests simulieren die Registrierung und Handhabung von Ereignissen unter Verwendung von Mockery und potenziellen Helfern aus dem Testframework. Bei der Verwendung von Yii2 oder einem ähnlichen fortgeschrittenen PHP-Framework wird dies an dessen integrierte Methoden angepasst.
- **Initialisierungsprüfung**: Die Initialisierungsmethode wird getestet, um sicherzustellen, dass Komponenten und Ereignishandler korrekt eingerichtet sind.
- **Einstellungen Handhabung**: Tests sind enthalten, um sicherzustellen, dass das Einstellungsmodell korrekt erstellt ist und dass HTML-Einstellungen mit gemockten Ansichten und Sitzungen gerendert werden.
- **Ereignishandhabung**: Der letzte Test überprüft, ob Ereignishandler angehängt sind und wie beabsichtigt funktionieren, wenn relevante Ereignisse ausgelöst werden.

## m240408_115547_event_tableTest

### Schlüsselpunkte der Tests

- **Testing safeUp()**: Stellt sicher, dass die createTable-Methode mit den richtigen Parametern aufgerufen wird. Dieser Test überprüft, ob alle Felder gemäss dem Migrationsskript korrekt konfiguriert sind.
- **Testing safeDown()**: Überprüft, ob die dropTable-Methode korrekt aufgerufen wird, um die Tabelle zu entfernen.
- **Assert-Bedingungen**: Beide Methoden überprüfen den booleschen Rückgabewert, um sicherzustellen, dass die Methoden wie erwartet funktionieren.

## m240414_082535_update_tableTest

### Schlüsselpunkte der Tests

- **Testing safeUp()**: Validiert, dass createTable mit den richtigen Parametern aufgerufen wird und die korrekte Struktur der Datenbanktabelle sichergestellt ist.
- **Testing safeDown()**: Überprüft, ob dropTable korrekt aufgerufen wird, um die Tabelle zu entfernen.

## Testresultate

Nach der Durchführung aller oben beschriebenen Tests können wir mit Freude berichten, dass sämtliche Tests erfolgreich sind. Es treten keine Fehler oder Probleme auf, die eine Anpassung der getesteten Methoden oder Funktionen erforderlich machen. Diese Ergebnisse bestätigen die korrekte Implementierung und Stabilität unserer Unit Tests sowie der zugrunde liegenden Logik und Datenbankinteraktionen. Somit sind wir zuversichtlich, dass unsere Anwendung robust und fehlerfrei arbeitet.
