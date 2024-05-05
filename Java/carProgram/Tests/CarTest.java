
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarTest {

    int[] testLeft = {0,0,0,0};
    int[] testRight = {1,0,0,0};
    int[] testForward10 = {2,10,0,0};
    int[] testBackwards10 = {3,10,0,0};
    int[] testDrive80_20 = {4,0,80,20};

    int[] turnLeft = {0,0,0,0};
    int[] turnRight = {1,0,0,0};

    int[] moveForward10 = {2,10,0,0};
    int[] moveForward20 = {2,20,0,0};
    int[] moveBackwards10 = {3,10,0,0};
    int[] moveBackwards20 = {3,20,0,0};

    int[] drive60_60 = {4,0,60,60};
    int[] drive80_20 = {4,0,80,20};

    @Test
    public void carAddMoveLeftTest() {
        Car testCar = new Car();


        testCar.addMove(this.testLeft);


        assertTrue(testCar.getDirection() == 3);


    }
    @Test
    public void carAddMoveRightTest() {
        Car testCar = new Car();

        testCar.addMove(this.testRight);

        assertTrue(testCar.getDirection() == 1);
    }


    @Test
    public void carAddMoveForwardTest() {
        Car testCar = new Car();

        testCar.addMove(this.testForward10);

        assertTrue(testCar.pos()[1] == 59 && testCar.pos()[0] == 49 && testCar.pos()[2] == 0);
    }

    @Test
    public void carAddMoveBackwardsTest() {
        Car testCar = new Car();

        testCar.addMove(this.testBackwards10);

        assertTrue(testCar.pos()[1] == 39 && testCar.pos()[0] == 49 && testCar.pos()[2] == 0);


    }
    @Test
    public void carAddMoveDrive80_20Test() {
        Car testCar = new Car();

        testCar.addMove(this.testDrive80_20);

        assertTrue(testCar.pos()[0] == 80 && testCar.pos()[1] == 20 && testCar.pos()[2] == 0);
    }

    @Test
    public void carMovementTest1() {
        Car car = new Car();


        car.addMove(moveForward10);
        System.out.println(car.pos()[1]);
        assertTrue(car.pos()[1] == 59);
        car.addMove(turnLeft);
        assertTrue(car.getDirection() == 3);
        car.addMove(moveBackwards20);
        assertTrue(car.pos()[1] == 59 && car.pos()[0] == 69 && car.pos()[2] == 3);
        car.addMove(drive60_60);
        assertTrue(car.pos()[1] == 60 && car.pos()[0] == 60 && car.pos()[2] == 3);
        car.addMove(turnRight);
        car.addMove(turnRight);
        car.addMove(turnRight);
        car.addMove(moveForward20);
        assertTrue(car.getDirection() == 2);
        assertTrue(car.pos()[1] == 40 && car.pos()[0] == 60 && car.pos()[2] == 2);
    }
}
