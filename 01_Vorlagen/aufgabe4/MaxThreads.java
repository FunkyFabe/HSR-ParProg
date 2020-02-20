package aufgabe4;

public class MaxThreads {
    public static void main(String[] args) {
        for (int i = 1; i < 10000; i++) {
            var thread = new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(1000000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            System.out.println(i);
        }
    }
}
