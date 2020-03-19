package aufgabe2b;

enum PhilosopherState {
	THINKING, HUNGRY, EATING
}

class Philosopher extends Thread {
	private PhilosopherTable table;
	private PhilosopherState state = PhilosopherState.THINKING;
	private int id;

	public Philosopher(PhilosopherTable table, int id) {
		this.id = id;
		this.table = table;
	}

	public PhilosopherState getPhilosopherState() {
		return state;
	}

	public long getId() {
		return id;
	}

	private void think() throws InterruptedException {
		state = PhilosopherState.THINKING;
		table.notifyStateChange(this);
		Thread.sleep((int) (Math.random() * 1500));
	}

	private void eat() throws InterruptedException {
		state = PhilosopherState.EATING;
		table.notifyStateChange(this);
		Thread.sleep((int) (Math.random() * 1000));
	}

	private void takeForks() throws InterruptedException {
		state = PhilosopherState.HUNGRY;
		table.notifyStateChange(this);

		int leftForkNo = table.leftForkNumber(id);
		int rightForkNo = table.rightForkNumber(id);

		table.acquireFork(leftForkNo);
		while (!table.tryAcquireFork(rightForkNo)) {
			System.out.println("Philosophers " + getId() + " retries...");
			table.releaseFork(leftForkNo);
			sleep(500);
			table.acquireFork(leftForkNo);
		}
	}

	private void putForks() {
		table.releaseFork(table.leftForkNumber(id));
		table.releaseFork(table.rightForkNumber(id));
	}

	@Override
	public void run() {
		Thread.yield();
		try {
			while (true) {
				think();
				takeForks();
				eat();
				putForks();
			}
		} catch (InterruptedException e) {
			throw new AssertionError(e);
		}
	}
}