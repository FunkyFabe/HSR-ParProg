package aufgabe2;

import java.util.concurrent.CountDownLatch;

public class CDLBasedRaceControl extends AbstractRaceControl {
    private CountDownLatch waitingForReady = new CountDownLatch(CARS);
    private CountDownLatch start = new CountDownLatch(1);
    private CountDownLatch raceFinished = new CountDownLatch(1);
    private CountDownLatch waitingForLapOfHonor = new CountDownLatch(CARS);
    private boolean raceOver = false;

    @Override
    protected void waitForAllToBeReady() throws InterruptedException {
        waitingForReady.await();
    }

    @Override
    public void readyToStart() {
        waitingForReady.countDown();
    }

    @Override
    protected void giveStartSignal() {
        start.countDown();
    }

    @Override
    public void waitForStartSignal() throws InterruptedException {
        start.await();
    }

    @Override
    protected void waitForFinishing() throws InterruptedException {
        raceFinished.await();
    }

    @Override
    public boolean isOver() {
        return raceOver;
    }

    @Override
    public void passFinishLine() {
        raceOver = true;
        raceFinished.countDown();
        waitingForLapOfHonor.countDown();

    }

    @Override
    public void waitForLapOfHonor() throws InterruptedException {
        waitingForLapOfHonor.await();
    }
}
