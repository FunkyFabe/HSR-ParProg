package aufgabe1;

import java.util.concurrent.Semaphore;

public class WarehouseWithSemaphore implements Warehouse {
    private Semaphore capacity;
    private Semaphore stock;

    public WarehouseWithSemaphore(int capacity, boolean fair) {
        this.capacity = new Semaphore(capacity, fair);
        this.stock = new Semaphore(0, fair);
    }

    @Override
    public void put(int amount) throws InterruptedException {
        // Wait until the amount can be added without exceeding the capacity.
        // If this is true, increase the stock size by the specified amount.
        capacity.acquire(amount);
        stock.release(amount);
    }

    @Override
    public void get(int amount) throws InterruptedException {
        // Wait until the stock contains at least the specified amount.
        // If this is true, decrease the stock size by the specified once.
        stock.acquire(amount);
        capacity.release(amount);
    }
}
