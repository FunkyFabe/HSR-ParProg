package aufgabe2;

public class MonitorBasedRaceControl extends AbstractRaceControl {
	private int startingGridReady = CARS;
	private boolean raceStarted = false;
	private boolean raceOver = false;
	private int waitingForLapOfHonor = CARS;

	protected synchronized void waitForAllToBeReady() throws InterruptedException {
		while (startingGridReady > 0) {
			wait();
		}
	}

	public synchronized void readyToStart() {
		startingGridReady--;
		if (startingGridReady == 0) {
			notifyAll();
		}
	}

	public synchronized void waitForStartSignal() throws InterruptedException {
		while (!raceStarted) {
			wait();
		}
	}

	protected synchronized void giveStartSignal() {
		raceStarted = true;
		notifyAll();
	}

	protected synchronized void waitForFinishing() throws InterruptedException {
		while (!raceOver) {
			wait();
		}
	}

	public synchronized boolean isOver() {
		return raceOver;
	}

	public synchronized void passFinishLine() {
		raceOver = true;
		waitingForLapOfHonor--;
		notifyAll();
	}

	public synchronized void waitForLapOfHonor() throws InterruptedException {
		while (waitingForLapOfHonor > 0) {
			wait();
		}
	}
}
