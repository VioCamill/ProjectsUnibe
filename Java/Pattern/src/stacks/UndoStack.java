package stacks;

import java.util.ArrayList;
import java.util.List;

/**
 * Stack for which we may ask to undo the last push or pop.
 */
public class UndoStack<E> extends LinkStackDecorator<E> {

	private static final String PUSH = "PUSH";
	private static final String POP = "POP";

	private List<StackCommand> history = new ArrayList<StackCommand>();

	private class StackCommand {
		private E item;
		private String action;

		private StackCommand(E item, String action) {
			this.item = item;
			this.action = action;
		}
	}

	public UndoStack(IStack<E> delegate){
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
		delegate.push(item);
		history.add(new StackCommand(item, PUSH));
	}

	@Override
	public E top() {
		return delegate.top();
	}

	@Override
	public void pop() {
		E item = top();
		delegate.pop();
		history.add(new StackCommand(item, POP));
	}

	/**
	 * Undo the last push or pop
	 */
	public void undo() {
		if (!history.isEmpty()) {
			
		}
	}
}
