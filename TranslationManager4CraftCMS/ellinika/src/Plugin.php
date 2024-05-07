<?php

namespace pse\craftellinika;

use Craft;
use craft\base\Element;
use craft\base\Model;
use craft\base\Plugin as BasePlugin;
use craft\events\ModelEvent;
use craft\elements\Entry;
use phpDocumentor\Reflection\Types\True_;
use pse\craftellinika\controllers\EllinikaController;
use pse\craftellinika\models\Settings;
use pse\craftellinika\controllers\UpdateController;
use ReflectionObject;
use yii\base\Event;
use pse\craftellinika\controllers\databaseController;
use pse\craftellinika\controllers\EntryHandler;
use craft\events\RegisterUrlRulesEvent;
use craft\web\UrlManager;
use DateTime;
use DateTimeZone;

/**
 * Ellinika plugin.
 *
 * Main class for the Ellinika plugin, handling initialization,
 * event handling, and providing access to plugin instances and settings.
 * This class is responsible for setting up the plugin's core functionalities,
 * including URL routing and handling Craft CMS events related to entries.
 *
 * @method static Plugin getInstance() Returns the instance of this plugin.
 * @method Settings getSettings() Returns the settings model for this plugin.
 * @author pse24
 * @copyright pse24
 * @license MIT
 */
class Plugin extends BasePlugin
{
    private int $currentSiteId;
    public string $schemaVersion = '1.0.0'; // Specifies the plugin's schema version
    public bool $hasCpSettings = true; // Indicates whether the plugin has its own settings page in the Control Panel
    public bool $hasCpSection = true; // Indicates whether the plugin adds its own section to the Control Panel

    /**
     * Provides configuration for the plugin's components.
     * Use this method to declare and configure any components required by the plugin.
     *
     * @return array The configuration array for components.
     */
    public static function config(): array
    {
        return [
            'components' => [
                // Component configurations go here...
            ],
        ];
    }

    /**
     * Holds the singleton instance of the plugin.
     */
    public static $plugin;

    /**
     * Initializes the plugin.
     * This method is called after the plugin class is instantiated, setting up event handlers,
     * URL rules, and any necessary component configurations.
     */
    public function init()
    {
        parent::init();
        self::$plugin = $this;

        // Register the service
        $this->setComponents([
            'entryDataService' => \pse\craftellinika\services\EntryDataService::class,
        ]);

        // Register custom URL rules for the plugin
        Event::on(
            UrlManager::class,
            UrlManager::EVENT_REGISTER_CP_URL_RULES,
            function (RegisterUrlRulesEvent $event) {
                $event->rules['ellinika'] = 'ellinika/ellinika/index';
                $event->rules['ellinika/delete-entry'] = 'ellinika/ellinika/delete-entry';
            }
        );

        // Attach event handlers for Craft CMS events, such as when entries are saved
        $this->attachEventHandlers();
    }

    /**
     * Creates the settings model instance for the plugin.
     * Override this method to provide a custom settings model for your plugin.
     *
     * @return Model|null The settings model instance or null if none.
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
     */
    protected function settingsHtml(): ?string
    {
        // Retrieve flash message from the session
        $changeMessage = Craft::$app->getSession()->getFlash('entryChange', 'No changes found.', false);

        // Render the plugin settings template
        return Craft::$app->view->renderTemplate('ellinika/_settings', [
            'plugin' => $this,
            'settings' => $this->getSettings(),
            'changeMessage' => $changeMessage,
        ]);
    }


    /**
     * Attaches event handlers for the plugin.
     * Use this method to connect to Craft CMS events, such as when entries are saved.
     */
    private function attachEventHandlers(): void
    {
        // Register event handler for the 'afterSave' event of Entry
        Event::on(
            Entry::class,
            Entry::EVENT_AFTER_SAVE,
            function (ModelEvent $event) {
                static $changed = true;
                // Get the saved entry object
                /** @var Entry $entry */
                $entry = $event->sender;
                if($entry->getIsDraft() || $entry->getIsRevision()){
                    return;
                }

                /*Craft::info("Log bei änderung Entry:
                ist event neu ? {$event->isNew},
                ist Neu für die Seite? : {$entry->isNewForSite}
                Titel: {$entry->title},
                ID: {$entry->id}, 
                SeitenId: {$entry->siteId}, 
                status: {$entry->status},
                ist frisch ? : {$entry->getIsFresh()},
                ist draft? {$entry->getIsDraft()},
                ist revision?: {$entry->getIsRevision()}
         
                    ", 'custom-module');*/
                $entryHandler = new EntryHandler($entry);
                if($entry->getIsFresh()){
                    $entryHandler->processFreshEntry();
                } else {
                    $entryHandler->processEntry($changed);
                    $changed = false;
                }
            }
        );
        Event::on(
            Entry::class,
            Entry::EVENT_AFTER_DELETE,
            function ($event) {
                //Delete all entries in dbs with the id of the entry
                $entry = $event->sender;
                UpdateController::deleteEntryById($entry);
                EllinikaController::deleteEntryById($entry);
            }
        );
    }
}
