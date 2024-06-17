<?php

namespace pse\craftellinika\tests;

use PHPUnit\Framework\TestCase;
use pse\craftellinika\controllers\EllinikaController;
use craft\elements\Entry;
use Craft;
use yii\db\Command;
use yii\db\Connection;
use yii\db\Query;
use Mockery as m;

/**
 * Unit tests for EllinikaController.
 * 
 * @author Dario Häfliger
 * @version 1.2
 */
class EllinikaControllerTest extends TestCase
{
    protected $mockDb;
    protected $mockCommand;

    /**
     * Setup before each test.
     */
    protected function setUp(): void
    {
        parent::setUp();
        $this->mockDb = m::mock('alias:' . Connection::class);
        $this->mockCommand = m::mock(Command::class);
        Craft::$app->set('db', $this->mockDb);
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
     * Tests insertEntryData method for both existing and new entries.
     */
    public function testInsertEntryData()
    {
        $entry = m::mock(Entry::class);
        $entry->id = 123;
        $entry->siteId = 1;
        $entry->shouldReceive('getCpEditUrl')->andReturn('http://example.com');

        // Setting up mocks for database interactions
        $this->mockDb->shouldReceive('createCommand')->andReturn($this->mockCommand);
        $this->mockCommand->shouldReceive('insert')->once()->andReturn(true);
        $this->mockCommand->shouldReceive('delete')->once()->andReturn(true);

        // Static method mocks
        EllinikaController::shouldReceive('isInTable')->once()->with($entry->id, $entry->siteId)->andReturn(true);
        EllinikaController::shouldReceive('deleteEntry')->once()->with($entry);

        // Test insertion with change status
        EllinikaController::insertEntryData($entry, true);
    }

    /**
     * Tests the deletion of an entry by its ID.
     */
    public function testDeleteEntryById()
    {
        $entry = m::mock(Entry::class);
        $entry->id = 123;

        $this->mockDb->shouldReceive('createCommand')->once()->andReturn($this->mockCommand);
        $this->mockCommand->shouldReceive('delete')->once()->andReturn(true);

        EllinikaController::deleteEntryById($entry);
    }

    /**
     * Tests checking if an entry exists in the database table.
     */
    public function testIsInTable()
    {
        $entryId = 123;
        $siteId = 1;

        $exists = true;
        $queryMock = m::mock(Query::class);
        $queryMock->shouldReceive('from')->andReturnSelf()
            ->shouldReceive('where')->andReturnSelf()
            ->shouldReceive('exists')->andReturn($exists);

        $this->assertEquals($exists, EllinikaController::isInTable($entryId, $siteId));
    }

    /**
     * Tests the method to check if all 'changed' statuses for a given entry ID are true.
     */
    public function testCheckAllChangedTrue()
    {
        $entryId = 123;
        $countTotal = 5;
        $countChangedTrue = 5;

        $queryMock = m::mock(Query::class);
        $queryMock->shouldReceive('from')->andReturnSelf()
                  ->shouldReceive('where')->andReturnSelf()
                  ->shouldReceive('count')->andReturn($countTotal, $countChangedTrue);

        $result = EllinikaController::checkAllChangedTrue($entryId);
        $this->assertTrue($result);
    }

    /**
     * Tests the conditional deletion of entries if all 'changed' flags are true.
     */
    public function testDeleteEntriesIfAllChangedTrue()
    {
        $entryId = 123;
        $this->mockDb->shouldReceive('beginTransaction')->andReturn(m::mock(\yii\db\Transaction::class));
        $this->mockCommand->shouldReceive('delete')->once()->andReturn(true);
        $this->mockCommand->shouldReceive('commit')->once();

        EllinikaController::shouldReceive('checkAllChangedTrue')->once()->with($entryId)->andReturn(true);

        EllinikaController::deleteEntriesIfAllChangedTrue($entryId);
    }
}
