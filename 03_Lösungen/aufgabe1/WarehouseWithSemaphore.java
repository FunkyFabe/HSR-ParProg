package aufgabe1;

import java.util.concurrent.Semaphore;

public class WarehouseWithSemaphore implements Warehouse {
	private final Semaphore upperLimit;
	private final Semaphore lowerLimit;

	public WarehouseWithSemaphore(int capacity, boolean fair) {
		upperLimit = new Semaphore(capacity, fair);
		lowerLimit = new Semaphore(0, fair);
	}

	@Override
	public void put(int amount) throws InterruptedException {
		upperLimit.acquire(amount);
		lowerLimit.release(amount);
	}

	@Override
	public void get(int amount) throws InterruptedException {
		lowerLimit.acquire(amount);
		upperLimit.release(amount);
	}
}
