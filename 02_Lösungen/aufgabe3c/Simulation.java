package aufgabe3c;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer extends Thread {
	private final BlockingQueue<Long> buffer;
	private final int nofItems;

	public Producer(BlockingQueue<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	@Override
	public void run() {
		try {
			Random random = new Random();
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
	private final BlockingQueue<Long> buffer;
	private final int nofItems;

	public Consumer(BlockingQueue<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < nofItems; i++) {
				buffer.take();
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
		var buffer = new ArrayBlockingQueue<Long>(BUFFER_CAPACITY);
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
