package stacks;

/**
 * This Stack implementation uses a linked list of Cells.
 *
 * Invariant:
 * The size should always be non-negative.
 * If the size is zero, then the top element should not exist.
 * Else the top element should exist.
 */
public class LinkStack<E> implements IStack<E> {
	protected Cell top;
	protected int size;

	public LinkStack() {
		// Establishes the invariant.
		top = null;
		size = 0;
	}

	/**
	 * This inner class is just a glorified data structure to hold items and links
	 * to other cells.
	 */
	protected class Cell {
		E item;
		Cell next;

		Cell(E item, Cell next) {
			this.item = item;
			this.next = next;
		}
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public int size() {
		return size;
	}

	/**
	 * Links a new Cell to the head of the list pointed to by top.
	 *
	 * The stack should not be empty afterwards, and the top element should be the
	 * one that we added.
	 */
	public void push(E item) {
		top = new Cell(item, top);
		size++;
		assert !this.isEmpty();
		assert this.top() == item;
		assert invariant();
	}

	/**
	 * Returns the top without removing it.
	 *
	 * The stack should not be empty.
	 *
	 * @return The top element of type E
	 */
	public E top() {
		assert !this.isEmpty();
		return top.item;
	}

	/**
	 * Unlinks the head Cell and sets top to point to the tail of the list.
	 *
	 * The stack should not be empty.
	 */
	public void pop() {
		assert !this.isEmpty();
		top = top.next;
		size--;
		assert invariant();
	}

	/**
	 * The invariant is established by the constructor, and must hold at the start
	 * <I>and</i> end of each method. (To keep things simple, we only check at the
	 * end of methods that modify the state.)
	 */
	protected boolean invariant() {
		return (size >= 0) && ((size == 0 && this.top == null) || (size > 0 && this.top != null));
	}

}