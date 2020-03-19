package aufgabe1;

public class WarehouseWithMonitor implements Warehouse {
	private final int capacity;
	private int stock = 0;

	public WarehouseWithMonitor(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public synchronized void put(int amount) throws InterruptedException {
		while (stock + amount > capacity) {
			wait();
		}
		stock += amount;
		notifyAll();
	}

	@Override
	public synchronized void get(int amount) throws InterruptedException {
		while (amount > stock) {
			wait();
		}
		stock -= amount;
		notifyAll();
	}
}
