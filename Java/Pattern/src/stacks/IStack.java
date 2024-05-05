package stacks;

/**
 * Generic version of a simple StackInterface
 */
public interface IStack<E> {
	public boolean isEmpty();
	public int size();
	public void push(E item);
	public E top();
	public void pop();
}
