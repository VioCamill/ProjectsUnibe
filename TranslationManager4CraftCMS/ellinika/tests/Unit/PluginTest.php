<?php

namespace pse\craftellinika\tests;

use Codeception\Test\Unit;
use pse\craftellinika\Plugin;
use Craft;
use craft\web\UrlManager;
use craft\events\RegisterUrlRulesEvent;
use craft\elements\Entry;
use craft\events\ModelEvent;
use craft\base\Model;
use yii\base\Event;
use Mockery as m;
use pse\craftellinika\controllers\EntryHandler;
use pse\craftellinika\controllers\UpdateController;
use pse\craftellinika\controllers\EllinikaController;

/**
 * Tests for the main Plugin class of Ellinika.
 * 
 * @author Dario Häfliger
 * @version 1.0
 */
class PluginTest extends Unit
{
    /**
     * @var \UnitTester
     */
    protected $tester;

    protected function _before()
    {
        parent::_before();
        // Set up Craft's core components that the plugin might interact with
        Craft::$app = m::mock('\craft\web\Application', ['getView' => m::mock('\craft\web\View')]);
        Craft::$app->expects()->getView()->andReturn(m::mock(['renderTemplate' => 'Rendered HTML']));
    }

    protected function _after()
    {
        m::close();
    }

    /**
     * Tests the initialization of the plugin.
     */
    public function testInit()
    {
        // Expect URL rules to be set up
        Event::fake();
        $plugin = new Plugin();
        $plugin->init();

        // Ensure URL rules are registered
        Event::assertListening(
            UrlManager::class,
            UrlManager::EVENT_REGISTER_CP_URL_RULES,
            function (RegisterUrlRulesEvent $event) {
                return $event->rules === [
                    'ellinika' => 'ellinika/ellinika/index',
                    'ellinika/delete-entry' => 'ellinika/ellinika/delete-entry'
                ];
            }
        );

        // Ensure event handlers for Entry save and delete are registered
        Event::assertListening(
            Entry::class,
            Entry::EVENT_AFTER_SAVE,
            [Plugin::class, 'handleAfterSave']
        );

        Event::assertListening(
            Entry::class,
            Entry::EVENT_AFTER_DELETE,
            [Plugin::class, 'handleAfterDelete']
        );
    }

    /**
     * Test the settings model creation.
     */
    public function testCreateSettingsModel()
    {
        $plugin = new Plugin();
        $settings = $plugin->createSettingsModel();
        $this->assertInstanceOf(Model::class, $settings);
    }

    /**
     * Test the rendering of settings HTML.
     */
    public function testSettingsHtml()
    {
        $plugin = new Plugin();
        Craft::$app->expects()->getSession()->getFlash('entryChange', 'No changes found.', false)->andReturn('No changes found.');
        $html = $plugin->settingsHtml();
        $this->assertEquals('Rendered HTML', $html);
    }

    /**
     * Tests that the event handlers are attached correctly.
     */
    public function testAttachEventHandlers()
    {
        $plugin = new Plugin();
        
        // Calling attachEventHandlers
        $plugin->init();

        // Simulate ENTRY_AFTER_SAVE event triggering
        $entry = m::mock(Entry::class);
        $entry->shouldReceive('getIsRevision')->andReturn(false);
        $entry->shouldReceive('getIsDraft')->andReturn(false);
        $event = new ModelEvent(['sender' => $entry]);

        // Process the entry
        EntryHandler::shouldReceive('processEntry')->once();

        Event::trigger(Entry::class, Entry::EVENT_AFTER_SAVE, $event);

        // Simulate ENTRY_AFTER_DELETE event triggering
        UpdateController::shouldReceive('deleteEntryById')->once();
        EllinikaController::shouldReceive('deleteEntryById')->once();
        
        Event::trigger(Entry::class, Entry::EVENT_AFTER_DELETE, $event);
    }
}
