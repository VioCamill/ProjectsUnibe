<?php

namespace pse\craftellinika\tests;

use PHPUnit\Framework\TestCase;
use pse\craftellinika\controllers\EntryHandler;
use craft\elements\Entry;
use Craft;
use DateTime;
use DateTimeZone;
use Mockery as m;
use pse\craftellinika\controllers\EllinikaController;
use pse\craftellinika\controllers\UpdateController;

/**
 * Unit tests for EntryHandler.
 * 
 * @author Dario Häfliger
 * @version 1.1
 */
class EntryHandlerTest extends TestCase
{
    /**
     * @var EntryHandler
     */
    private $entryHandler;

    /**
     * @var Entry
     */
    private $entryMock;

    /**
     * Setup before each test.
     */
    protected function setUp(): void
    {
        parent::setUp();
        $this->entryMock = m::mock(Entry::class);
        $this->entryMock->id = 123;
        $this->entryMock->siteId = 1;
        $this->entryMock->dateUpdated = new DateTime('now', new DateTimeZone('Europe/Zurich'));
        $this->entryHandler = new EntryHandler($this->entryMock);
    }

    /**
     * Cleanup after each test.
     */
    protected function tearDown(): void
    {
        m::close();
        parent::tearDown();
    }

    /**
     * Tests the processEntry method when entry changes are new.
     */
    public function testProcessEntryWithNewChanges()
    {
        // Set up static method calls and expectations
        EllinikaController::shouldReceive('isInTable')->andReturn(false);
        UpdateController::shouldReceive('getLastUpdate')->andReturn('2024-05-01 03:26:00');
        UpdateController::shouldReceive('insertUpdateData')->once();

        // Entry not changed scenario
        EllinikaController::shouldReceive('checkEntryChanged')->andReturn(false);
        EllinikaController::shouldReceive('entryIdExists')->andReturn(true);
        EllinikaController::shouldReceive('insertEntryData')->once();

        $this->entryHandler->processEntry(true);
    }

    /**
     * Tests the processEntry method when no new changes and conditions are met.
     */
    public function testProcessEntryWithoutNewChangesConditionsMet()
    {
        // Entry is unchanged and exists, and the database indicates it hasn't changed
        EllinikaController::shouldReceive('checkEntryChanged')->andReturn(true);
        EllinikaController::shouldReceive('entryIdExists')->andReturn(true);
        // Expect no insert data since no new changes and conditions are met
        EllinikaController::shouldNotReceive('insertEntryData');

        $this->entryHandler->processEntry(false);
    }

    /**
     * Tests getting content from an entry as a string.
     */
    public function testGetContentAsString()
    {
        $content = "Sample Content";
        $entry = m::mock(Entry::class);
        $entry->shouldReceive('getType')->andReturn((object)['getFieldLayout' => (object)['getCustomFields' => []]]);
        $entry->shouldReceive('getFieldValue')->andReturn($content);

        $result = EntryHandler::getContentAsString($entry);
        $this->assertEquals($content, $result);
    }

    /**
     * Tests checking if the entry is a single entry.
     */
    public function testIsSingleEntry()
    {
        $entry = m::mock(Entry::class);
        $entry->shouldReceive('getSection')->andReturn((object)['type' => \craft\models\Section::TYPE_SINGLE]);

        $result = EntryHandler::isSingleEntry($entry);
        $this->assertTrue($result);
    }

    /**
     * Tests the time difference calculation.
     */
    public function testTimeDiffCalculation()
    {
        // Assume method to be public for testing or use reflection if needed
        $changedUpdate = '2024-05-01 12:30:00';
        $latestUpdate = '2024-05-01 12:00:00';

        $timeDiff = $this->entryHandler->getTimeDiff($changedUpdate, $latestUpdate);
        $this->assertEquals(30, $timeDiff);
    }
}
