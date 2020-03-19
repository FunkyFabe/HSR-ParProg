package aufgabe1;

import java.util.*;

public class TestScenario {
	private static final int TOTAL_ELEMENTS = 1_000_000; 
	
	public static void main(String[] args) throws InterruptedException {
		testSeries(1, 1, 5);
		testSeries(5, 5, 5);
		testSeries(1, 10, 5);
		testSeries(100, 100, 5);
		testSeries(1, 10, TOTAL_ELEMENTS);
	}

	private static void testSeries(int nofProducers, int nofConsumers, int bufferCapacity) throws InterruptedException {
		System.out.println("TEST SERIES: " + nofProducers + " producers " + nofConsumers + " consumers (capacity " + bufferCapacity + ")");
		test(new WarehouseWithMonitor(bufferCapacity), "unfair", nofProducers, nofConsumers, bufferCapacity);
		test(new WarehouseWithSemaphore(bufferCapacity, false), "unfair", nofProducers, nofConsumers, bufferCapacity);
		test(new WarehouseWithSemaphore(bufferCapacity, true), "fair", nofProducers, nofConsumers, bufferCapacity);
		test(new WarehouseWithLockCondition(bufferCapacity, false), "unfair", nofProducers, nofConsumers, bufferCapacity);
		test(new WarehouseWithLockCondition(bufferCapacity, true), "fair", nofProducers, nofConsumers, bufferCapacity);
	}

	private static void test(Warehouse buffer, String name, int nofProducers,
			int nofConsumers, int bufferCapacity) throws InterruptedException {
		System.out.println("Test " + buffer.getClass().getSimpleName() + " " + name);
		for (int run = 0; run < 3; run++) {
		var allThreads = new ArrayList<Thread>();
		for (int count = 0; count < nofProducers; count++) {
			allThreads.add(new Producer(buffer, TOTAL_ELEMENTS / nofProducers, bufferCapacity / 2));
		}
		for (int count = 0; count < nofConsumers; count++) {
			allThreads.add(new Consumer(buffer, TOTAL_ELEMENTS / nofConsumers, bufferCapacity / 2));
		}
		long startTime = System.currentTimeMillis();
		for (var thread : allThreads) {
			thread.start();
		}
		for (var thread : allThreads) {
			thread.join();
		}
		long stopTime = System.currentTimeMillis();
		System.out.println("Total time: " + (stopTime - startTime) + " ms");
		}
	}
}
