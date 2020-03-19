package aufgabe1;

public class ConsoleTicker {
	private static void periodTicker(char sign, int intervallMillis) {
		try {
			while (true) {
				System.out.print(sign);
				Thread.sleep(intervallMillis);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(() -> periodTicker('.', 10)).start();
		new Thread(() -> periodTicker('*', 20)).start();
		// more tickers possible ...
	}
}
