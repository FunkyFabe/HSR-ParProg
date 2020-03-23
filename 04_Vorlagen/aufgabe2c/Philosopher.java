package aufgabe2c;

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
        // acquire both forks
        table.acquireFork(table.firstFork(id));
        Thread.sleep(500);
        table.acquireFork(table.secondFork(id));
    }

    private void putForks() {
        table.releaseFork(table.firstFork(id));
        table.releaseFork(table.secondFork(id));
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
            new AssertionError(e);
        }
    }
}