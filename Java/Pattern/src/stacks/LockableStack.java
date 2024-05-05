package stacks;

/**
 * Stock that may be locked by clients. Internally, the stack locks itself
 * before any push or pop, releasing the lock afterwards.
 */
public class LockableStack<E> extends LinkStackDecorator<E> {

	private boolean locked;

	public LockableStack(IStack<E> delegate) {
		super(delegate);
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public void push(E item) {
		assert (!isLocked());

		this.lock();
		delegate.push(item);
		this.unlock();
	}

	@Override
	public E top() {
		return delegate.top();
	}

	@Override
	public void pop() {
		assert (!isLocked());

		this.lock();
		delegate.pop();
		this.unlock();
	}

	public void lock() {
		this.locked = true;
	}

	public void unlock() {
		this.locked = false;
	}

	public boolean isLocked() {
		return this.locked;
	}

}
