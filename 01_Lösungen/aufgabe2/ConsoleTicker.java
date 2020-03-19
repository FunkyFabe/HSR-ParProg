package aufgabe2;

import java.io.IOException;

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

	public static void main(String[] args) throws IOException {
		var tickerA = new Thread(() -> periodTicker('.', 10));
		tickerA.setDaemon(true);
		tickerA.start();
		var tickerB = new Thread(() -> periodTicker('*', 20));
		tickerB.setDaemon(true);
		tickerB.start();

		System.in.read();
	}
}
