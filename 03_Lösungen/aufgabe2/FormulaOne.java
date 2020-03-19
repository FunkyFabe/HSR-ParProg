package aufgabe2;

public class FormulaOne {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("------ BARRIER ------");
		new BarrierBasedRaceControl().runRace();
		System.out.println("------ LATCH ------");
		new LatchBasedRaceControl().runRace();
		System.out.println("------ MONITOR ------");
		new MonitorBasedRaceControl().runRace();
	}
}
