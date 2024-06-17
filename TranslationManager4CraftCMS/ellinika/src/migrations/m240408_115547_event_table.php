<?php

namespace pse\craftellinika\migrations;

use Craft;
use craft\db\Migration;

/**
 * m240408_115547_event_table migration.
 *
 * This migration class creates or drops the 'ellinika' table in Craft CMS database.
 * The table is used for storing entries related to translations.
 * 
 * @author      Daniele De Jeso
 * @version     v1.0
 */
class m240408_115547_event_table extends Migration
{
    /**
     * Creates the 'ellinika' table.
     * 
     * This method creates the database table 'ellinika' with the specified schema:
     * - entry_id: Integer field representing the related entry ID.
     * - language_id: Integer field representing the related site ID.
     * - text: String field for storing the text without translation.
     * - url: String field for storing the URL.
     * 
     * @return bool Whether the migration was successful.
     */
    public function safeUp(): bool
    {
        // Creating table 'ellinika'
       $this->createTable('{{%ellinika_ui}}', [
        'entry_id' => $this->integer()->notNull(),
        'language_id' => $this->integer()->notNull(),
        'fieldHandle' => $this -> string(),
        'title' => $this->string(),
        'text' => $this->text(),
        'url' => $this->string(),
        'changed' => $this->boolean()->notNull(),
        'author' => $this->string()->notNull(),
        'dateCreated' => $this->dateTime()->notNull(),
    ]);

        return true;
    }

    /**
     * Drops the 'ellinika' table.
     * 
     * This method drops the 'ellinika' table from the database.
     * 
     * @return bool Whether the migration was successful.
     */
    public function safeDown(): bool
    {
        // Deleting table 'ellinika'
        $this->dropTable('{{%ellinika_ui}}');

        return true;
    }
}