package aufgabe2;

import java.util.concurrent.BrokenBarrierException;

public abstract class AbstractRaceControl {
	public final int CARS = 10;
	public final int LAPS = 5;
	private final RaceCar raceCars[];
	
	public AbstractRaceControl() {
		raceCars = new RaceCar[CARS];
		for (int count = 0; count < CARS; count++) {
			raceCars[count] = new RaceCar(this, "Car # " + count, LAPS);
		}
	}

	// Called by race control
	protected abstract void waitForAllToBeReady() throws InterruptedException, BrokenBarrierException;

	// Called by race cars
	public abstract void readyToStart() throws BrokenBarrierException, InterruptedException;

	// Called by race control
	protected abstract void giveStartSignal();

	// Called by race cars
	public abstract void waitForStartSignal() throws InterruptedException;

	// Called by race control
	protected abstract void waitForFinishing() throws InterruptedException;

	// Called by race cars
	public abstract boolean isOver();

	public abstract void passFinishLine();

	// Called by race control and race cars
	public abstract void waitForLapOfHonor() throws InterruptedException;

	public void runRace() throws InterruptedException, BrokenBarrierException {
		System.out.println("Preparing race...");
		startAllEngines();
		System.out.println("Race starts soon...");
		waitForAllToBeReady();
		System.out.println("Race started: go!");
		giveStartSignal();
		waitForFinishing();
		waitForLapOfHonor();
		finishRace();
	}

	private final void startAllEngines() {
		for (int count = 0; count < CARS; count++) {
			raceCars[count].start();
		}
	}

	private final void finishRace() throws InterruptedException {
		for (int count = 0; count < CARS; count++) {
			raceCars[count].join();
		}
		System.out.println("All race cars are parked");
	}
}
