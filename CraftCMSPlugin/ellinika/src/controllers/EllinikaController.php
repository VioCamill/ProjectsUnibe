<?php

namespace pse\craftellinika\controllers;

use Craft;
use craft\db\Query;
use craft\elements\Entry;
use craft\helpers\UrlHelper;
use craft\web\Controller;
use craft\helpers\Db;
use pse\craftellinika\models\Ellinika;
use Stringy\Stringy;
use yii\db\Exception;
use yii\web\NotFoundHttpException;


/**
 * Handles data manipulation and interactions for the Ellinika plugin.
 * This controller manages database operations such as retrieving, inserting, and deleting entries.
 * 
 * @author Daniele De Jeso
 * @version 1.2
 */
class EllinikaController extends Controller
{

    /**
     * Inserts entry data into the plugin's database table.
     * This method is called as part of the event handling for entry saving,
     * inserting relevant data from the entry into the 'ellinika_db' table.
     *
     * @param Entry $entry The entry that has been saved.
     */
    public static function insertEntryData(Entry $entry,  bool $changed): void
    {
        // Check if entry exists in table
        $inTable = EllinikaController::isInTable($entry -> id, $entry ->siteId);

        // Delete entry if already in table
        if($inTable){
            EllinikaController::deleteEntry($entry);
        }
        $db = Craft::$app->getDb();
        $db->createCommand()->insert('{{%ellinika_db}}', [
            'entry_id' => $entry->id,
            'language_id' => $entry->siteId,
            'text' => EntryHandler::getContentAsString($entry),
            'url' => $entry->getCpEditUrl(),
            'changed' => $changed,
        ])->execute();

        // Delete entries if all 'changed' are true
        EllinikaController::deleteEntriesIfAllChangedTrue($entry->id);

    }

    public static function insertFreshEntry(Entry $entry, bool $changed)
    {
        $db = Craft::$app->getDb();
        $supportedSites = $entry->getSupportedSites();
        $siteIds = array_column($supportedSites, 'siteId');
        foreach ($siteIds as $siteId) {
                if($siteId != $entry->siteId){
                    $changed = false;
                }
                $db->createCommand()->insert('{{%ellinika_db}}', [
                    'entry_id' => $entry->id,
                    'language_id' => $siteId,
                    'text' => EntryHandler::getContentAsString($entry),
                    'url' => $entry->getCpEditUrl(),
                    'changed' => $changed
                ])->execute();
        }

    }

    public static function deleteEntry(Entry $entry): void
    {   $db = Craft::$app->getDb();
        $db->createCommand()->delete('{{%ellinika_db}}', [
            'entry_id' => $entry->id,
            'language_id' => $entry->siteId
        ])->execute();
    }

    public static function deleteEntryById(Entry $entry): void
    {
        $db = Craft::$app->getDb();
        $db->createCommand()->delete('{{%ellinika_db}}', [
            'entry_id' => $entry->id
        ])->execute();
    }

    /**
     * Checks if an entry exists in the 'ellinika_db' table.
     *
     * @param int $entryId The ID of the entry.
     * @param int $siteId The ID of the site.
     * @return bool Whether the entry exists in the table.
     */
    public static function isInTable(int $entryId, int $siteId): bool
    {
        // Check if an entry exists in the 'ellinika_db' table
        $exists = (new Query())
            ->from('{{%ellinika_db}}')
            ->where([
                'entry_id' => $entryId,
                'language_id' => $siteId,
            ])
            ->exists();
        return $exists;
    }

    /**
     * Checks if an entry with the given ID exists in the database.
     *
     * @param int $entryId The ID of the entry.
     * @return bool Whether the entry exists.
     */
    public static function entryIdExists(int $entryId): bool
    {
        // Check if an entry exists in the database
        $exists = (new Query())
            ->from('{{%ellinika_db}}')
            ->where([
                'entry_id' => $entryId
            ])
            ->exists();

        return $exists;
    }

    /**
     * Checks the 'changed' status of an entry.
     *
     * @param int $entryId The ID of the entry.
     * @param int $siteId The ID of the site.
     * @return ?bool The 'changed' status of the entry.
     */
    public static function checkEntryChanged(int $entryId, int $siteId): ?bool
    {
        // Get the 'changed' value from the database
        $alreadyChanged = (new Query())
            ->select(['changed'])
            ->from('{{%ellinika_db}}')
            ->where([
                'entry_id' => $entryId,
                'language_id' => $siteId,
            ])
            ->scalar();

        return $alreadyChanged;
    }

    /**
     * Checks if all 'changed' statuses for a given entry ID are true.
     *
     * @param int $entryId The ID of the entry.
     * @return bool Whether all 'changed' statuses are true.
     */
    public static function checkAllChangedTrue(int $entryId): bool
    {
        // Get database instance
        $db = Craft::$app->getDb();
        // Count total and changed entries
        $countTotal = (new Query())
            ->from('{{%ellinika_db}}')
            ->where(['entry_id' => $entryId])
            ->count();
        $countChangedTrue = (new Query())
            ->from('{{%ellinika_db}}')
            ->where([
                'entry_id' => $entryId,
                'changed' => true
            ])
            ->count();

        return $countTotal > 1 && $countTotal == $countChangedTrue;
    }

