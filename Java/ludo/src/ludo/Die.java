package ludo;


/**
 * Die class that simulates a die with as many sides as wished
 */
public class Die {

    protected final int sides;

    /**
     * Creates a die with 6 sides
     */
    public Die() {
        this.sides = 6;
    }

    /**
     * Creates a die with the given number of sides
     * @param sides the number of sides of the die
     */
    public Die(int sides) {
        assert (sides > 0);
        this.sides = sides;
    }

    /**
     * Rolls the die and returns the result
     * @return the result of the roll
     */
    public int roll() {
        int result = 1 + (int) (sides * Math.random());
        assert (result >= 1 && result <= sides);
        return result;
    }




}
