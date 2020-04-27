package aufgabe1;

public class PetersonMutex {
	private volatile boolean state0 = false;
	private volatile boolean state1 = false;
	private volatile int turn = 0;

	// acquire lock by thread 0
	public void thread0Lock() {
		state0 = true;
		turn = 1;
		while (turn == 1 && state1);
	}

	// release lock by thread 0
	public void thread0Unlock() {
		state0 = false;
	}

	// acquire lock by thread 1
	public void thread1Lock() {
		state1 = true;
		turn = 0;
		while (turn == 0 && state0);
	}

	// release lock by thread 1
	public void thread1Unlock() {
		state1 = false;
	}
}
