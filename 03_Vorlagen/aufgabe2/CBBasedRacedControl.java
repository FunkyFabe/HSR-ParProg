package aufgabe2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CBBasedRacedControl extends AbstractRaceControl {
    CyclicBarrier readyToStart = new CyclicBarrier(CARS + 1); // 1 represents the race control
    CyclicBarrier start = new CyclicBarrier(1);


    @Override
    protected void waitForAllToBeReady() throws InterruptedException, BrokenBarrierException {
        readyToStart.await();
    }

    @Override
    public void readyToStart() throws BrokenBarrierException, InterruptedException {
        readyToStart.await();
    }

    @Override
    protected void giveStartSignal() {

    }

    @Override
    public void waitForStartSignal() throws InterruptedException {

    }

    @Override
    protected void waitForFinishing() throws InterruptedException {

    }

    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public void passFinishLine() {

    }

    @Override
    public void waitForLapOfHonor() throws InterruptedException {

    }
}
