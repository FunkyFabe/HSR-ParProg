package aufgabe1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WarehouseWithLockCondition implements Warehouse {
    private Lock monitor;
    private Condition nonFull;
    private Condition nonEmpty;

    private int capacity;
    private int stock;

    public WarehouseWithLockCondition(int capacity, boolean fair) {
        monitor = new ReentrantLock(fair);
        nonFull = monitor.newCondition();
        nonEmpty = monitor.newCondition();
        this.capacity = capacity;
        this.stock = 0;
    }

    @Override
    public void put(int amount) throws InterruptedException {
        // Wait until the amount can be added without exceeding the capacity.
        // If this is true, increase the stock size by the specified amount.
        monitor.lock();
        try {
            while (stock + amount > capacity) {
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
        // Wait until the stock contains at least the specified amount.
        // If this is true, decrease the stock size by the specified once
        monitor.lock();
        try {
            while (stock < amount) {
                nonEmpty.await();
            }
            stock -= amount;
            nonFull.signalAll();
        } finally {
            monitor.unlock();
        }
    }
}
