package aufgabe2;

import java.util.concurrent.CountDownLatch;

public class LatchBasedRaceControl extends AbstractRaceControl {
	private final CountDownLatch ready = new CountDownLatch(CARS);
	private final CountDownLatch start = new CountDownLatch(1);
	private final CountDownLatch finished = new CountDownLatch(1);
	private final CountDownLatch honors = new CountDownLatch(CARS);

	protected void waitForAllToBeReady() throws InterruptedException {
		ready.await();
	}

	public void readyToStart() {
		ready.countDown();
	}

	public void waitForStartSignal() throws InterruptedException {
		start.await();
	}

	protected void giveStartSignal() {
		start.countDown();
	}

	protected void waitForFinishing() throws InterruptedException {
		finished.await();
	}

	public boolean isOver() {
		return finished.getCount() == 0;
	}

	public void passFinishLine() {
		finished.countDown();
		honors.countDown();
	}

	public void waitForLapOfHonor() throws InterruptedException {
		honors.await();
	}
}
