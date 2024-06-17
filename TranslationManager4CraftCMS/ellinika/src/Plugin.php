<?php

namespace pse\craftellinika;

use Craft;
use craft\base\Element;
use craft\base\Model;
use craft\base\Plugin as BasePlugin;
use craft\errors\MissingComponentException;
use craft\events\ModelEvent;
use craft\elements\Entry;
use pse\craftellinika\controllers\EllinikaController;
use pse\craftellinika\models\Settings;
use pse\craftellinika\controllers\UpdateController;
use Twig\Error\LoaderError;
use Twig\Error\RuntimeError;
use Twig\Error\SyntaxError;
use yii\base\Event;
use pse\craftellinika\controllers\EntryHandler;
use craft\events\RegisterUrlRulesEvent;
use craft\web\UrlManager;
use yii\base\Exception;
use yii\base\InvalidConfigException;
use yii\console\Application as ConsoleApplication;

/**
 * Ellinika plugin.
 *
 * Main class for the Ellinika plugin, handling initialization,
 * event handling, and providing access to plugin instances and settings.
 * This class is responsible for setting up the plugin's core functionalities,
 * including URL routing and handling Craft CMS events related to entries.
 *
 * @author pse24
 * @copyright pse24
 * @license MIT
 */
class Plugin extends BasePlugin
{
    /** @var string The plugin's schema version */
    public string $schemaVersion = '1.0.0';

    /** @var bool Indicates if the plugin has its own settings page in the control panel */
    public bool $hasCpSettings = true;

    /** @var bool Indicates if the plugin has its own section in the control panel */
    public bool $hasCpSection = true;

    /** @var Plugin Holds the singleton instance of the plugin */
    public static Plugin $plugin;

    /**
     * Initializes the plugin.
     * This method is called after the plugin class is instantiated, setting up event handlers,
     * URL rules, and any necessary component configurations.
     */
    public function init()
    {
        parent::init();
        self::$plugin = $this;

        // Register custom URL rules for the plugin
        Event::on(
            UrlManager::class,
            UrlManager::EVENT_REGISTER_CP_URL_RULES,
            function (RegisterUrlRulesEvent $event) {
                $event->rules['ellinika'] = 'ellinika/ellinika/index';
                $event->rules['ellinika/delete-field'] = 'ellinika/ellinika/delete-field';
                $event->rules['actions/ellinika/ellinika/update-field-content'] = 'ellinika/ellinika/update-field-content';
            }
        );

        $this->attachEventHandlers();
    }
    /**
     * Creates the settings model instance for the plugin
     *
     * @return Model|null settings model instance or null if creation fails
     * @throws InvalidConfigException if configuration is invalid
     */
    protected function createSettingsModel(): ?Model
    {
        return Craft::createObject(Settings::class);
    }

    /**
     * Renders the settings HTML for the plugin.
     * This method is called to output the HTML for the plugin's settings page in the control panel.
     *
     * @return string|null The rendered settings HTML or null.
     * @throws MissingComponentException if required component is missing
     */
    protected function settingsHtml(): ?string
    {
        // Retrieve flash message from the session
        $changeMessage = Craft::$app->getSession()->getFlash('entryChange', 'No changes found.', false);

        // Render the plugin settings template
        try {
            return Craft::$app->view->renderTemplate('ellinika/_settings', [
                'plugin' => $this,
                'settings' => $this->getSettings(),
                'changeMessage' => $changeMessage,
            ]);
        } catch (LoaderError|RuntimeError|SyntaxError|Exception $e) {
            Craft::$app->getSession()->setError($e->getMessage());
            return 'An error occurred while rendering the settings page.';
        }
    }

    /**
     * Attaches event handlers for the plugin
     * Method is connected to Craft CMS events:
     * EVENT_AFTER_SAVE and EVENT_AFTER_DELETE
     *
     * @return void
     */
    private function attachEventHandlers(): void
    {

        Event::on(
            Element::class,
            Element::EVENT_AFTER_SAVE,
            function (ModelEvent $event) {
                // Boolean to distinguish which language has been modified
                static $changed = true;

                /** @var Entry $entry that was saved */
                $entry = $event->sender;

                // check if entry is draft or revision. If true scip processing
                if ($entry->getIsDraft() || $entry->getIsRevision()) {
                    return;
                }
                // initialize new EntryHandler
                $entryHandler = new EntryHandler($entry);
                // check if entry is fresh
                if ($entry->getIsFresh()) {
                    $entryHandler->processFreshEntry();
                } else {
                    $entryHandler->processEntry($changed);
                    $changed = false;
                }
            }
        );
        // Handle after an element is deleted
        Event::on(
            Element::class,
            Element::EVENT_AFTER_DELETE,
            function ($event) {
                /** @var Entry $entry that was deleted */
                $entry = $event->sender;
                //Deletes all entries in plugin tables with the id of the entry
                UpdateController::deleteEntryById($entry);
                EllinikaController::deleteEntryById($entry);
            }
        );
    }

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
}
