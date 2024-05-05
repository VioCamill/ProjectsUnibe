# Exercise 6

In this exercise, we continue working on the Ludo board game. So far, we only initialized the game and created the 
important components. However, no logic for actually playing the game has been implemented.

The goal in this exercise is to implement the game logic and represent it through another UML diagram. 
Similar to the snakes and ladders game, the game is printed after each step.

To pass this exercise, you have to implement tasks 4 and 5 as described below. 
As a prerequisite, you need to have finished tasks 1 and 2 in exercise 5; if you have not done so yet, you first have to complete exercise 5.


## Task 4: Game Logic and testing

### Sub-Task 4.1 :Game Logic
In the previous exercise, you implemented the basic structures to represent a Ludo game and functions for basic movement. 
However, the bulk of the logic is still missing. The goal of this iteration is to provide a fully functional game that corresponds to the described rules. 
It consists of doing the following tasks:

- Implement a `Die` class that is used by the players.
- Implement the remaining actions a player can make (for example, rolling the die, placing a piece onto the starting square, moving a token into the goal tile, sending other player’s pieces to the starting position, …).
- Keep track of the game state. This includes things like remembering which player’s turn it is next, determining whether the game is over, etc.
- Implement any other missing features to obtain a fully functional game.

If you are unsure about rules, you can either ask us or decide on your own how your game should behave, but then you will have to document the changes in a separate markdown file.
Either way, you should have test cases that make sure that the rules are implemented correctly, and the game behaves as expected.

#### Requirements to pass this part
- The game should be playable from start to end, without errors or bugs.

### Sub-Task 4.2 : Testing
As usual testing is an important part to verify the correct functionality.

#### Requirements to pass this part
Write tests for  **at least** the following scenarios:
- Player movement of the piece.
- Player places a piece onto the starting position.
- Player rolls a six, does his part, and rolls again.
- Player wins.
- Player resets another players piece with his own.
- Player cannot execute his turn, due to rolling a six on his home road, or he would land on his own piece, and ends his turn without moving.

We encourage you to write more tests than mentioned in the above descriptions.

### Sub-Task 4.3 : Polishing

Finally, finish off your implementation by cleaning up your project and making
sure that you follow the principles taught in the course. 

#### Requirements to pass this part
Make sure that you have applied the following consistently:
- documentation (Javadoc, inline comments where necessary),
- design by contract,
- responsibility-driven design (state responsibilities in class comments!).

## Task 5: Second UML diagram

In the previous exercise, you have designed individual parts of the game like a renderer, player, etc. 
You have designed classes with a reason in your mind like the role of a class, how responsibilities should be 
distributed among classes and how they should interact with each other. In this exercise, you have brought these parts 
together, and now it is important to look back, ponder, and document your design.

#### Requirements to pass this part
- Create a UML class diagram showing the design of your game. If classes are interacting with each other, include their relations in the diagram.
Name the diagram  `final_class_diagram_ex6`. Take a picture of the handmade diagram or scan it and add it to your repository.

- Add a file named `changes.md`. It should describe the differences between the current UML (`final_class_diagram_ex6`) and the initial one (`initial_class_diagram_ex5`) for exercise 5. 
What has been changed? Have there been some bigger refactoring requirements? If yes, why was it necessary? Is the code more clear now? Write these observations into this file (`changes.md`).

- Once done, mark the final version as follows:

```
git tag -a v2 -m "Ludo stage 2"
```

## Optional tasks

If you want to improve your game, you can implement the game with two players or three players.
Considering rules for such specific cases (two or three player game versions) is upto you.
If you use different rules for two or three player versiosn (compared to four player version), please mention in the README.md file.

## Notes

If you are unsure about the specific rules, you are free to decide for yourself. 
However, please include a file README where you explain your decision.

## **Do not forget** to mention your contributions for the exercise in the `Contributions.md` file.

## Deadline

Submit your solutions by pushing your code to the git repository by ___Friday, 21 April, 12:00___. *Do not forget the tag!*
