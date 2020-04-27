package aufgabe4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueueTest extends ConcurrentTest {
	private static final Duration TIMEOUT = Duration.ofSeconds(5);

	private static class Item {
		public int threadId;
		public int number;
	}

	@Test
	@DisplayName("Single producer, single consumer")
	public void testSingleProducerConsumer() throws InterruptedException {
		assertTimeoutPreemptively(TIMEOUT, () -> multiProduceConsume(1, 1, 1000_000));
	}

	@Test
	@DisplayName("Multiple producers, single consumer")
	public void testMultiProducerSingleConsumer() throws InterruptedException {
		assertTimeoutPreemptively(TIMEOUT, () -> multiProduceConsume(2, 1, 1000_000));
	}

	@Test
	@DisplayName("Single producer, multiple consumers")
	public void testSingleProducerMultiConsumer() throws InterruptedException {
		assertTimeoutPreemptively(TIMEOUT, () -> multiProduceConsume(1, 2, 1000_000));
	}

	@Test
	@DisplayName("Multiple producers, multiple consumers")
	public void testMultiProducersConsumers() throws InterruptedException {
		assertTimeoutPreemptively(TIMEOUT, () -> {
			multiProduceConsume(10, 10, 10_000);
			multiProduceConsume(15, 7, 10_000);
		});
	}

	@Test
	@DisplayName("Mixed usage")
	public void testMixedUsage() throws InterruptedException {
		assertTimeoutPreemptively(TIMEOUT, () -> {
			mixedUse(1, 1000_000);
			mixedUse(100, 10_000);
		});
	}

	private void mixedUse(int nofThreads, int nofSteps) throws InterruptedException {
		var queue = new LockFreeQueue<Integer>();
		var threads = new ArrayList<Thread>();
		for (int id = 0; id < nofThreads; id++) {
			threads.add(new Thread(() -> {
				for (int number = 0; number < nofSteps; number++) {
					queue.enqueue(number);
					assertNotNull(queue.dequeue());
				}
			}));
		}
		forkJoin(threads);
		assertNull(queue.dequeue());
	}

	private void multiProduceConsume(int nofProducers, int nofConsumers, int elementFraction)
			throws InterruptedException {
		var queue = new LockFreeQueue<Item>();
		var threads = new ArrayList<Thread>();
		int totalElements = nofProducers * nofConsumers * elementFraction;
		for (int id = 0; id < nofProducers; id++) {
			int threadId = id;
			threads.add(new Thread(() -> {
				produceItems(totalElements / nofProducers, queue, threadId);
			}));
		}
		for (int id = 0; id < nofConsumers; id++) {
			threads.add(new Thread(() -> {
				consumeItems(totalElements / nofConsumers, queue);
			}));
		}
		forkJoin(threads);
		assertNull(queue.dequeue());
	}

	private void forkJoin(List<Thread> threads) throws InterruptedException {
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
	}

	private void consumeItems(int amount, LockFreeQueue<Item> queue) {
		var map = new HashMap<Integer, Integer>();
		for (int number = 0; number < amount; number++) {
			var item = queue.dequeue();
			while (item == null) {
				Thread.yield();
				item = queue.dequeue();
			}
			if (map.containsKey(item.threadId)) {
				assertTrue(map.get(item.threadId) < item.number);
			}
			map.put(item.threadId, item.number);
		}
	}

	private void produceItems(int amount, LockFreeQueue<Item> queue, int threadId) {
		for (int number = 0; number < amount; number++) {
			var item = new Item();
			item.threadId = threadId;
			item.number = number;
			queue.enqueue(item);
		}
	}
}
