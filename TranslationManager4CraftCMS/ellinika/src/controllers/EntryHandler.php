<?php

namespace pse\craftellinika\controllers;

use craft\base\Field;
use craft\elements\Entry;
use craft\errors\InvalidFieldException;
use craft\errors\MissingComponentException;
use craft\fields\PlainText;
use craft\models\EntryType;
use pse\craftellinika\Plugin;
use DateTime;
use yii\base\InvalidConfigException;
use yii\db\Exception;


/**
 * EntryHandler checks an entry on multilingual websites
 * for inconsistencies in the updates
 *
 * @author pse24
 * @copyright devedis
 * @license MIT
 */
 class EntryHandler
 {
    private Entry $entry;
    private static array $changedFieldHandles = [];
    private DateTime $lastUpdate;

    /**
     * @param Entry $entry
     */
    public function __construct(Entry $entry)
    {
        $this->entry = $entry;
        $this->lastUpdate = $entry->dateUpdated;
    }

     /**
      * Processes an updated entry
      * @param bool $changed
      * @return void
      * @throws InvalidConfigException
      * @throws InvalidFieldException
      * @throws MissingComponentException
      * @throws \Exception
      */
    public function processEntry(bool $changed): void
    {
        $entryType = $this->entry->getType();
        $fields = self::getFields($entryType);
        if ($changed) {
            // Speichere geänderte Felder beim ersten Durchgang
            self::$changedFieldHandles = $this->entry->getDirtyFields();
        }
        // Iterates over the fields and gets their handles
        foreach ($fields as $field) {
            if (!$field instanceof PlainText){
                continue;
            }
            if (!in_array($field->handle, self::$changedFieldHandles)) {
                continue;
            }
            $fieldValue = $this->entry->getFieldValue($field->handle);
            if ($changed) {
                $inTable = EllinikaController::isInTable($this->entry->id, $this->entry->siteId, $field->handle);
                if (!$inTable) {
                    if ($this->isWithinTimeThreshold($field)) {
                        continue;
                    }
                }
                UpdateController::insertUpdateData($this->entry, $this->lastUpdate->format('Y-m-d H:i:s'), $field->handle);
            }
            $existingChanged = EllinikaController::checkEntryChanged($this->entry->id, $this->entry->siteId, $field->handle);
            $existingEntry = EllinikaController::entryIdExists($this->entry->id, $field->handle);
            // If 'changed' is true in the database or entry with an EntryId does not exist and the new 'changed' variable is false, do nothing
            if (!$changed && ($existingChanged || !$existingEntry)) {
                continue;
            }
            EllinikaController::insertEntryData($this->entry, $changed, $field->handle, $fieldValue);
            EllinikaController::deleteEntriesIfAllChangedTrue($this->entry->id, $field->handle);
        }
    }

    /**
     * Processes a fresh entry
     * @throws Exception
     * @throws InvalidConfigException
     * @throws MissingComponentException
     * @throws InvalidFieldException
     */
    public function processFreshEntry(): void
    {
        $fields = self::getFields($this->entry->getType());
        foreach ($fields as $field) {
            if (!$field instanceof PlainText){
                continue;
            }
            $fieldValue = $this->entry->getFieldValue($field->handle);
            EllinikaController::insertFreshEntry($this->entry, $field->handle, $fieldValue);
            $lastUpdate = $this->lastUpdate;
            UpdateController::insertFreshEntry($this->entry, $lastUpdate->format('Y-m-d H:i:s'), $field->handle);
        }
    }

     /**
      * Gets all fields connected to this entry
      * @param EntryType $entryType
      * @return array
      */

    private static function getFields(EntryType $entryType): array
    {
        $fieldLayout = $entryType->getFieldLayout();
        return $fieldLayout->getCustomFields();
    }

     /**
      * Checks if the entry should come into the table again
      * if new update is less than threshold time the entry is
      * not inserted again
      * @param Field $field
      * @return bool true if new update is less than threshold time set in settings
      */
    private function isWithinTimeThreshold(Field $field): bool
    {
        $lastUpdate = UpdateController::getLastUpdate($this->entry->id, $this->entry->siteId, $field->handle);
        $timeDiff = $this->getTimeDiff($this->lastUpdate->format('Y-m-d H:i:s'), $lastUpdate);
        $settings = Plugin::getInstance()->getSettings();
        $timeThreshold = $settings->timeThreshold;
        return $timeDiff < $timeThreshold;
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
        return (strtotime($changedUpdate) - strtotime($latestUpdate)) / 60;
    }
}