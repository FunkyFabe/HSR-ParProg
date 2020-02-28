package aufgabe1;

import java.util.ArrayList;

class BankAccount {
    private int balance = 0;

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized boolean withdraw(int amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public int getBalance() {
        return balance;
    }
}

public class BankTest1 {
    private static final int NOF_CUSTOMERS = 10;
    private static final int NOF_TRANSACTIONS = 10000000;

    public static void main(String[] args) throws InterruptedException {
        var account = new BankAccount();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NOF_CUSTOMERS; i++) {
            var thread = new Thread(() -> {
                for (int k = 0; k < NOF_TRANSACTIONS; k++) {
                    account.deposit(100);
                    if (!account.withdraw(100)) {
                        throw new RuntimeException("withdraw() return false");
                    }

                }
            });
            threads.add(thread);
            thread.start();
        }
        for (var thread : threads) {
            thread.join();
        }
        System.out.println("Balance: " + account.getBalance());
    }
}
