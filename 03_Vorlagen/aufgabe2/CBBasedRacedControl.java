package aufgabe2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CBBasedRacedControl extends AbstractRaceControl {
    private final CyclicBarrier barrier = new CyclicBarrier(CARS + 1);
    private final CountDownLatch finished = new CountDownLatch(1);
    private final CountDownLatch started = new CountDownLatch(1);

    protected void waitForAllToBeReady() throws InterruptedException {
        try {
            barrier.await();
        } catch (BrokenBarrierException e) {
            throw new AssertionError(e);
        }
    }

    public void readyToStart() {
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new AssertionError(e);
        }
    }

    public void waitForStartSignal() throws InterruptedException {
        started.await();
    }

    protected void giveStartSignal() {
        started.countDown();
    }

    protected void waitForFinishing() throws InterruptedException {
        finished.await();
    }

    public boolean isOver() {
        return finished.getCount() == 0;
    }

    public void passFinishLine() {
        finished.countDown();
    }

    public void waitForLapOfHonor() throws InterruptedException {
        try {
            barrier.await();
        } catch (BrokenBarrierException e) {
            throw new AssertionError(e);
        }
    }
}
