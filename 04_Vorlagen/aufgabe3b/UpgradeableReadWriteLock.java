package aufgabe3b;

import java.util.concurrent.Semaphore;

public class UpgradeableReadWriteLock {
    private int readLocks = 0;
    private boolean writeLock = false;
    private Thread upgradeThread;

    public synchronized void readLock() throws InterruptedException {
        while (writeLock) {
            wait();
        }
        readLocks++;
    }

    public synchronized void readUnlock() {
        readLocks--;
        notifyAll();
    }

    public synchronized void upgradeableReadLock() throws InterruptedException {
        while (upgradeThread != null || writeLock) {
            wait();
        }
        upgradeThread = Thread.currentThread();
    }

    public synchronized void upgradeableReadUnlock() {
        upgradeThread = null;
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        while (readLocks > 0 || upgradeThread != null || writeLock) {
            if (upgradeThread == Thread.currentThread()) {
                break;
            }
            wait();
        }
        writeLock = true;
    }

    public synchronized void writeUnlock() {
        writeLock = false;
        notifyAll();
    }
}

/*
3a)
Die Unterscheidung ist notwendig, da mehrere  "upgradeableReadLock()" Locks nicht kompatibel sind.
Diese Information kann man aus der Kompatibilitätsmatrix entnehmen.
Hätte man 2 "upgradeableReadLock()" Locks, welche das Upgrade auf den WriteLock() durchführen wollten, würden sich diese gegenseitig blockieren, da WriteLocks exklusiv sind.
 */
