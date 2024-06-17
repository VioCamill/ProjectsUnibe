<?php

namespace pse\craftellinika\tests;

use PHPUnit\Framework\TestCase;
use pse\craftellinika\migrations\m240414_082535_update_table;
use craft\db\Migration;
use Craft;
use yii\db\Command;
use Mockery as m;

/**
 * Unit tests for m240414_082535_update_table migration.
 */
class m240414_082535_update_tableTest extends TestCase
{
    /**
     * @var m240414_082535_update_table
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
        $this->migration = new m240414_082535_update_table;
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
            ->with('{{%update_db}}', [
                // type check keys are not null
                'id' => m::type('Object'),
                'entry_id' => m::type('Object'),
                'language_id' => m::type('Object'),
                'last_Update' => m::type('Object'),
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
            ->with('{{%update_db}}')
            ->andReturnTrue();

        // Assert that safeDown method returns true
        $this->assertTrue($this->migration->safeDown());
    }
}
