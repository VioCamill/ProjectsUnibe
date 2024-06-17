# Integration Testdokumentation

Integrationstests stellen sicher, dass das Plugin `Ellinika` korrekt im Craft CMS integriert ist. Diese Tests überprüfen die Richtigkeit der Verwendung von Entry Points. Dies wird manuell durch `info()`-Methoden aus der `Craft`-Klasse abgerufen und in `.log`-Dateien abgespeichert und ausgewertet.

## Durchführung der Integrationstests

Um die Integration des Plugins zu überprüfen, werden `info()`-Methoden verwendet, um relevante Informationen zu sammeln und in `.log`-Dateien zu speichern. Diese Log-Dateien werden anschliessend ausgewertet, um sicherzustellen, dass alle Entry Points korrekt funktionieren.

### Erstellen der Log-Dateien

Der folgende Codeblock zeigt, wie die `info()`-Methoden verwendet werden, um Log-Dateien zu erstellen:

#### Hinzugefügter Code in Plugin.php

```php
private function attachEventHandlers(): void
{
    Event::on(
        Element::class,
        Element::EVENT_AFTER_SAVE,
        function (ModelEvent $event) {
            /** @var Entry $entry that was saved */
            $entry = $event->sender;
            
            Craft::info('Entry saved: ' . $entry->title, __METHOD__);
            
            // Further Code
        }
    );
    // Handle after an element is deleted
    Event::on(
        Element::class,
        Element::EVENT_AFTER_DELETE,
        function ($event) {
            /** @var Entry $entry that was deleted */
            $entry = $event->sender;

            Craft::info('Entry deleted: ' . $entry->title, __METHOD__);

            // Further Code
        }
    );
}
```

### Log Datei

Die generierten Log-Dateien enthalten Informationen über die durchgeführten Operationen. Hier ein Ausschnitt für unsere Entry Points:

```log
2024-05-14 14:36:08 [-][-][info][application] Entry saved: Lion
2024-05-20 14:36:21 [-][-][info][application] Entry deleted: Lion
```

## Ergebnisse

Die Integrationstests haben bestätigt, dass das Plugin `Ellinika` korrekt im Craft CMS integriert ist. Alle Entry Points wurden erfolgreich überprüft, und die Log-Dateien zeigen, dass beim Speichern und Löschen, das Plugin korrektüber die Entry Points angesprochen wurde. Die Auswertung der Log-Dateien ergab keine Fehler oder Probleme, was darauf hinweist, dass die Integration fehlerfrei funktioniert.