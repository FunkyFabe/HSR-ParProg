package aufgabe1;

public class PetersonTest {
	private static final int ROUNDS = 10_000_000;
	private static int counter;

	public static void main(String[] args) throws InterruptedException {
		var mutex = new PetersonMutex();
		counter = 0;
		var thread0 = new Thread(() -> {
			for (int i = 0; i < ROUNDS; i++) {
				mutex.thread0Lock();
				counter++;
				mutex.thread0Unlock();
			}
		});
		var thread1 = new Thread(() -> {
			for (int i = 0; i < ROUNDS; i++) {
				mutex.thread1Lock();
				counter--;
				mutex.thread1Unlock();
			}
		});
		thread0.start();
		thread1.start();
		thread0.join();
		thread1.join();
		if (counter != 0) {
			throw new AssertionError("Wrong synchronization");
		}
		System.out.println("Completed");
	}
}