    /**
     * Deletes all entries for a given entry ID from the 'ellinika_db' if all 'changed' are true.
     *
     * @param int $entryId The ID of the entry to delete.
     */
    public static function deleteEntriesIfAllChangedTrue(int $entryId): void
    {
        // If all 'changed' statuses are true, delete entries
        if (EllinikaController::checkAllChangedTrue($entryId)) {
            $db = Craft::$app->getDb();
            $transaction = $db->beginTransaction();
            try {
                $db->createCommand()
                    ->delete('{{%ellinika_db}}', ['entry_id' => $entryId])
                    ->execute();

                $transaction->commit();
            } catch (\Exception $e) {
                $transaction->rollBack();
                throw $e;
            }
        }
    }

    /**
     * Renders the index page with a table displaying all entries.
     * It dynamically provides variable representation for rows and columns,
     * reflecting any additions or deletions of rows/columns.
     * 
     * @return mixed The rendered template of the index page.
     */
    public function actionIndex()
    {
        $entries = (new Query())
            ->select(['entry_id', 'text', 'language_id', 'url', 'changed'])
            ->from('{{%ellinika_db}}')
            ->all(Craft::$app->db);
    
        $entriesByEntryId = [];
    
        foreach ($entries as $entry) {
            if (!isset($entriesByEntryId[$entry['entry_id']])) {
                $entriesByEntryId[$entry['entry_id']] = [
                    'text' => $entry['text'],  // Speichert den Text, unabhängig vom Status
                    'unchangedLanguages' => [],
                    'changedLanguages' => [],  // Fügt eine neue Liste für geänderte Sprachen hinzu
                    'url' => $entry['url']
                ];
            }
    
            if (!$entry['changed']) {
                $entriesByEntryId[$entry['entry_id']]['unchangedLanguages'][] = $entry['language_id'];
            } else {
                $entriesByEntryId[$entry['entry_id']]['changedLanguages'][] = $entry['language_id'];
            }
        }
    
        return $this->renderTemplate('ellinika/index', [
            'entries' => $entriesByEntryId
        ]);
    }  


    /**
     * Deletes an entry from the database based on the provided ID and refreshes the index page.
     * 
     * @param int $id The ID of the entry to delete.
     * @throws NotFoundHttpException If the entry is not found.
     * @return mixed The updated index page without the deleted entry.
     */
    public function actionDeleteEntry()
    {
        $this->requirePostRequest();
        $entryId = Craft::$app->request->getRequiredBodyParam('entryId');

        $transaction = Craft::$app->getDb()->beginTransaction();
        try {
            Craft::$app->getDb()->createCommand()
                ->delete('{{%ellinika_db}}', ['entry_id' => $entryId])
                ->execute();

            $transaction->commit();
            Craft::$app->getSession()->setNotice('Entries successfully deleted.');
        } catch (\Throwable $e) {
            $transaction->rollBack();
            Craft::$app->getSession()->setError('Failed to delete entries: ' . $e->getMessage());
            return $this->redirect('ellinika/index');
        }

        // Abrufen der verbleibenden Einträge nach dem Löschen und Aufbereitung wie in actionIndex
        $entries = (new Query())
            ->select(['entry_id', 'text', 'language_id', 'url', 'changed'])
            ->from('{{%ellinika_db}}')
            ->all(Craft::$app->db);

        $entriesByEntryId = [];

        foreach ($entries as $entry) {
            if (!isset($entriesByEntryId[$entry['entry_id']])) {
                $entriesByEntryId[$entry['entry_id']] = [
                    'text' => $entry['text'],
                    'unchangedLanguages' => [],
                    'changedLanguages' => [],
                    'url' => $entry['url']
                ];
            }

            if (!$entry['changed']) {
                $entriesByEntryId[$entry['entry_id']]['unchangedLanguages'][] = $entry['language_id'];
            } else {
                $entriesByEntryId[$entry['entry_id']]['changedLanguages'][] = $entry['language_id'];
            }
        }

        // Weiterleiten zur Index-Seite mit den verbleibenden Einträgen
        return $this->renderTemplate('ellinika/index', [
            'entries' => $entriesByEntryId
        ]);
    }


    /**
     * Retrieves the language for a given site ID.
     *
     * @param int $siteId The ID of the site.
     * @return string The language of the site.
     * @throws Exception If the site ID does not exist.
     */
    public function getLanguageBySiteId(int $siteId): string
    {
        // Find the site by its ID
        $site = Site::find()->id($siteId)->one();

        if (!$site) {
            throw new \Exception("Site with ID $siteId does not exist.");
        }

        // Return the language code of the site
        return $site->language;
    }


    /**
     * Retrieves all entries from the database.
     *
     * @return array An array of all entries in the database.
     */
    public function getAllEntries()
    {
        $entries = Craft::$app->getDb()->createCommand(
            'SELECT * FROM {{%ellinika_db}}'
        )->queryAll();

        return $entries;
    }
}
