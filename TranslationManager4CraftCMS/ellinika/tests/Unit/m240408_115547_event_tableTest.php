<?php

namespace pse\craftellinika\tests;

use PHPUnit\Framework\TestCase;
use pse\craftellinika\migrations\m240408_115547_event_table;
use craft\db\Migration;
use Craft;
use yii\db\Command;
use Mockery as m;

/**
 * Unit tests for m240408_115547_event_table migration.
 * 
 * @author Dario Häfliger
 * @version 1.0
 */
class m240408_115547_event_tableTest extends TestCase
{
    /**
     * @var m240408_115547_event_table
     */
    private $migration;

    /**
     * @var \yii\db\Migration
     */
    private $mockMigration;

    /**
     * Setup before each test.
     */
    protected function setUp(): void
    {
        parent::setUp();
        $this->mockMigration = m::mock(Migration::class, [Craft::$app->db]);
        $this->migration = new m240408_115547_event_table;
        $this->migration->db = Craft::$app->db;
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
     * Tests the safeUp method.
     */
    public function testSafeUp()
    {
        $this->mockMigration->shouldReceive('createTable')
            ->once()
            ->with('{{%ellinika_db}}', [
                // type check keys are not null
                'entry_id' => m::type('Object'),
                'language_id' => m::type('Object'),
                'text' => m::type('Object'),
                'url' => m::type('Object'),
                'changed'=> m::type('Object'),
            ])->andReturnTrue();

        // Assert that safeUp method returns true
        $this->assertTrue($this->migration->safeUp());
    }

    /**
     * Tests the safeDown method.
     */
    public function testSafeDown()
    {
        $this->mockMigration->shouldReceive('dropTable')
            ->once()
            ->with('{{%ellinika_db}}')
            ->andReturnTrue();

        // Assert that safeDown method returns true
        $this->assertTrue($this->migration->safeDown());
    }
}
