package aufgabe4;

public class MaximumThreads {
	public static void main(String[] args) {
		int i = 0;
		while (true) {
			i++;
			new Thread(() -> {
				try {
					while (true) {
						Thread.sleep(10000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
			System.out.println(i);
		}
	}
}
