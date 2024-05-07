<?php

namespace pse\craftellinika\migrations;

use Craft;
use craft\db\Migration;

/**
 * m240414_082535_update_table migration.
 */
class m240414_082535_update_table extends Migration
{
    /**
     * @inheritdoc
     */
    public function safeUp(): bool
    {
        // Creating table 'update_db'
        $this->createTable('{{%update_db}}', [
            'id' => $this->primaryKey(),
            'entry_id' => $this->integer()->notNull(),
            'language_id' => $this->integer()->notNull(),
            'last_Update' => $this->string()->notNull(),
        ]);
    

        return true;
    }

    /**
     * @inheritdoc
     */
    public function safeDown(): bool
    {
        $this->dropTable('{{%update_db}}');

        return true;
    }
}
