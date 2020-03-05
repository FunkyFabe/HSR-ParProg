package aufgabe1;

import java.util.Random;

class Producer extends Thread {
	private final Warehouse warehouse;
	private final int itemCount;
	private final int callLimit;

	public Producer(final Warehouse warehouse, int itemCount, int callLimit) {
		this.warehouse = warehouse;
		this.itemCount = itemCount;
		this.callLimit = callLimit;
	}

	@Override
	public void run() {
		try {
			var random = new Random(4711);
			int countAllItems = 0;
			while (countAllItems < itemCount) {
				int countPerCall = random.nextInt(callLimit) + 1;
				if (countAllItems + countPerCall > itemCount) {
					countPerCall = itemCount - countAllItems;
				}
				warehouse.put(countPerCall);
				countAllItems += countPerCall;
			}
		} catch (InterruptedException e) {
			throw new AssertionError(e);
		}
	}
}
