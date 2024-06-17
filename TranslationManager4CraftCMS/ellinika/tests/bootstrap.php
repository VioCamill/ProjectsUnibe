<?php

// Define path constants
define('CRAFT_BASE_PATH', __DIR__ . '/../../../');
define('CRAFT_VENDOR_PATH', CRAFT_BASE_PATH . 'vendor');

// Load Composer's autoloader
require_once CRAFT_VENDOR_PATH . '/autoload.php';

// Emulate a web environment
define('CRAFT_ENVIRONMENT', 'test');
$_SERVER['HTTP_HOST'] = 'localhost';
$_SERVER['REQUEST_URI'] = '/';
$_SERVER['SCRIPT_FILENAME'] = __FILE__;
$_SERVER['SCRIPT_NAME'] = __FILE__;

// Load Craft's web bootstrap
require_once CRAFT_VENDOR_PATH . '/craftcms/cms/bootstrap/web.php';

// Initialize Craft
Craft::$app->init();
