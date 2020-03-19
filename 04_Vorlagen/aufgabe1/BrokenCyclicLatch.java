package aufgabe1;

import java.util.concurrent.CountDownLatch;

public class BrokenCyclicLatch {
	private static final int NOF_ROUNDS = 10;
	private static final int NOF_THREADS = 10;

	private static CountDownLatch latch = new CountDownLatch(NOF_THREADS);
	private static CountDownLatch resetLatch = new CountDownLatch(1);

	private static void multiRounds(int number) throws InterruptedException {
		for (int round = 0; round < NOF_ROUNDS; round++) {
			latch.countDown();
			latch.await();
			if (number == 0) {
				latch = new CountDownLatch(NOF_THREADS); // new latch for new round
			}
			System.out.println("Round " + round + " thread " + number);
		}
	}

	public static void main(String[] args) {
		for (int count = 0; count < NOF_THREADS; count++) {
			int number = count;
			new Thread(() -> {
				try {
					multiRounds(number);
				} catch (InterruptedException e) {
					throw new AssertionError(e);
				}
			}).start();
		}
	}
}
