# Exercise 07
In this exercise, you will apply your knowledge on design patterns to implement and adapt stacks.

In the lecture on Design by Contract, we introduced a simple stack interface:

        public interface IStack<E> { 	
        public boolean isEmpty(); 	
        public int size(); 		
        public void push(E item); 	
        public E top(); 		
        public void pop();
        }


Afterwards, we implemented this interface by maintaining a linked data structure 
(class LinkStack, see slides and lecture code examples for lecture 03).

Actually, the Java standard library also provides a Stack implementation (java.util.Stack), 
but it is not compatible with our interface.
So, in order to make sure that our LinkStack implementation make everything works 
with the java.util.Stack, we have to write a suitable wrapper using the Adapter Pattern.

## Task 1 

Your first task is to do this by implementing one of the variants of the Adapter Pattern, i.e., providing a Class and
Object Adapter that adapt java.util.Stack to IStack.
Name the resulting classes as ClassAdapterLinkStack or ObjectAdapterLinkStack, respectively.
Do not forget all the assert statements that formalize our pre- and post-conditions as well as class invariants.
If you do not remember them anymore, go look at the lecture slides.

Make sure that everything works properly by re-running our tests.
Namely, make sure that our tests in LinkStackTest work with both Adapter implementations.
To do this either extend from the base test and overwrite the stack, or copy the test file and switch the stack.

# Task 2
Since we have given you a lot to do in the last task (i.e., implement one of the variant of the
 Adapter Pattern and adapt make sure that our tests passes for your implementation), 
 we want you to taking a decision. You **can** choose between option 1 and 2, but you **must** take one.
Quoting Jigsaw, "make your choice" (it is a charachter from an horror movie, so don't google if it is not your bread and butter).

### Option 1
Have a look at the various subclasses of our LinkStack provided.
It turns out that all of these subclasses contribute certain optional features 
(logging, undo, lockable) which we would like to combine in a flexible manner.
The number of feature combinations explodes quickly, so doing this statically by further 
subclassing and multi-inheritance (e.g., a subclass that inherits from LockableStack and UndoStack)
is not feasible. It is not even possible since Java does not support multi-inheritance.

Your first task is to refactor the given example by applying the Decorator Pattern, such that all the features can flexibly be combined.
Also adapt the test such that we test at least a few feature combinations, say "undo+logging", "logging+lockable", "undo+lockable".

### Option 2

In consideration to task 2, can we use another design pattern (or even a combination of patterns) for the sake of instantiating all the different kinds of
stacks (think of Template Method and Factory Method)? 

Have a look at the AWT classes from the Java standard library ( for example a quick search for the source code gives you https://github.com/JetBrains/jdk8u_jdk/tree/master/src/share/classes/java/awt). 
Can you find design patterns implemented in AWT?
There might be several ones, but you should find at least one.

Write your answers for both questions in a file called `QuestionAnswers.md`.


## Deadline
Submit your solutions by pushing your code to the git repository by 
___Friday, 28 Abril, 12:00___.
