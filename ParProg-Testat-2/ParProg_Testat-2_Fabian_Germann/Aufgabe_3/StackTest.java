package aufgabe3;

import java.util.ArrayList;

public class StackTest {
	private static final int THREADS = 100;
	private static final int STEPS = 100_000;
	
	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		var stack = new LockFreeStack<Integer>();
		var threads = new ArrayList<Thread>();
		for (int count = 0; count < THREADS; count++) {
			var thread = new Thread(() -> {
					for (int step = 0; step < STEPS; step++) {
						stack.push(step);
						if (stack.pop() == null) {
							throw new AssertionError("Race condition");
						}
					}
			});
			threads.add(thread);
			thread.start();
		}
		for (var thread : threads) {
			thread.join();
		}
		if (stack.pop() != null) {
			throw new AssertionError("Race condition");
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Duration: "  + (endTime - startTime) + "ms");
	}
}
