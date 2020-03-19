package aufgabe2a;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class PhilosopherTable {
	private List<ChangeListener> listeners = new ArrayList<>();
	private int nofPhilosophers;
	private Semaphore[] forks;
	private Philosopher[] philosophers;

	public PhilosopherTable(int nofPhilosophers) {
		System.out.println("creating table ...");
		this.nofPhilosophers = nofPhilosophers;
		forks = new Semaphore[nofPhilosophers];
		for (int index = 0; index < nofPhilosophers; index++) {
			forks[index] = new Semaphore(1);
		}
		philosophers = new Philosopher[nofPhilosophers];
		for (int index = 0; index < nofPhilosophers; index++) {
			philosophers[index] = new Philosopher(this, index);
		}
	}

	public void acquireFork(int forkNumber) throws InterruptedException {
		forks[forkNumber].acquire();
	}

	public void releaseFork(int forkNumber) {
		forks[forkNumber].release();
	}

	public int leftForkNumber(int philosopherId) {
		return philosopherId;
	}

	public int rightForkNumber(int philosopherId) {
		return (philosopherId + 1) % nofPhilosophers;
	}

	public void addListener(ChangeListener listener) {
		listeners.add(listener);
	}

	public void notifyStateChange(Object source) {
		ChangeEvent event = new ChangeEvent(source);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	public void start() {
		notifyStateChange(this);
		for (int index = nofPhilosophers - 1; index >= 0; index--) {
			philosophers[index].setPriority(Thread.MIN_PRIORITY);
			philosophers[index].start();
		}
	}

	public Philosopher getPhilosopher(int index) {
		return philosophers[index];
	}
}
