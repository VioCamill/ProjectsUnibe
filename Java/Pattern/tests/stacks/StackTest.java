package stacks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for a few feature combinations
 */
public class StackTest {

    /**
     * @return stacks with different features combined
     */
    public static Stream<IStack<String>> stackProvider() {
        return Stream.of(
                new UndoStack<>(new NullLogStack<>(new LinkStack<>())), // NullLogStack for silent testing
                new NullLogStack<>(new LockableStack<>(new LinkStack<>())), // NullLogStack for silent testing
                new UndoStack<>(new LockableStack<>(new LinkStack<>())));
    }

    @ParameterizedTest
    @MethodSource("stackProvider")
    public void empty(IStack stack) {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @ParameterizedTest
    @MethodSource("stackProvider")
    public void emptyTopFails(IStack stack) {
        assertThrows(AssertionError.class, () -> stack.top());
    }

    @ParameterizedTest
    @MethodSource("stackProvider")
    public void emptyRemoveFails(IStack stack) {
        assertThrows(AssertionError.class, () -> stack.pop());
    }

    @ParameterizedTest
    @MethodSource("stackProvider")
    public void pushOneElement(IStack stack) {
        stack.push("a");
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals("a", stack.top());
    }

    @ParameterizedTest
    @MethodSource("stackProvider")
    public void pushPopOneElement(IStack stack) {
        stack.push("a");
        stack.pop();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @ParameterizedTest
    @MethodSource("stackProvider")
    public void pushNull(IStack stack) {
        stack.push(null);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertNull(stack.top());
    }

    @ParameterizedTest
    @MethodSource("stackProvider")
    public void twoElement(IStack stack) {
        stack.push("a");
        assertEquals("a", stack.top());
        stack.push("b");
        assertEquals("b", stack.top());
        stack.pop();
        assertEquals("a", stack.top());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("stackProvider")
    public void firstInLastOut(IStack stack) {
        stack.push("a");
        stack.push("b");
        stack.push("c");
        assertEquals("c", stack.top());
        stack.pop();
        assertEquals("b", stack.top());
        stack.pop();
        assertEquals("a", stack.top());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("stackProvider")
    public void brokenSequence(IStack stack) {
        stack.push("a");
        stack.pop();
        assertThrows(AssertionError.class, () -> stack.pop());
    }
}
