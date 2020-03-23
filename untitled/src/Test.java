import java.util.concurrent.Exchanger;

public class Test {
    public static void main(String[] args) {
        var exchanger = new Exchanger<String>();
        for (int count = 0; count <= 2; count++) {
            new Thread(() -> {
                for (int in = 0; in < 5; in++) {
                    try {
                        var inText = Thread.currentThread().getName() + ": "+ in;
                        var out = exchanger.exchange(inText);
                        System.out.println(Thread.currentThread().getName() + ": give " + in + " got " + out);
                    } catch (InterruptedException e) {
                    }
                }
            }).start();
        }
    }
}
