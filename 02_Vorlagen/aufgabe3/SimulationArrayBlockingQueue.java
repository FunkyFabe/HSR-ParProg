package aufgabe3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

class BoundedBuffeArrayBlockingQueuer<T> {
    private ArrayBlockingQueue<T> buffer;

    public BoundedBuffeArrayBlockingQueuer(int capacity) {
        buffer = new ArrayBlockingQueue<T>(capacity);
    }

    public synchronized void put(T item) throws InterruptedException {
        while (!buffer.isEmpty()) {
            wait();
        }
        buffer.put(item);
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        notifyAll();
        return buffer.take();
    }
}

class ArrayBlockingQueueProducer extends Thread {
    private final BoundedBuffeArrayBlockingQueuer<Long> buffer;
    private final int nofItems;

    public ArrayBlockingQueueProducer(BoundedBuffeArrayBlockingQueuer<Long> buffer, int nofItems) {
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

class ArrayBlockingQueueConsumer extends Thread {
    private final BoundedBuffeArrayBlockingQueuer<Long> buffer;
    private final int nofItems;

    public ArrayBlockingQueueConsumer(BoundedBuffeArrayBlockingQueuer<Long> buffer, int nofItems) {
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

public class SimulationArrayBlockingQueue {
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
// Total time: 11561 ms


// 1 Producer, 10 Consumer, Bufferkapazität 1,
// 1‘000‘000 Elemente von Producer und 100‘000 pro Consumer
// Total time: 17263 ms

// 1 Produce, 10 Consumer ist langsamer

