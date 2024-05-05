package stacks;

/**
 * Stack that simply logs any method call to the console.
 */
public class LogStack<E> extends LinkStackDecorator<E> {

	public LogStack(IStack<E> delegate){
		super(delegate);
	}

	@Override
	public boolean isEmpty() {
		System.out.println("isEmpty");
		return delegate.isEmpty();
	}

	@Override
	public int size() {
		System.out.println("size");
		return delegate.size();
	}

	@Override
	public void push(E item) {
		System.out.println("push: " + item);
		delegate.push(item);
	}

	@Override
	public E top() {
		System.out.println("top");
		return delegate.top();
	}

	@Override
	public void pop() {
		System.out.println("pop");
		delegate.pop();
	}

}
