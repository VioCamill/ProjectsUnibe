<?php

namespace pse\craftellinika\models;

use Craft;
use craft\base\Model;

/**
 * Settings is a Model for the migration for this plugin
 * Settings specifies the format of the table in the database of Craft CMS
 * 
 *
 * @author pse24
 * @copyright pse24
 * @license MIT
 */
class Settings extends Model
{
    public $charactersToShow = 100; // Startvalue
    public $timeThreshold = 1; // minute
    public $timeUnit = 'minutes';    // default time unit

    /**
     * Defines the validation rules for the model's attributes.
     * 
     * This function specifies the rules for validating the attributes of this model:
     * - `charactersToShow` must be an integer
     * - `timeThreshold` must be an integer
     * - `timeUnit` must be one of the specified values ('minutes', 'hours', or 'days')
     * - `charactersToShow`, `timeThreshold`, and `timeUnit` are required fields
     *
     * @return array The validation rules
     */
    public function rules():array
    {
        return [
            ['charactersToShow', 'integer'],
            ['timeThreshold', 'integer'],
            ['timeUnit', 'in', 'range' => ['minutes', 'hours', 'days']],
            [['charactersToShow', 'timeThreshold', 'timeUnit'], 'required'],
        ];
    }
}