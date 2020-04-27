import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TheSupercomputer {
	private enum StatusType {
		BORED, STARTING, CONFUSED, ERROR, RECOVERING_FROM_ERROR, OVERHEATED, CALCULATING
	}

	private static final int ITERATIONS = 5;
	private List<ChangeListener> listeners = new ArrayList<>();
	private StatusType status = StatusType.BORED;
	private int dotsCount = 0;

	public void addListener(ChangeListener listener) {
		listeners.add(listener);
	}

	public void notifyChange() {
		var event = new ChangeEvent(this);
		for (var listener : listeners) {
			listener.stateChanged(event);
		}
	}

	public String calculateUltimateAnswer() {
		try {
			int firstPart = calculateFirstPart();
			int secondPart = calculateSecondPart();
			int modulo = calculateModulo();
			status = StatusType.BORED;
			notifyChange();
			return Integer.toString(firstPart * secondPart % modulo);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private int calculateModulo() throws InterruptedException {
		status = StatusType.OVERHEATED;
		doSleepLoop();
		status = StatusType.CALCULATING;
		doSleepLoop();
		return 53;
	}

	private int calculateSecondPart() throws InterruptedException {
		status = StatusType.CONFUSED;
		doSleepLoop();
		doSleepLoop();
		status = StatusType.ERROR;
		doSleepLoop();
		doSleepLoop();
		status = StatusType.RECOVERING_FROM_ERROR;
		doSleepLoop();
		status = StatusType.CALCULATING;
		doSleepLoop();
		return 41;
	}

	private int calculateFirstPart() throws InterruptedException {
		status = StatusType.STARTING;
		doSleepLoop();
		status = StatusType.CALCULATING;
		doSleepLoop();
		return 23;
	}

	private void doSleepLoop() throws InterruptedException {
		for (int i = 0; i < ITERATIONS; i++) {
			Thread.sleep(500);
			dotsCount %= ITERATIONS;
			dotsCount++;
			notifyChange();
		}
	}

	public String getStatus() {
		return status.name() + dotText();
	}

	private String dotText() {
		var chars = new char[dotsCount];
		Arrays.fill(chars, '.');
		return new String(chars);
	}
}
