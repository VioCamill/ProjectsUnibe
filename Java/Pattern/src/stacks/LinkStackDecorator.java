package stacks;

/**
 * Decorator for LinkStack.
 * Superclass for all Stacks with additional feature
 */
public abstract class LinkStackDecorator<E> implements IStack<E> {
    protected IStack<E> delegate;
    public LinkStackDecorator(IStack<E> delegate){
        this.delegate = delegate;
    }
}
