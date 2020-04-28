package aufgabe3;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> {
    private AtomicReference<Node<T>> top = new AtomicReference<>();

    public void push(T value) {

        top.updateAndGet(current -> new Node<>(value, current));
    }

    public T pop() {
		var removedNode = top.getAndUpdate(current -> current == null ? null : current.getNext());
		return removedNode == null ? null : removedNode.getValue();

    }
}
