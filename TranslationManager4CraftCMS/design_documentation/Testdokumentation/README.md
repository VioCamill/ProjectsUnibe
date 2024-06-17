# Testdokumentation und -resultate

Systematisches Testen ist unabdingbar, um die Qualität von Software zu garantieren. Dieses Dokument beschreibt das Testkonzept für unser Projekt und beantwortet wichtige Fragen zu den verschiedenen Testtypen. Nachfolgend werden die durchgeführten Tests im Detail beschrieben.

## Unit- und Datenbanktests

Unit-Tests werden mithilfe des Testframeworks [PHPUnit](https://phpunit.de/) durchgeführt. Es werden einzelne Klassen sowie deren Methoden getestet. Aufgrund der engen Integration mit Craft und Yii müssen viele Funktionen gemockt werden, was die Erstellung von Unit-Tests sehr herausfordernd macht. Um diese Funktionen zu mocken, verwenden wir das Framework [Mockery](https://github.com/mockery/mockery).

### Umfang und Durchführung
Die Tests decken verschiedene Szenarien ab, um sicherzustellen, dass alle Methoden und Klassen wie erwartet funktionieren. Diese Tests sind entscheidend, um die Logik unserer Anwendung zu überprüfen, ohne direkte Interaktionen mit der Datenbank zu erfordern.

### Datenbanktests
Obwohl keine direkten Datenbanktests durchgeführt werden, wird die Korrektheit der Datenbankmanipulationen überprüft. Dies erfolgt durch die Simulation von Datenbankoperationen, um sicherzustellen, dass alle Manipulationen korrekt durchgeführt werden. Die einzelnen Datenbankmanipulationsmethoden werden mit Unit-Tests überprüft, um sicherzustellen, dass die Datenbankoperationen wie erwartet funktionieren.

Detaillierte Informationen zu den Unit- und Datenbanktests finden Sie in der [Unit- und Datenbanktests-Dokumentation](./UnitTests.md).

## Integrationstest

Integrationstests stellen sicher, dass das Plugin `Ellinika` korrekt im Craft CMS integriert ist. Diese Tests überprüfen die Richtigkeit der Verwendung von Entry Points. Dies wird manuell durch `info()`-Methoden aus der `Craft`-Klasse abgerufen und in `.log`-Dateien abgespeichert und ausgewertet.

Weitere Informationen zu den Integrationstests finden Sie in der [Integrationstests-Dokumentation](./IntegrationTests.md).

## Installationstest

Installationstests werden durchgeführt, um sicherzustellen, dass das Plugin auf verschiedenen UNIX-Systemen korrekt installiert werden kann. Da das Plugin vorerst nicht veröffentlicht wird, erfolgt die Installation über die Kommandozeile. Die erfolgreiche Installation wird im GUI-Interface von Craft CMS überprüft. Zudem wird manuell kontrolliert, ob die Ellinika-Tabelle und deren Einträge in der Craft CMS-Datenbank vorhanden sind.

Detaillierte Informationen zu den Installationstests finden Sie in der [Installationstests-Dokumentation](./InstallationTests.md).

## GUI Test

### Usability Test
Der Usability Test bewertet die Intuitivität des GUI für technisch affine Benutzer. Etwa 20 Personen werden verschiedene Aufgaben ausführen, wie das Übersetzen und Löschen von Einträgen sowie das Einstellen von Übersetzungsoptionen. Die Tester geben Rückmeldungen und bewerten die Intuitivität des GUI.

### Responsiveness Test
Im Responsiveness Test wird überprüft, wie das Plugin-Interface auf verschiedenen Bildschirmgrössen und Auflösungen aussieht. Dies schliesst Tests auf verschiedenen Internet-Browsern ein, jedoch keine Tests auf mobilen Geräten.

Detaillierte Informationen zu den GUI Tests finden Sie in der [GUI-Tests-Dokumentation](./GUITests.md).

## Stress Test

Der Stress Test überprüft, ob das Plugin unter hoher Last stabil bleibt. Hierbei werden automatisch etwa 1000 Einträge erstellt, die korrekt in die Datenbank eingetragen und im GUI angezeigt werden müssen. Die Korrektheit dieser Einträge wird überprüft.

Detaillierte Informationen zu den Stresstests finden Sie in der [Stresstests-Dokumentation](./StressTests.md).

## Testresultate

Nach der Durchführung aller oben beschriebenen Tests können wir berichten, dass sämtliche Tests erfolgreich sind. Es treten keine Fehler oder Probleme auf, die eine Anpassung der getesteten Methoden oder Funktionen erforderlich machen. Diese Ergebnisse bestätigen die korrekte Implementierung und Stabilität unserer Unit Tests sowie der zugrunde liegenden Logik und Datenbankinteraktionen. Unsere Anwendung arbeitet robust und fehlerfrei.

Weitere Details zu den einzelnen Testresultaten können in den jeweiligen sssdateien eingesehen werden.
