<?php

namespace pse\craftellinika\controllers;

use Cassandra\Date;
use Craft;
use craft\db\Query;
use craft\elements\conditions\TitleConditionRule;
use craft\elements\Entry;
use craft\web\Controller;
use craft\helpers\Db;
use pse\craftellinika\models\Ellinika;
use Stringy\Stringy;
use yii\db\Exception;
use yii\web\NotFoundHttpException;
use DateTime;
use DateTimeZone;

/**
 * EntryHandler checks an entry on multilingual websites
 * for inconsistencies in the updates
 *
 * @author Kevin Lautenschlager, Viola Meier, Daniele
 * @version  v1.1
 */

class EntryHandler
{

    /**
     * @var Entry
     */
    private Entry $entry;
    private DateTime $lastUpdate;

    /**
     * Constructs an EntryHandler object
     * @param Entry $entry which triggered EVENT_AFTER_SAFE
     *
     * @throws \Exception
     */
    public function __construct(Entry $entry)
    {
        $this->entry = $entry;
        $this->lastUpdate = $entry->dateUpdated;
    }


    /**
     * Processes the entry, checks translations, and sets flash message
     * @throws \Exception
     */
    public function processEntry($changed): void
    {
        if ($changed) {
            $inTable = EllinikaController::isInTable($this->entry->id,$this->entry->siteId);
            if(!$inTable){
                $lastUpdate = UpdateController::getLastUpdate($this->entry->id,$this->entry->siteId);
                $timeDiff = $this->getTimeDiff($this->lastUpdate->format('Y-m-d H:i:s'), $lastUpdate);
                if($timeDiff < 1){
                    return;
                }
            }
            UpdateController::insertUpdateData($this->entry, $this->lastUpdate->format('Y-m-d H:i:s'));
        }

        $existingChanged = EllinikaController::checkEntryChanged($this->entry->id, $this->entry->siteId);
        $existingEntry = EllinikaController::entryIdExists($this->entry->id);
        // If 'changed' is true in the database or entry with an EntryId does not exist and the new 'changed' variable is false, do nothing
        if ($changed == false && ($existingChanged == true || $existingEntry==false)) {
            return;
        }

        EllinikaController::insertEntryData($this->entry, $changed);
    }
    public function processFreshEntry()
    {
        EllinikaController::insertFreshEntry($this->entry, true);
        $lastUpdate = $this->lastUpdate;
        UpdateController::insertFreshEntry($this->entry, $lastUpdate->format('Y-m-d H:i:s'));
    }


    /**
     * Retrieves the content of an entry as a string.
     *
     * @param Entry $entry The entry to retrieve content from.
     * @return string The content string.
     */
    public static function getContentAsString(Entry $entry): ?string
    {
        $entryType = $entry->getType();
        $content = "Default";
        $isSingle = self::isSingleEntry($entry);
        if($isSingle){
            $content = "No existing fields";
        } else {
            // Checks if the entry type exists
            if ($entryType) {

                // Gets the field layout of the entry type
                $fieldLayout = $entryType->getFieldLayout();

                // Gets all fields from the field layout
                $fields = $fieldLayout->getCustomFields();

                // Iterates over the fields and gets their handles
                foreach ($fields as $field) {
                    echo $field->handle;
                }
            }
            if(!$entry->getFieldValue($field->handle) == null){
                $content = $entry->getFieldValue($field->handle);
            }
        }
        return $content;
    }

    public static function isSingleEntry(Entry $entry): bool
    {
        $section = $entry->getSection();

        if ($section && $section->type === \craft\models\Section::TYPE_SINGLE) {
            return true;
        }
        return false;
    }

    /**
     * Calculates the time difference between two dates.
     *
     * @param string $changedUpdate The first date.
     * @param string $latestUpdate The second date as a string.
     * @return float
     */
    private function getTimeDiff(string $changedUpdate, string $latestUpdate): float
    {

        $min_diff = (strtotime($changedUpdate) - strtotime($latestUpdate)) / 60;
        return $minutes = $min_diff;
    }
}

