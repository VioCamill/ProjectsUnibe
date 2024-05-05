package stacks;

/**
 * Silent version of LogStack for silent testing
 */
public class NullLogStack<E> extends LogStack<E> {


    public NullLogStack(IStack<E> delegate) {
        super(delegate);
    }

    @Override
    public boolean isEmpty(){
        return delegate.isEmpty();
    }

    @Override
    public int size(){
      return delegate.size();
    }
    @Override
    public void push(E item){
        delegate.push(item);
    }
    @Override
    public E top() {
        return delegate.top();
    }
    @Override
    public void pop(){
        delegate.pop();
    }
}
