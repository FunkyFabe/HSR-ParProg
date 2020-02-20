package aufgabe3;

public class PrimeCounter {
	private static boolean isPrime(long number) {
		for (long factor = 2; factor * factor <= number; factor++) {
			if (number % factor == 0) {
				return false;
			}
		}
		return true;
	}

	private static long countPrimes(long start, long end) {
		long count = 0;
		for (long number = start; number < end; number++) {
			if (isPrime(number)) {
				count++;
			}
		}
		return count;
	}

	private static final long START = 1_000_000L;
	private static final long END = 10_000_000L;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		long count = countPrimes(START, END);
		long endTime = System.currentTimeMillis();
		System.out.println("#Primes: " + count + " Time: " + (endTime - startTime) + " ms");
		// #Primes: 586081 Time: 17430 ms
		// #Primes: 586081 Time: 5797 ms
	}
}
