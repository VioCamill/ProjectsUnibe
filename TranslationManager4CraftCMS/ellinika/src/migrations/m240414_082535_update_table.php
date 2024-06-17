<?php

namespace pse\craftellinika\migrations;

use Craft;
use craft\db\Migration;

/**
 * m240414_082535_update_table migration.
 *
 * This migration class creates or drops the 'update_db' table in the Craft CMS database.
 * The table is used for storing information related to updates.
 * 
 * @author pse24
 * @copyright pse24
 * @license MIT
 */
class m240414_082535_update_table extends Migration
{
    /**
     * Creates the 'update_db' table.
     * 
     * This method creates the database table 'update_db' with the specified schema:
     * - id: Primary key for the table.
     * - entry_id: Integer field representing the related entry ID.
     * - language_id: Integer field representing the related site ID.
     * - fieldHandle: String field for storing the field handle.
     * - last_Update: String field for storing the last update information.
     * 
     * @return bool Whether the migration was successful.
     */
    public function safeUp(): bool
    {
        // Creating table 'update_db'
        $this->createTable('{{%ellinika_update}}', [
            'id' => $this->primaryKey(),
            'entry_id' => $this->integer()->notNull(),
            'language_id' => $this->integer()->notNull(),
            'fieldHandle' => $this->string(),
            'last_Update' => $this->string()->notNull(),
        ]);

        return true;
    }

    /**
     * Drops the 'update_db' table.
     * This method drops the 'update_db' table from the database.
     * 
     * @return bool Whether the migration was successful.
     */
    public function safeDown(): bool
    {
        // Deleting table 'update_db'
        $this->dropTable('{{%ellinika_update}}');

        return true;
    }
}
