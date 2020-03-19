package aufgabe2;

import java.util.concurrent.BrokenBarrierException;

public class FormulaOne {
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		new CDLBasedRaceControl().runRace();
	}
}
