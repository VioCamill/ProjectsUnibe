<?php

namespace pse\craftellinika\controllers;

use Craft;
use craft\db\Query;
use craft\elements\Entry;
use craft\web\Controller;
use craft\helpers\DateTimeHelper;
use pse\craftellinika\models\Settings;
use yii\base\InvalidConfigException;
use yii\db\Exception;
use yii\web\NotFoundHttpException;
use DateTime;
use DateTimeZone;

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
    public static function insertEntryData(Entry $entry, bool $changed, string $fieldHandle, ?string $textFieldHandle): void
    {
        $db = Craft::$app->getDb();
        $transaction = $db->beginTransaction();
        $currentTimestamp = (new DateTime('now', new DateTimeZone(Craft::$app->getTimeZone())))->format('Y-m-d H:i:s');

        try {
            $params = ['entry_id' => $entry->id, 'language_id' => $entry->siteId, 'fieldHandle' => $fieldHandle];
            $updateFields = [
                'text' => $textFieldHandle,
                'url' => $entry->getCpEditUrl(),
                'changed' => $changed,
                'dateCreated' => $currentTimestamp,
            ];

            if (self::isInTable($entry->id, $entry->siteId, $fieldHandle)) {
                $db->createCommand()->update('{{%ellinika_ui}}', $updateFields, $params)->execute();
            } else {
                // Call insertFreshEntry with appropriate parameters
                self::insertFreshEntry($entry, $fieldHandle, $textFieldHandle);
            }
            $transaction->commit();
            self::deleteField($entry->id, $fieldHandle);

        } catch (\Exception $e) {
            $transaction->rollBack();
            Craft::$app->getSession()->setError('Failed to save entry data: ' . $e->getMessage());
            throw $e;
        }
    }






    /**
     * Checks if all 'changed' statuses for a given entry ID and field handle are true.
     *
     * @param int $entryId The ID of the entry.
     * @param string $fieldHandle The handle of the field.
     * @return bool Whether all 'changed' statuses are true for the entry and field.
     */
    public static function checkAllChangedTrue(int $entryId, string $fieldHandle): bool
    {
        $db = Craft::$app->getDb();
        $countTotal = (new Query())
            ->from('{{%ellinika_ui}}')
            ->where([
                'entry_id' => $entryId,
                'fieldHandle' => $fieldHandle
            ])
            ->count();

        $countChangedTrue = (new Query())
            ->from('{{%ellinika_ui}}')
            ->where([
                'entry_id' => $entryId,
                'fieldHandle' => $fieldHandle,
                'changed' => true
            ])
            ->count();

        return $countTotal > 0 && $countTotal == $countChangedTrue;
    }

    /**
     * Inserts Entry into the table when new created
     * @throws Exception
     * @throws InvalidConfigException
     */
    public static function insertFreshEntry(Entry $entry, string $fieldHandle, ?string $textFieldHandle)
    {
        $supportedSites = Craft::$app->sites->getAllSites();
        $author = Craft::$app->user->identity;
        $db = Craft::$app->getDb();
        $currentTimestamp = (new DateTime('now', new DateTimeZone(Craft::$app->getTimeZone())))->format('Y-m-d H:i:s');

        foreach ($supportedSites as $site) {

            $siteId = $site->id;
            $entryInSite = Entry::find()
                ->id($entry->id)
                ->siteId($siteId)
                ->one();
            $changed = ($siteId == $entry->siteId);

            $db->createCommand()->insert('{{%ellinika_ui}}', [
                'entry_id' => $entry->id,
                'language_id' => $siteId,
                'text' => $textFieldHandle,
                'url' => $entryInSite->getCpEditUrl(),
                'changed' => $changed,
                'author' => $author,
                'title' => $entry->title,
                'fieldHandle' => $fieldHandle,
                'dateCreated' => $currentTimestamp,
            ])->execute();
        }
    }

    /**
     * Deletes an entry and its associated field data from the custom table based on entry ID and field handle,
     * only if all supported languages have marked the entry as changed.
     *
     * @param int $entryId The ID of the entry to delete.
     * @param string $fieldHandle The field handle associated with the entry.
     * @throws \yii\db\Exception If there is an error during the database operation.
     */
    public static function deleteField(int $entryId, string $fieldHandle): void
    {
        // If all 'changed' statuses are true, delete entries
        if (EllinikaController::checkAllChangedTrue($entryId, $fieldHandle)) {
            EllinikaController::deleteInDB();
        }
    }

    /**
     * Deletes a field with the button in the GUI
     */
    public function actionDeleteField()
    {
        EllinikaController::deleteInDB();
        return $this->actionIndex();
    }

    /**
     *General delete function
     */
    public static function deleteInDB()
    {
        $request = Craft::$app->getRequest();
        $entryId = $request->getBodyParam('entryId');
        $fieldHandle = $request->getBodyParam('fieldHandle');

        $db = Craft::$app->getDb();
        $transaction = $db->beginTransaction();
        try {
            $db->createCommand()->delete('{{%ellinika_ui}}', ['entry_id' => $entryId, 'fieldHandle' => $fieldHandle])->execute();

            $transaction->commit();
            Craft::$app->getSession()->setNotice('Entry and associated field data successfully deleted.');
        } catch (\Exception $e) {
            $transaction->rollBack();
            Craft::$app->getSession()->setError('Failed to delete entries: ' . $e->getMessage());
        }
    }


    /**
     * Deletes whole entry with the id (in craft itself)
     */

    public static function deleteEntryById(Entry $entry): void
    {
        $db = Craft::$app->getDb();
        try {
            $db->createCommand()->delete('{{%ellinika_ui}}', ['entry_id' => $entry->id])->execute();
            //Craft::$app->getSession()->setNotice('Entry deleted successfully.'); not needed
        } catch (\Exception $e) {
            Craft::$app->getSession()->setError('Failed to delete entry: ' . $e->getMessage());
        }
    }

    /**
     * Checks if an entry exists in the 'ellinika_db' table.
     *
     * @param int $entryId The ID of the entry.
     * @param int $siteId The ID of the site.
     * @return bool Whether the entry exists in the table.
     */
    public static function isInTable(int $entryId, int $siteId, string $fieldHandle): bool
    {
        // Check if an entry exists in the 'ellinika_db' table
        $exists = (new Query())
            ->from('{{%ellinika_ui}}')
            ->where([
                'entry_id' => $entryId,
                'language_id' => $siteId,
                'fieldHandle' => $fieldHandle,
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
    public static function entryIdExists(int $entryId, string $fieldHandle): bool
    {
        // Check if an entry exists in the database
        $exists = (new Query())
            ->from('{{%ellinika_ui}}')
            ->where([
                'entry_id' => $entryId,
                'fieldHandle' => $fieldHandle,
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
    public static function checkEntryChanged(int $entryId, int $siteId, string $fieldHandle): ?bool
    {
        // Get the 'changed' value from the database
        $alreadyChanged = (new Query())
            ->select(['changed'])
            ->from('{{%ellinika_ui}}')
            ->where([
                'entry_id' => $entryId,
                'language_id' => $siteId,
                'fieldHandle' => $fieldHandle,
            ])
            ->scalar();

        return $alreadyChanged;
    }

    /**
     * Deletes all entries for a given entry ID from the 'ellinika_db' if all 'changed' are true.
     *
     * @param int $entryId The ID of the entry to delete.
     */
    public static function deleteEntriesIfAllChangedTrue(int $entryId, string $fieldHandle): void
    {
        // If all 'changed' statuses are true, delete entries
        if (EllinikaController::checkAllChangedTrue($entryId, $fieldHandle)) {
            $db = Craft::$app->getDb();
            $transaction = $db->beginTransaction();
            try {
                $db->createCommand()
                    ->delete('{{%ellinika_ui}}', ['entry_id' => $entryId, 'fieldHandle' => $fieldHandle])
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
        $rawEntries = $this->fetchAllEntries();
        $entriesByEntryId = $this->processEntries($rawEntries);
        return $this->renderIndexPage($entriesByEntryId);
    }

    /**
     * Fetch all entries from the database.
     *
     * @return array An array of raw entry data.
     */
    private function fetchAllEntries(): array
    {
        return (new Query())
            ->select(['entry_id', 'language_id', 'fieldHandle', 'text', 'url', 'changed', 'author', 'dateCreated', 'title'])
            ->distinct(true)
            ->from('{{%ellinika_ui}}')
            ->all(Craft::$app->db);
    }

    /**
     * Process raw entries to structure them by entry ID and field handle.
     *
     * @param array $rawEntries Array of raw entry data from the database.
     * @return array Processed entries structured for display.
     */
    private function processEntries(array $rawEntries): array
    {
        $entriesByEntryId = [];

        foreach ($rawEntries as $entry) {
            $entryId = $entry['entry_id'];
            if (!isset($entriesByEntryId[$entryId])) {
                $entriesByEntryId[$entryId] = [
                    'title' => $entry['title'],
                    'author' => $entry['author'],
                    'dateCreated' => $entry['dateCreated'],
                    'fields' => [],
                ];
            }

            $this->processField($entriesByEntryId[$entryId], $entry);
        }

        return $entriesByEntryId;
    }

    /**
     * Process individual fields and organize texts, URLs, and language changes.
     *
     * @param array &$entry Reference to the entry array being processed.
     * @param array $rawEntry The raw entry data from the database.
     */
    private function processField(array &$entry, array $rawEntry): void
    {
        $fieldHandle = $rawEntry['fieldHandle'];
        // Initialize field if not already
        if (!isset($entry['fields'][$fieldHandle])) {
            $entry['fields'][$fieldHandle] = [
                'texts' => [],
                'urls' => [],
                'changedLanguages' => [],
                'unchangedLanguages' => []
            ];
        }

        // Aggregate texts and URLs
        $entry['fields'][$fieldHandle]['texts'][$rawEntry['language_id']] = $rawEntry['text'];
        $entry['fields'][$fieldHandle]['urls'][$rawEntry['language_id']] = $rawEntry['url'];

        // Distribute languages into 'changed' and 'unchanged'
        if ($rawEntry['changed']) {
            if (!in_array($rawEntry['language_id'], $entry['fields'][$fieldHandle]['changedLanguages'])) {
                $entry['fields'][$fieldHandle]['changedLanguages'][] = $rawEntry['language_id'];
            }
        } else {
            if (!in_array($rawEntry['language_id'], $entry['fields'][$fieldHandle]['unchangedLanguages'])) {
                $entry['fields'][$fieldHandle]['unchangedLanguages'][] = $rawEntry['language_id'];
            }
        }
    }

    /**
     * Render the index page template with the structured entries.
     *
     * @param array $entriesByEntryId Structured entries for display.
     * @return mixed The rendered template of the index page.
     */
    private function renderIndexPage(array $entriesByEntryId)
    {
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
            'SELECT * FROM {{%ellinika_ui}}'
        )->queryAll();

        return $entries;
    }

    /**
     * Updates the content of a field for a specified entry.
     *
     * @param int $entryId The ID of the entry to update.
     * @param string $textFieldHandle The handle of the text field to update.
     * @param string $newText The new text to set for the field.
     * @return bool Whether the update was successful.
     * @throws NotFoundHttpException If the entry is not found.
     */
    public static function updateFieldContent(int $entryId, string $textFieldHandle, string $newText): bool
    {
        $entry = Craft::$app->getElements()->getElementById($entryId, Entry::class);
        if (!$entry) {
            Craft::$app->getSession()->setError('Entry not found.');
            return false;
        }

        $entry->setFieldValue($textFieldHandle, $newText);
        if (!Craft::$app->getElements()->saveElement($entry)) {
            Craft::$app->getSession()->setError('Failed to update the entry.');
            return false;
        }

        Craft::$app->getSession()->setNotice('Entry updated successfully.');
        return true;
    
    }

    /**
     * Updates the content of a specific field for an entry in a given language.
     *
     * @param int $entryId The ID of the entry to update.
     * @param string $fieldHandle The handle of the field to update.
     * @param string $languageId The language ID for which to update the content.
     * @param string $newText The new text content for the field.
     * @return array Whether the update was successful.
     */
    public function actionUpdateFieldContent()
    {
        $this->requirePostRequest();
        $entryId = Craft::$app->getRequest()->getBodyParam('entryId');
        $fieldHandle = Craft::$app->getRequest()->getBodyParam('fieldHandle');
        $languageId = Craft::$app->getRequest()->getBodyParam('languageId'); // Get the language ID from the request
        $newText = Craft::$app->getRequest()->getBodyParam('newText');
        Craft::$app->response->format = \craft\web\Response::FORMAT_JSON; // Ensure the response is in JSON format

        // Fetch the entry for the specific language
        $entry = Craft::$app->getElements()->getElementById($entryId, null, $languageId);
        if (!$entry) {
            throw new NotFoundHttpException('Entry not found.');
        }

        // Set the value of the field for the specified language
        $entry->setFieldValue($fieldHandle, $newText);

        // Save the entry, specifying the site ID (language)
        if (!Craft::$app->getElements()->saveElement($entry)) {
            return [
                'success' => false,
                'message' => 'Failed to update the entry.'
            ];
        }

        return [
            'success' => true,
            'message' => 'Entry updated successfully.'
        ];
    }


    public function actionSaveSettings()
    {
        $this->requirePostRequest();
        $settings = new Settings();
        $settings->timeThreshold = Craft::$app->getRequest()->getBodyParam('timeThreshold');
        $settings->timeUnit = Craft::$app->getRequest()->getBodyParam('timeUnit');

        // Convert timeThreshold to minutes if necessary
        switch ($settings->timeUnit) {
            case 'hours':
                $settings->timeThreshold *= 60;
                break;
            case 'days':
                $settings->timeThreshold *= 1440;
                break;
        }

        if (!Craft::$app->plugins->savePluginSettings(Craft::$app->plugins->getPlugin('ellinika'), $settings->toArray())) {
            Craft::$app->getSession()->setError('Couldn’t save settings.');
            return null;
        }

        Craft::$app->getSession()->setNotice('Settings saved.');
        return $this->redirectToPostedUrl();
    }
}