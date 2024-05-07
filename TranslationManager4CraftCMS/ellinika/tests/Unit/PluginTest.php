<?php

declare(strict_types = 1);

namespace Tests\Unit;

use PHPUnit\Framework\TestCase;

class PluginTest extends TestCase
{
    // Tests methods of Plugin.php
    /**
     * for instance:
     * public function handles_events(): void
     * {
     *  assert(...)
     * }
     * 
     * Test methods: Given - When - Then, Arrange - Act - Assert
     */

    /** @test Returns true for testing purposes*/
    public function returnTrue(): void
    {
        $expected = true;
        $this->assertEquals($expected, true);
    }
}