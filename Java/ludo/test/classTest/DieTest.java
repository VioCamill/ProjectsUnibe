package classTest;

import ludo.Die;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DieTest {

    //checks if the result is always between 1 and 6
    @Test
    public void checkResultOfDie() {

        Die die = new Die(6);
        for (int i = 0; i<1000; i++) {
            int temp = die.roll();
            Assertions.assertTrue(temp>=1&&temp<=6);
        }

    }

    //rolls 10000 times the dice to check if the expected value is close enough to 3.5
    @Test
    public void checkDistribution() {

        Die die = new Die(6);
        int temp = 0;
        for (int i = 0; i < 10000; i++) {
            temp +=die.roll();

        }
        Assertions.assertTrue(temp >= 34500 && temp <= 35500);
    }






}
