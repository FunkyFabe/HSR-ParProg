package aufgabe3;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class BoundedBuffer<T> {
	// TODO: Implement bounded buffer as monitor
	public BoundedBuffer(int capacity) {

	}

	public void put(T item) {
		// TODO: wait until non-full, then add item in last position
		throw new UnsupportedOperationException();
	}

	public T get() {
		// TODO: wait until non-empty, then remove and return item from first position
		throw new UnsupportedOperationException();
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
		var random = ThreadLocalRandom.current();
		for (int i = 0; i < nofItems; i++) {
			buffer.put(random.nextLong());
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
		for (int i = 0; i < nofItems; i++) {
			buffer.get();
		}
	}
}

public class Simulation {
	private static final int NOF_PRODUCERS = 2;
	private static final int NOF_CONSUMERS = 4;
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
		for (var thread: threads) {
			thread.start();
		}
		for (var thread: threads) {
			thread.join();
		}
		long stopTime = System.currentTimeMillis();
		System.out.println("Producer-consumer simulation finished");
		System.out.println("Total time: " + (stopTime - startTime) + " ms");
	}
}
