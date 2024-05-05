package classTest;

import ludo.CommandParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class CommandParserTest {
    //Tests 1 and 2 as valid input
   @Test
   public void validDecisionInputsOnly (){

       CommandParser commandParser = new CommandParser();
       // 49 is the asci II code for 1
       System.setIn(new ByteArrayInputStream(new byte[] {49}));
       commandParser.playerDecision();
       System.setIn(new ByteArrayInputStream(new byte[] {50}));
       commandParser.playerDecision();
   }

    @Test
    public void askValidPlayerNameTest()  {

        CommandParser commandParser = new CommandParser();

        //Set input to Rambo
        System.setIn(new ByteArrayInputStream(new byte[] {82,97,109,98,111}));
        //Checks if the Rambo is returned as String
        Assertions.assertEquals("Rambo", commandParser.askPlayerName(2));

    }

        //Manual Test for command parser
@Test
    public static void main(String[] args) {
       //Here the test can be switched on and off, by changing the value of runTest
       boolean runTest = false;
       if (runTest){
           CommandParser commandParser = new CommandParser();
           System.out.println("Manueller Test: Eingabe ist gefordert");

           commandParser.playerDecision();

           String name = commandParser.askPlayerName(1);
       }
   }

}









