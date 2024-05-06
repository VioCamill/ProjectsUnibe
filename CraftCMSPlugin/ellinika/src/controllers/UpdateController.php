<?php

namespace pse\craftellinika\controllers;

use Craft;
use craft\db\Query;
use craft\elements\Entry;
use DateTime;
use yii\db\Exception;
use yii\web\Controller;
use yii\web\NotFoundHttpException;

class UpdateController extends Controller
{
    public static function insertUpdateData(Entry $entry, String $lastUpdate): void
    {   $db = Craft::$app->getDb();
        $inTable = UpdateController::isInUpdateTable($entry->id, $entry->siteId);
        if($inTable){
            UpdateController::deleteEntry($entry);
        }
        $db->createCommand() ->insert('{{%update_db}}', [
            'entry_id' => $entry->id,
            'language_id' => $entry->siteId,
            'last_Update' => $lastUpdate
        ])->execute();
    }

    public static function insertFreshEntry(Entry $entry, string $lastUpdate)
    {
        $supportedSites = $entry->getSupportedSites();
        $siteIds = array_column($supportedSites, 'siteId');
        $db = Craft::$app->getDb();
        foreach ($siteIds as $siteId) {
            $db->createCommand()->insert('{{%update_db}}', [
                'entry_id' => $entry->id,
                'language_id' => $siteId,
                'last_Update' => $lastUpdate,
            ])->execute();
        }
    }

    public static function deleteEntry(Entry $entry)
    {   $db = Craft::$app->getDb();
        $db->createCommand()->delete('{{%update_db}}', [
            'entry_id' => $entry->id,
            'language_id' => $entry->siteId
        ])->execute();

    }

    public static function deleteEntryById(Entry $entry){
        $db = Craft::$app->getDb();
        $db->createCommand()->delete('{{%update_db}}', [
            'entry_id' => $entry->id,
        ])->execute();
    }

    public static function isInUpdateTable(int $entryId, int $siteId): bool
    {
        $exists = (new Query())
            ->from('{{%update_db}}')
            ->where([
                'entry_id' => $entryId,
                'language_id' => $siteId,
            ])
            ->exists();
        return $exists;
    }



        /**
     * @throws Exception
     */
    public static function getLastUpdate(int $entryId, int $siteId): ?string
    {
        $lastUpdate = (new Query())
            ->select(['last_Update'])
            ->from('{{%update_db}}')
            ->where([
                'entry_id' => $entryId,
                'language_id' => $siteId,
            ])
            ->scalar();
        return $lastUpdate;
    }

}