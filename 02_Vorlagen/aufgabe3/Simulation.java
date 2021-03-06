package aufgabe3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

class BoundedBuffer<T> {
    private ArrayList<T> buffer;
    private int insertIndex = 0;
    private int removeIndex = 0;
    private int size = 0;
    private int capacity;

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        buffer = new ArrayList<T>(Collections.nCopies(capacity, null));
    }

    public synchronized void put(T item) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        buffer.set(insertIndex, item);
        insertIndex = (insertIndex + 1) % capacity;
        size++;
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        T item = buffer.get(removeIndex);
        removeIndex = (removeIndex + 1) % capacity;
        size--;
        notifyAll();
        return item;
    }

    private synchronized boolean isFull() {
        return size == capacity;
    }

    private synchronized boolean isEmpty() {
        return size == 0;
    }
}

class Producer extends Thread {
    private final BoundedBuffeArrayBlockingQueuer<Long> buffer;
    private final int nofItems;

    public Producer(BoundedBuffeArrayBlockingQueuer<Long> buffer, int nofItems) {
        this.buffer = buffer;
        this.nofItems = nofItems;
    }

    @Override
    public void run() {
        var random = ThreadLocalRandom.current();
        for (int i = 0; i < nofItems; i++) {
            try {
                buffer.put(random.nextLong());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private final BoundedBuffeArrayBlockingQueuer<Long> buffer;
    private final int nofItems;

    public Consumer(BoundedBuffeArrayBlockingQueuer<Long> buffer, int nofItems) {
        this.buffer = buffer;
        this.nofItems = nofItems;
    }

    @Override
    public void run() {
        for (int i = 0; i < nofItems; i++) {
            try {
                buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Simulation {
    private static final int NOF_PRODUCERS = 1;
    private static final int NOF_CONSUMERS = 10;
    private static final int BUFFER_CAPACITY = 1;
    // TOTAL_ELEMENTS must be a multiple of ELEMENTS_PER_PRODUCER and ELEMENTS_PER_CONSUMER
    private static final int TOTAL_ELEMENTS = 1000000;
    private static final int ELEMENTS_PER_PRODUCER = TOTAL_ELEMENTS / NOF_PRODUCERS;
    private static final int ELEMENTS_PER_CONSUMER = TOTAL_ELEMENTS / NOF_CONSUMERS;

    public static void main(String[] args) throws InterruptedException {
        var threads = new ArrayList<Thread>();
        var buffer = new BoundedBuffeArrayBlockingQueuer<Long>(BUFFER_CAPACITY);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NOF_PRODUCERS; i++) {
            threads.add(new ArrayBlockingQueueProducer(buffer, ELEMENTS_PER_PRODUCER));
        }
        for (int i = 0; i < NOF_CONSUMERS; i++) {
            threads.add(new ArrayBlockingQueueConsumer(buffer, ELEMENTS_PER_CONSUMER));
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


// 1 Producer, 1 Consumer, Bufferkapazität 1,
// je 1‘000‘000 Elemente pro Producer und Consumer
// Total time: 11954 ms


// 1 Producer, 10 Consumer, Bufferkapazität 1,
// 1‘000‘000 Elemente von Producer und 100‘000 pro Consumer
// Total time: 18894 ms

// 1 Produce, 10 Consumer ist langsamer

