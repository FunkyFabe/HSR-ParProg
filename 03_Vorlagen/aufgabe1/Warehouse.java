package aufgabe1;

public interface Warehouse {
	// Wait until the amount can be added without exceeding the capacity.
	// If this is true, increase the stock size at once.
	void put(int amount) throws InterruptedException;

	// Wait until the stock contains at least the specified amount.
	// If this is true, decrease the stock size at once.
	void get(int amount) throws InterruptedException;
}
