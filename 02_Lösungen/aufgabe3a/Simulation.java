package aufgabe3a;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

class BoundedBuffer<T> {
	private final Queue<T> queue = new LinkedList<T>();
	private final int capacity;

	public BoundedBuffer(int capacity) {
		this.capacity = capacity;
	}

	public synchronized void put(T item) throws InterruptedException {
		while (queue.size() == capacity) {
			wait();
		}
		queue.add(item);
		notifyAll();
	}

	public synchronized T get() throws InterruptedException {
		while (queue.size() == 0) {
			wait();
		}
		T item = queue.remove();
		notifyAll();
		return item;
	}
}

class Producer extends Thread {
	private final BoundedBuffer<Long> buffer;
	private final int nofItems;

	public Producer(BoundedBuffer<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	@Override
	public void run() {
		try {
			var random = ThreadLocalRandom.current();
			for (int i = 0; i < nofItems; i++) {
				buffer.put(random.nextLong());
			}
			System.out.println("Producer finished " + getName());
		} catch (InterruptedException e) {
			throw new AssertionError();
		}
	}
}

class Consumer extends Thread {
	private final BoundedBuffer<Long> buffer;
	private final int nofItems;

	public Consumer(BoundedBuffer<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < nofItems; i++) {
				buffer.get();
			}
			System.out.println("Consumer finished " + getName());
		} catch (InterruptedException e) {
			throw new AssertionError();
		}
	}
}

public class Simulation {
	private static final int NOF_PRODUCERS = 1;
	private static final int NOF_CONSUMERS = 1;
	private static final int BUFFER_CAPACITY = 1;
	// TOTAL_ELEMENTS must be a multiple of ELEMENTS_PER_PRODUCER and ELEMENTS_PER_CONSUMER
	private static final int TOTAL_ELEMENTS = 1000000;
	private static final int ELEMENTS_PER_PRODUCER = TOTAL_ELEMENTS / NOF_PRODUCERS;
	private static final int ELEMENTS_PER_CONSUMER = TOTAL_ELEMENTS / NOF_CONSUMERS;

	public static void main(String[] args) throws InterruptedException {
		var threads = new ArrayList<Thread>();
		var buffer = new BoundedBuffer<Long>(BUFFER_CAPACITY);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < NOF_PRODUCERS; i++) {
			threads.add(new Producer(buffer, ELEMENTS_PER_PRODUCER));
		}
		for (int i = 0; i < NOF_CONSUMERS; i++) {
			threads.add(new Consumer(buffer, ELEMENTS_PER_CONSUMER));
		}
		for (var thread : threads) {
			thread.start();
		}
		for (var thread : threads) {
			thread.join();
		}
		long stopTime = System.currentTimeMillis();
		System.out.println("Producer-consumer simulation finished");
		System.out.println("Total time: " + (stopTime - startTime) + " ms");
	}
}
