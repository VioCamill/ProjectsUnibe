# Stress Testdokumentation

Um die Belastbarkeit unseres Plugins zu überprüfen, wurde ein umfassender Stress Test durchgeführt. Dabei haben wir in kurzer Zeit automatisch ungefähr 1000 Einträge im Craft CMS erstellt. Diese Einträge mussten korrekt in die Datenbank gespeichert und im GUI angezeigt werden. Die Richtigkeit der Einträge wurde anschliessend überprüft.

## Automatisierte Erstellung der Einträge

Die Einträge wurden im Craft CMS automatisch erstellt. Jeder Eintrag besteht aus einem Titel und einer Beschreibung im Channel "Mammals". Die Titel und Beschreibungen wurden im folgenden Format generiert:

- **Title**: Animal0001
- **Animal Description**: This is a description for Animal0001

Der folgende Codeblock zeigt, wie die Einträge automatisch erstellt wurden:

```php
// Anzahl der zu erstellenden Einträge
$entryCount = 1000;

for ($i = 1; $i <= $entryCount; $i++) {
    $title = 'Animal' . str_pad($i, 4, '0', STR_PAD_LEFT);
    $description = 'This is a description for ' . $title;

    // Neuer Eintrag wird erstellt
    $entry = new Entry();
    $entry->sectionId = Craft::$app->sections->getSectionByHandle('mammals')->id;
    $entry->typeId = Craft::$app->sections->getEntryTypesBySectionId($entry->sectionId)[0]->id;
    $entry->title = $title;
    $entry->setFieldValue('animalDescription', $description);
    $entry->enabled = true;

    // Eintrag wird in der Datenbank gespeichert
    if (!Craft::$app->elements->saveElement($entry)) {
        Craft::error('Failed to save entry ' . $title, __METHOD__);
    }
}
```

## Überprüfung der Einträge

Nach der automatisierten Erstellung der Einträge wurde manuell überprüft, ob die Einträge korrekt in der Tabelle vorhanden sind. Dies wurde durchgeführt, indem die Einträge direkt in der Datenbank und über das GUI von Craft CMS inspiziert wurden. 

### Ergebnisse

- **Korrektheit der Einträge**: Alle Einträge waren korrekt in der Datenbank gespeichert und wurden im GUI wie erwartet angezeigt.
- **Datenbanktabelle**: Die Datenbanktabelle wurde durch die grosse Anzahl an Einträgen nicht verzerrt.
- **GUI Performance**: Das Interface zeigte keine Verzögerungen oder Performanceprobleme trotz der hohen Anzahl an Einträgen.

Der Stress Test hat somit bestätigt, dass unser Plugin auch unter hoher Last stabil und performant bleibt. Alle Einträge wurden korrekt verarbeitet und angezeigt, ohne die Funktionalität oder die Performance der Anwendung negativ zu beeinflussen.
