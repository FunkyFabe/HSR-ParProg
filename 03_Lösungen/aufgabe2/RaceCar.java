package aufgabe2;

public class RaceCar extends Thread {
	private final AbstractRaceControl race;
	private int laps;

	public RaceCar(AbstractRaceControl myRace, String name, int nofLaps) {
		super(name);
		race = myRace;
		laps = nofLaps;
	}

	@Override
	public void run() {
		try {
			printState("starting engine, driving slowly");
			race.readyToStart();
			race.waitForStartSignal();
			printState("racing...");
			while (laps > 0 && !race.isOver()) {
				printState("remaining laps: " + laps);
				simulateRaceLap();
				laps--;
			}
			printState("pass finish line");
			race.passFinishLine();
			race.waitForLapOfHonor();
			printState("waves to audience");
			simulateRaceLap();
		} catch (InterruptedException e) {
			throw new AssertionError(e);
		}
	}

	private void simulateRaceLap() throws InterruptedException {
		Thread.sleep((int) (1000 * Math.random()));
	}

	private void printState(String message) {
		System.out.println(getName() + " " + message);
	}
}
