package stacks;

import java.util.EmptyStackException;
import java.util.Stack;

import static java.util.Objects.isNull;


/**
 * This class links the interface IStack to an object from java.util.Stack
 *
 * Invariant:
 * The size should always be non-negative.
 * If the size is zero, then the top element should not exist.
 * Else the top element should exist.
 *
 */

public class ObjectAdapterLinkStack<E> implements IStack<E> {

    Stack stack = new Stack<E>();

    @Override
    public boolean isEmpty() throws java.lang.AssertionError {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        assert (stack.size() >= 0);
        assert invariant();
        return stack.size();
    }

    /**
     * Stack is not empty, after an Element has been pushed
     *
     * @param item
     */

    @Override
    public void push(E item) {
        stack.push(item);
        assert !(stack.isEmpty());
        if (isNull(item)) assert isNull(stack.peek());
        else assert (stack.peek().equals(item));
        assert invariant();
    }

    /**
     * Returns the top object of the stack without removing it or throws a AssertionError when the stack is empty
     *
     * @return Object<E>
     */

    @Override
    public E top() {
        assert invariant();
        E topObject;
        try {
            topObject = (E) stack.peek();
        } catch (EmptyStackException E) {
            //checks if the stack really is empty
            assert stack.isEmpty();
            throw new java.lang.AssertionError();
        }
        assert invariant();
        return topObject;
    }

    /**
     * removes the top object of the stack
     *
     */
    @Override
    public void pop() {
        try {
            stack.pop();
        } catch (EmptyStackException E) {
            //checks if the stack really is empty
            assert stack.isEmpty();
            assert invariant();
            //thorws the right exception
            throw new java.lang.AssertionError();
        }
    }
    protected boolean invariant() {

        if (stack.isEmpty()) return stack.size()==0;
        // A non empty stack always has a size > 0
        return (stack.size()>0);
    }
}