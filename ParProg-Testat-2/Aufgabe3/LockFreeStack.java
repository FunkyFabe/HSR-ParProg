package aufgabe3;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> {
	private AtomicReference<Node<T>> top = new AtomicReference<>();

	public void push(T value) {
		while (true) {
			var current = top.get();
			var node = new Node<>(value, current);
			if (top.compareAndSet(current, node)) {
				return;
			}
		}
	}

	public T pop() {
		while (true) {
			var current = top.get();
			if (current == null) {
				return null;
			}
			if (top.compareAndSet(current, current.getNext())) {
				return current.getValue();
			}
		}
	}
}
