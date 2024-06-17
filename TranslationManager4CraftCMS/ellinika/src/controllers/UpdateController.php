<?php

namespace pse\craftellinika\controllers;

use Craft;
use craft\db\Query;
use craft\elements\Entry;
use craft\errors\MissingComponentException;
use yii\db\Exception;
use yii\web\Controller;

/**
 * UpdateController handles operations related to updating the update table.
 *
 * This controller provides methods to insert, update, and delete entries
 * with corresponding field in a custom update table within the Craft CMS database.
 * It ensures that operations are performed
 * atomically using database transactions, and includes methods for checking the existence
 * of entries and retrieving the last update timestamp for a given entry.
 */
class UpdateController extends Controller
{
    /**
     * Inserts or updates entry in update table
     * @param Entry $entry to be inserted or updated in update table
     * @param string $lastUpdate timestamp of last update
     * @param string $fieldHandle handle of field being updated
     * @return void
     * @throws MissingComponentException if required component is missing
     */
    public static function insertUpdateData(Entry $entry, string $lastUpdate, string $fieldHandle): void
    {
        $db = Craft::$app->getDb();
        $inTable = UpdateController::isInUpdateTable($entry->id, $entry->siteId, $fieldHandle);
        if ($inTable) {
            UpdateController::deleteEntry($entry, $fieldHandle);
        }
        $transaction = $db->beginTransaction();
        try {
            $db->createCommand()->insert('{{%ellinika_update}}', [
                'entry_id' => $entry->id,
                'language_id' => $entry->siteId,
                'fieldHandle' => $fieldHandle,
                'last_Update' => $lastUpdate
            ])->execute();
            $transaction->commit();
        } catch (Exception $e) {
            $transaction->rollBack();
            Craft::$app->getSession()->setError($e->getMessage());
        }
    }

    /**
     * Inserts default values for each existing site id
     * in update table if entry is fresh
     * @param Entry $entry to be inserted in update table
     * @param string $lastUpdate timestamp of date created (= last update)
     * @param string $fieldHandle handle of field being created
     * @return void
     * @throws MissingComponentException if required component is missing
     */
    public static function insertFreshEntry(Entry $entry, string $lastUpdate, string $fieldHandle)
    {
        $db = Craft::$app->getDb();
        $supportedSites = Craft::$app->sites->getAllSites();
        foreach ($supportedSites as $site) {
            $siteId = $site->id;
            $transaction = $db->beginTransaction();
            try {
                $db->createCommand()->insert('{{%ellinika_update}}', [
                    'entry_id' => $entry->id,
                    'language_id' => $siteId,
                    'fieldHandle' => $fieldHandle,
                    'last_Update' => $lastUpdate,
                ])->execute();
                $transaction->commit();
            } catch (Exception $e) {
                $transaction->rollBack();
                Craft::$app->getSession()->setError($e->getMessage());
            }
        }
    }

    /**
     * Deletes all entries with corresponding field handle
     * @param Entry $entry with corresponding field handle
     * @param string $fieldHandle handle of field to be deleted
     * @return void
     * @throws MissingComponentException if required component is missing
     */
    public static function deleteEntry(Entry $entry, string $fieldHandle)
    {
        $db = Craft::$app->getDb();
        $transaction = $db->beginTransaction();
        try {
            $db->createCommand()->delete('{{%ellinika_update}}', [
                'entry_id' => $entry->id,
                'language_id' => $entry->siteId,
                'fieldHandle' => $fieldHandle,
            ])->execute();
            $transaction->commit();
        } catch (Exception $e) {
            $transaction->rollBack();
            Craft::$app->getSession()->setError($e->getMessage());
        }
    }

    /**
     * Deletes all entries with corresponding id
     * @param Entry $entry to be deleted
     * @return void
     * @throws MissingComponentException if required component is missing
     */
    public static function deleteEntryById(Entry $entry)
    {
        $db = Craft::$app->getDb();
        $transaction = $db->beginTransaction();
        try {
            $db->createCommand()->delete('{{%ellinika_update}}', [
                'entry_id' => $entry->id,
            ])->execute();
            $transaction->commit();
        } catch (Exception $e) {
            $transaction->rollBack();
            Craft::$app->getSession()->setError($e->getMessage());
        }
    }

    /**
     * Checks if entry for corresponding site id and field handle
     * is already in update table
     * @param int $entryId
     * @param int $siteId
     * @param $fieldHandle
     * @return bool true if in table, else false
     */
    public static function isInUpdateTable(int $entryId, int $siteId, $fieldHandle): bool
    {
        return (new Query())
            ->from('{{%ellinika_update}}')
            ->where([
                'entry_id' => $entryId,
                'language_id' => $siteId,
                'fieldHandle' => $fieldHandle,
            ])
            ->exists();
    }

    /**
     * Gets last update from entry with corresponding
     * site id and field handle
     * @param int $entryId
     * @param int $siteId
     * @param string $fieldHandle
     * @return string|null
     */
    public static function getLastUpdate(int $entryId, int $siteId, string $fieldHandle): ?string
    {
        return (new Query())
            ->select(['last_Update'])
            ->from('{{%ellinika_update}}')
            ->where([
                'entry_id' => $entryId,
                'language_id' => $siteId,
                'fieldHandle' => $fieldHandle,
            ])
            ->scalar();
    }
}