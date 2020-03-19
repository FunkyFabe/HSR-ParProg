package aufgabe1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WarehouseWithLockCondition implements Warehouse {
	private final Lock monitor;
	private final Condition nonFull;
	private final Condition nonEmpty;
	private final int capacity;
	private int stock = 0;

	public WarehouseWithLockCondition(int capacity, boolean fair) {
		this.capacity = capacity;
		monitor = new ReentrantLock(fair);
		nonFull = monitor.newCondition();
		nonEmpty = monitor.newCondition();
	}

	@Override
	public void put(int amount) throws InterruptedException {
		monitor.lock();
		try {
			while(stock + amount > capacity) {
				nonFull.await();
			}
			stock += amount;
			nonEmpty.signalAll();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public void get(int amount) throws InterruptedException {
		monitor.lock();
		try {
			while(amount > stock) {
				nonEmpty.await();
			}
			stock -= amount;
			nonFull.signalAll();
		} finally {
			monitor.unlock();
		}
	}
}
