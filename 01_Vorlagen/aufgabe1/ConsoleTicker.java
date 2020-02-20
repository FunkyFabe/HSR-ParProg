package aufgabe1;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleTicker {
    private static void periodTicker(char sign, int intervallMillis) throws InterruptedException {
        while (true) {
            System.out.print(sign);
            Thread.sleep(intervallMillis);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        var threadA = new Thread(() -> {
            try {
                periodTicker('.', 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        var threadB = new Thread(() -> {
            try {
                periodTicker('A', 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.setDaemon(true);
        threadB.setDaemon(true);
        threadA.start();
        threadB.start();
		System.in.read();
    }


}



