Change between UML from Ex05 to UML Ex06

Playerpouch was refactored. The new idea was, that the playerpouch is making things more complicated then needen, 
because it was a new object.
The new approach is, that the GoalSquares, MoveSquares and HomeSquares are now stored as ArrayLists in the game class.
To keep the structure of the squares the same, to make it easier for other classes to handle the interaction with
all the squares.

BoardRenderer was refactored in BoardRenderer and BoardToStringBuffer, so that the responsibilities are divided.
BoardToStringBuffer handles all the changes in the StringBuffer.
The BoardToStringBuffer is a composition from the BoardRenderer class.

The new version of the Ludo is a lot more complex, many new classes were added to handle all the additional tasks.
But we planned the Ex05 with the Ex06 always in mind and we tried to implement a class structure that is capable of
beeing expanded without any major refactoring. But the basic structure was kept the same as Ex05. 