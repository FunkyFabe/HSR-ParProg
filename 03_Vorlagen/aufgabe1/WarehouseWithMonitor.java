package aufgabe1;

public class WarehouseWithMonitor implements Warehouse {
    private int capacity;
    private int stock;

    public WarehouseWithMonitor(int capacity) {
        this.capacity = capacity;
        this.stock = 0;
    }

    @Override
    public synchronized void put(int amount) throws InterruptedException {
        // Wait until the amount can be added without exceeding the capacity.
        // If this is true, increase the stock size by the specified amount.
        while (stock + amount > capacity) {
            wait();
        }
        stock += amount;
        notifyAll();
    }

    @Override
    public synchronized void get(int amount) throws InterruptedException {
        // Wait until the stock contains at least the specified amount.
        // If this is true, decrease the stock size by the specified once.
        while (stock < amount) {
            wait();
        }
        stock -= amount;
        notifyAll();
    }
}
