package aufgabe2;

import java.util.ArrayList;
import java.util.Random;

class BankAccount {
    private int balance = 0;

    public synchronized void deposit(int amount) {
        balance += amount;
        notifyAll();
    }

    public synchronized boolean withdraw(int amount, long timeOutMillis) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (amount > balance) {
            System.out.println("Starttime: " + System.currentTimeMillis());
            System.out.println("Current: " + System.currentTimeMillis());
            System.out.println("Diff: " + (System.currentTimeMillis() - startTime) + " \n");
            if (System.currentTimeMillis() - startTime > timeOutMillis) {
                System.out.println("Abort");
                return false;
            }
            wait(timeOutMillis / 3);
        }
        balance -= amount;
        return true;
    }

    public synchronized int getBalance() {
        return balance;
    }
}

public class BankTest2 {
    private static final int NOF_CUSTOMERS = 10;
    private static final int START_BUDGET = 1000;
    private static final int NOF_TRANSACTIONS = 100;
    private static final long TIME_OUT = 10;

    public static void main(String[] args) throws InterruptedException {
        var account = new BankAccount();
        var customers = new ArrayList<Thread>();
        var random = new Random(4711);
        for (int i = 0; i < NOF_CUSTOMERS; i++) {
            int amount = random.nextInt(100);
            customers.add(new Thread(() -> {
                for (int k = 0; k < NOF_TRANSACTIONS; k++) {
                    try {
                        account.withdraw(amount, TIME_OUT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    account.deposit(amount);
                }
            }));
        }
        for (var customer : customers) {
            customer.start();
        }
        account.deposit(START_BUDGET);
        for (var customer : customers) {
            customer.join();
        }
        System.out.println("Balance: " + account.getBalance());
    }
}
