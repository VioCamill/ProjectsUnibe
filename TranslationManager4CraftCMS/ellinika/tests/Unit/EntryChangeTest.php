<?php


namespace pse\craftellinika\tests;

use PHPUnit\Framework\TestCase;
use pse\craftellinika\controllers\EllinikaController;
use Craft;
use craft\elements\Entry;
use craft\services\Elements;
use yii\base\InvalidConfigException;
use yii\web\NotFoundHttpException;

class EntryChangeTest extends TestCase
{
    protected $controller;
    protected $elementsService;

    protected function setUp(): void
    {
        parent::setUp();
        $this->controller = new EllinikaController('ellinika', Craft::$app);

        // Mock the Elements service in Craft
        $this->elementsService = $this->createMock(Elements::class);
        Craft::$app->set('elements', $this->elementsService);
    }

    public function testUpdateFieldContent()
    {
        $entryId = 123; // Beispiel ID
        $textFieldHandle = 'myTextField';
        $newText = 'Hello World';

        // Mocking an Entry Element
        $entryMock = $this->createMock(Entry::class);
        $entryMock->method('setFieldValue')->with($textFieldHandle, $newText);
        $entryMock->expects($this->once())->method('setFieldValue')->with($textFieldHandle, $newText);

        $this->elementsService->method('getElementById')->willReturn($entryMock);
        $this->elementsService->expects($this->once())->method('getElementById')->with($entryId, Entry::class)->willReturn($entryMock);
        $this->elementsService->method('saveElement')->willReturn(true);

        // Assert that updateFieldContent returns true on successful update
        $result = $this->controller->updateFieldContent($entryId, $textFieldHandle, $newText);
        $this->assertTrue($result);
    }

    protected function tearDown(): void
    {
        parent::tearDown();
        Craft::$app->set('elements', null);
    }
}