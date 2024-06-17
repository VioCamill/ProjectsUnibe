<?php

namespace pse\craftellinika\tests;

use PHPUnit\Framework\TestCase;
use pse\craftellinika\controllers\UpdateController;
use craft\elements\Entry;
use Craft;
use yii\db\Command;
use yii\db\Connection;
use yii\db\Query;
use Mockery as m;

/**
 * Test class for UpdateController.
 * 
 * @author Dario Häfliger
 * @version 1.0
 */
class UpdateControllerTest extends TestCase
{
    protected $mockDb;
    protected $mockCommand;

    /**
     * Set up method.
     * Initializes mocks for the Db and command.
     */
    protected function setUp(): void
    {
        parent::setUp();
        $this->mockDb = m::mock('alias:' . Connection::class);
        $this->mockCommand = m::mock(Command::class);
        Craft::$app->set('db', $this->mockDb);
    }

    /**
     * Clean up method.
     * Closes all active mocks.
     */
    protected function tearDown(): void
    {
        m::close();
        parent::tearDown();
    }

    /**
     * Tests insertUpdateData method when the entry already exists.
     */
    public function testInsertUpdateDataWithExistingEntry()
    {
        $entry = m::mock(Entry::class);
        $entry->id = 123;
        $entry->siteId = 1;

        $this->mockDb->shouldReceive('createCommand')
            ->twice() // For delete and insert commands
            ->andReturn($this->mockCommand);

        $this->mockCommand->shouldReceive('delete')->once()->andReturn(true);
        $this->mockCommand->shouldReceive('insert')->once()->andReturn(true);

        UpdateController::shouldReceive('isInUpdateTable')
            ->once()
            ->with($entry->id, $entry->siteId)
            ->andReturn(true);

        UpdateController::shouldReceive('deleteEntry')
            ->once()
            ->with($entry);

        UpdateController::insertUpdateData($entry, '2024-05-01');
    }

    /**
     * Tests insertUpdateData method when the entry does not exist.
     */
    public function testInsertUpdateDataWithNewEntry()
    {
        $entry = m::mock(Entry::class);
        $entry->id = 124;
        $entry->siteId = 1;

        $this->mockDb->shouldReceive('createCommand')
            ->once() // Only insert command
            ->andReturn($this->mockCommand);

        $this->mockCommand->shouldReceive('insert')->once()->andReturn(true);

        UpdateController::shouldReceive('isInUpdateTable')
            ->once()
            ->with($entry->id, $entry->siteId)
            ->andReturn(false);

        UpdateController::insertUpdateData($entry, '2024-05-02');
    }

    /**
     * Tests deleteEntry method.
     */
    public function testDeleteEntry()
    {
        $entry = m::mock(Entry::class);
        $entry->id = 123;
        $entry->siteId = 1;

        $this->mockDb->shouldReceive('createCommand')->once()->andReturn($this->mockCommand);
        $this->mockCommand->shouldReceive('delete')->once()->andReturn(true);

        UpdateController::deleteEntry($entry);
    }

    /**
     * Tests deleteEntryById method.
     */
    public function testDeleteEntryById()
    {
        $entry = m::mock(Entry::class);
        $entry->id = 124;

        $this->mockDb->shouldReceive('createCommand')->once()->andReturn($this->mockCommand);
        $this->mockCommand->shouldReceive('delete')->once()->andReturn(true);

        UpdateController::deleteEntryById($entry);
    }

    /**
     * Tests isInUpdateTable method.
     */
    public function testIsInUpdateTable()
    {
        $entryId = 123;
        $siteId = 1;

        $query = m::mock(Query::class);
        $query->shouldReceive('from')->andReturnSelf();
        $query->shouldReceive('where')->andReturnSelf();
        $query->shouldReceive('exists')->andReturn(true);

        $this->assertEquals(true, UpdateController::isInUpdateTable($entryId, $siteId));
    }

    /**
     * Tests getLastUpdate method.
     */
    public function testGetLastUpdate()
    {
        $entryId = 123;
        $siteId = 1;

        $query = m::mock(Query::class);
        $query->shouldReceive('select')->andReturnSelf();
        $query->shouldReceive('from')->andReturnSelf();
        $query->shouldReceive('where')->andReturnSelf();
        $query->shouldReceive('scalar')->andReturn('2024-05-01');

        $this->assertEquals('2024-05-01', UpdateController::getLastUpdate($entryId, $siteId));
    }
}
