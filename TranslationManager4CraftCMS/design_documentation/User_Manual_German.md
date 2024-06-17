# Benutzerhandbuch von Ellinika
Dieses Dokument erklärt die Nutzung und den Aufbau von Ellinika.

# Use Cases

## Use Case 1: Entry für mehrsprachige Webseite erstellen 
### Kurzbeschreibung: 
Erstellen von einem Entry für eine dreisprachige Webseite (Beispiel mit Englisch, Deutsch und Französisch)
### Beteiligte: 
User und System Craft CMS mit Plugin Ellinika
### Vorbedingungen: 
- User hat Craft CMS und das Plugin Ellinika installiert
- Die Ellinika Tabelle enthält noch keine Einträge

  ![](/design_documentation/images/screenshots/emptyTable.png)
### Ablauf: 
1. User erstellt ein Entry in Craft CMS für die englische Webseite

![](/design_documentation/images/screenshots/createEntry.png)

2. User klickt im Dashboard auf Ellinika
3. User sieht Tabelle des Plugins mit geänderter Sprache (en) sowie die ungeänderten Sprachen Deutsch und Französisch <br>  ( de, fr)

![](/design_documentation/images/screenshots/tableWithEntry.png)

4. a) User wählt Deutsch als Sprachversion, die er ändern möchte
5. a) User klickt auf Edit
6. a) User ändert die deutsche Version im Popup Fenster

![](/design_documentation/images/screenshots/save.png)

7. a) User bestätigt seine Änderungen mit Save changes
### Alternativablauf zu a):
4. b) User klickt auf den Link (de) bei der deutschen Version

![](/design_documentation/images/screenshots/alternativeWay.png)

5. b) User wird auf die Bearbeitungsseite des Entries der deutschen Version verlinkt
6. b) User ändert den Eintrag in der deutschen Sprache direkt über die Entries in Craft CMS

![](/design_documentation/images/screenshots/alternativeChange.png)

7. b) User speichert seine Änderungen über die Entries in Craft CMS 
8. b) User klickt im Dashboard auf Ellinika um seine Änderungen zu prüfen
### Nachbedingungen: 
- Die Ellinika Tabelle enthält den Eintrag für den erstellten Entry
- Die englische und deutsche Version sind in der Spalte Changed aufgelistet
- Die französische Version ist nach wie vor in der Spalte Unchanged

![](/design_documentation/images/screenshots/endUseCase1.png)

## Use Case 2: Einstellungen von Ellinika
### Kurzbeschreibung: 
User möchte über die Ellinika Tabelle längere Texte angezeigt bekommen
### Beteiligte:
User und System Craft CMS mit Plugin Ellinika
### Vorbedingungen: 
- User hat Craft CMS und das Plugin Ellinika installiert
- In Der Tabelle von Ellinika ist ein Eintrag aufgelistet
- Text Feld zeigt nur ein Teil des Textes im entsprechenden Field an

![](/design_documentation/images/screenshots/UseCase2.png)

### Ablauf: 
1. User klickt im Dashboard von Craft CMS auf Settings
2. Bei den Settings klickt der User bei den Plugins auf Ellinika

![](/design_documentation/images/screenshots/settings.png)

3. User ändert die maximal angezeigten Characters von der Default Einstellung (100) auf 1000

![](/design_documentation/images/screenshots/characterSettings.png)

4. User speichert seine Einstellungen
5. User überprüft seine Änderungen in der Ellinika Tabelle
### Nachbedingung: 
- Vom Textfeld des Entries werden die ersten 200 Wörter in der Ellinika Tabelle angezeigt

![](/design_documentation/images/screenshots/characterChanged.png)

## Datenstruktur von Ellinika

### Ordnerstruktur
```bash
└── ellinika
    ├── src 
    │   ├── controllers
    │   │   ├── EllinikaController.php // Verwaltet die angezeigte Tabelle .
    │   │   ├── EntryHandler.php // Verabeitet die einzelnen Entries und ihre Fields.
    │   │   └── UpdateController.php // Erstellt die Zeitstempel der Einträge.
    │   ├── migrations
    │   │   ├── m240408_115547_event_table.php // Erstellt die Event-Tabelle.
    │   │   └── m240414_082535_update_table.php // Erstellt die Update-Tabelle.
    │   ├── models
    │   │   └── Settings.php // Enthält die Variablen vom Setting.
    │   ├── templates
    │   │   ├── _settings.twig // Definiert das Settings-Frontend.
    │   │   └── index.twig // Definiert das Frontend.
    │   ├── icon.svg // Ellinika logo
    │   └──  Plugin.php //
    ├── composer.json // Definiert die aktuelle verwendeten Pakete.
    └── composer.lock // Definiert die Requirements von Ellinika.

Hinweis: Es handelt sich um eine vereinfachte Ordnerstruktur.
```
### UML Klassendiagramm
![UML Diagram](/design_documentation/images/UML_Ellinika.svg)

#### controllers
Hier ist die Logik von Ellinika verankert. 
#### migrations
Zum erstellen von einer Datenbank in Craft CMS muss eine Migration durchgeführt werden. Ellinika erstellt dabei eine Event- und eine Update-Tabelle.
#### models
Diese Klasse speichert die vom User gewünschten Einstellungen.
##### Anzahl angezeiter Zeichen von einem Entry
Die maximale Anzahl der Zeichen eines Eintrags kann eingestellt werden.
##### Zeitliche Begrenzung bei erneuten Änderungen
Es kann störend sein, wenn ein geänderter Eintrag jedes Mal in Ellinika angezeigt wird. Deshalb ist es möglich, eine zeitliche Begrenzung zu setzen, so dass bei einer erneuten Änderung der Eintrag nicht erneut in Ellinika angezeigt wird.
##### Datenbank Tabellen 
1. Ellinika_UI:
    In der ellinika_ui Tabelle wird das bearbetitete Field eines Entries gespeichert.
    Jeder Tabelleneintrag enthält die ID des Entries, die ID der Sprache (siteID), der Text im entsprechenden Field, die Verlinkung zur Bearbeitungsseite von Craft CMS und ein Boolean, ob diese Sprachversion geändert wurde. 
    <br>
2. Ellinika_Update:
    In der ellinika_update Tabelle wird für jedes Field eines Entries das letzte Änderungsdatum für die entsprechende Sprache gespeichert. Die Tabelle ist für das Plugin notwendig, da Craft selbst keine Unterscheidung für das Änderungsdatum von verschiedenen Sprachen macht.






