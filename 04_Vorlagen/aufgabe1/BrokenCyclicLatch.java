package aufgabe1;

import java.util.concurrent.CountDownLatch;

public class BrokenCyclicLatch {
	private static final int NOF_ROUNDS = 10;
	private static final int NOF_THREADS = 10;

	private static CountDownLatch latch = new CountDownLatch(NOF_THREADS);

	private static void multiRounds(int number) throws InterruptedException {
		for (int round = 0; round < NOF_ROUNDS; round++) {
			latch.countDown();
			System.out.println(number + " decrements" );
			latch.await();
			if (number == 0) {
				latch = new CountDownLatch(NOF_THREADS); // new latch for new round
				System.out.println("Round " + round + " thread " + number + "RESET");
			} else {
				System.out.println("Round " + round + " thread " + number);
			}
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

/*
Sobald der latch 0 erreicht, laufen alle Threads weiter. Die Threads warten jedoch nicht, bis Thread 0 den latch zurückgesetzt hat.
Da countDown() nicht blockiert, können die Threads weiterlaufen. => Threads dekrementieren den latch, obwohl dieser bereits 0 ist.
Wenn dann der Thread 0 den latch zurücksetzt, kann es vorkommen, dass nicht genügend Threads countDown() aufrufen.
Dies führt dazu dass alle Threads warten und so das Programm blockiert.
 */