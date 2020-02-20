package aufgabe3;

import java.util.ArrayList;

public class PrimeCounterParallel {

    private static long countPrimes(long start, final long end, int numberOfThreads) throws InterruptedException {
        long count = 0;
        long subRange = (end - start) / numberOfThreads;
        ArrayList<PrimeCounterThread> threads = new ArrayList<>();
        while (start < end) {
            var thread = new PrimeCounterThread(start, start + subRange);
            thread.start();
            start += subRange;
            threads.add(thread);
        }

        for (var thread : threads) {
            thread.join();
            count += thread.getCount();
        }
        return count;
    }

    private static long meassureTime(long start, long end, int numberOfThreads, long referenceTime) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long count = countPrimes(start, end, numberOfThreads);
        long endTime = System.currentTimeMillis();
        long time = (endTime - startTime);
        System.out.println(numberOfThreads + " Threads #Primes: " + count + " Factor: " + (double) referenceTime / (double) time + " Time: " + time + " ms");
        return time;
    }

    private static final long START = 1_000_000L;
    private static final long END = 10_000_000L;
    private static final int NUMBER_OF_THREADS = 16;

    public static void main(String[] args) throws InterruptedException {
        long refrenceTime = meassureTime(START, END, 1, 0);
        for (int i = 2; i <= NUMBER_OF_THREADS; i++) {
            meassureTime(START, END, i, refrenceTime);
        }
        // #Primes: 586081 Time: 17430 ms
        // #Primes parallel: 586081 Time: 5797 ms
    }
}