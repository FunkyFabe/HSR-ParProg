package aufgabe3;

public class PrimeCounterThread extends Thread {
    private long count = 0;
    private long start;
    private long end;

    public PrimeCounterThread(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        //super.run();
        for (long number = start; number < end; number++) {
            if (isPrime(number)) {
                count++;
            }
        }
    }

    private static boolean isPrime(long number) {
        for (long factor = 2; factor * factor <= number; factor++) {
            if (number % factor == 0) {
                return false;
            }
        }
        return true;
    }

    public long getCount() {
        return count;
    }
}
