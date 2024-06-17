# Installationstestdokumentation

Ein Installationstest wurde durchgeführt, um sicherzustellen, dass das Plugin `Ellinika` korrekt auf Craft CMS installiert werden kann. Da Craft CMS nur auf UNIX-Systemen läuft, wurden die Installationen auf unseren lokalen Betriebssystemen getestet (Debian- und MacOS-Instanzen).

## Durchführung des Installationstests

Das Plugin `Ellinika` wurde über die Kommandozeile installiert, da es vorerst nicht veröffentlicht und im Plugin-Store verfügbar ist. Die erfolgreiche Installation wurde im GUI-Interface von Craft CMS überprüft. Ein besonderes Augenmerk lag auf der automatischen Migration der Ellinika-Tabelle in die Datenbank von Craft CMS. Nach der Installation wurde mit einer `MySQL client`-Instanz manuell überprüft, ob die Einträge und die Tabelle in der Datenbank sichtbar sind.

### Implementierung in Plugin.php

Der folgende Code zeigt, wie die Installation und Deinstallation des Plugins sowie die Migrationen in Plugin.php implementiert wurden:

```php
/**
 * Handle tasks after plugin is installed.
 */
public function afterInstall(): void
{
    parent::afterInstall();
    // Run the migrations
    $this->runMigrationsInBackground('up');
}

/**
 * Handle tasks before plugin is uninstalled.
 */
public function beforeUninstall(): void
{
    // Run the migrations down
    $this->runMigrationsInBackground('down');
    parent::beforeUninstall();
}

/**
 * Run migrations in the background.
 *
 * @param string $direction
 */
protected function runMigrationsInBackground(string $direction)
{
    $basePath = Craft::getAlias('@root');
    $cmd = escapeshellcmd(PHP_BINDIR . '/php') . ' ' . escapeshellarg($basePath . '/craft') . ' migrate/' . $direction . ' --plugin=' . escapeshellarg($this->handle) . ' > /dev/null 2>&1 &';
    exec($cmd);
}
```

## Überprüfung der Installation

Nach der Installation wurde manuell überprüft, ob die Tabelle und die Einträge in der Datenbank korrekt erstellt wurden. Dazu wurden folgende Schritte unternommen:

1. Installation des Plugins über die Kommandozeile (oder im GUI):

    ```sh
    php craft install/plugin ellinika
    ```

2. Überprüfung im GUI-Interface:

    - Die erfolgreiche Installation von `Ellinika` wurde im Craft CMS-Admin-Panel überprüft.

3. Manuelle Überprüfung der Datenbank:

    - Mit einer `MySQL-Instanz` wurde überprüft, ob die `ellinika`-Tabellen korrekt in der Datenbank angehängt sind.

## Erebnisse
Die Installationstests haben bestätigt, dass das Plugin Ellinika korrekt auf Craft CMS installiert werden kann. Die Migrationen wurden erfolgreich ausgeführt, und die Tabellen korrekt in der Datenbank erstellt. Die Überprüfung im GUI-Interface zeigte keine Fehler oder Probleme. Somit ist sichergestellt, dass die Installation und Deinstallation des Plugins fehlerfrei funktionieren.