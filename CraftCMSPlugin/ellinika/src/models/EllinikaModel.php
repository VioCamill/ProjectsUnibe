<?php

namespace pse\craftellinika\models;

use craft\base\Model;

/**
 * EllinikaModel is a Model for the migration for this plugin
 * 
 * EllinikaModel specifies the format of the table in the database of Craft CMS
 * 
 * @author      Dario Häfliger
 * @version     v1.0
 */
class EllinikaModel extends Model
{
    public $id;
    public $entry_id;
    public $text;
    public $url;
}
