package snakes.playTest;


import snakes.IDie;

/**
 * Mock class mimicking a Die Object, but returning set ints at set positions. Can only be used for the specific test
 * PlayTest#playTestMockDie
 *
 * @author Tim Ambühl
 */

public class MockDie implements IDie {

    private int index;

    private int[] rolls = {1, 2, 1, 1, 5, 1, 1};

    public MockDie() {
        this.index = -1;
    }

    @Override
    public int roll() {

        this.index++;
        return rolls[index];
    }
}
