package aufgabe2;

import java.util.concurrent.atomic.AtomicInteger;

public class BankAccount {
    private AtomicInteger balance = new AtomicInteger(0);

    public void deposit(int amount) {
        balance.addAndGet(amount);
    }

    public boolean withdraw(int amount) {
        int oldValue, newValue;
        do {
            oldValue = balance.get();
            if (oldValue < amount) {
                return false;
            }
            newValue = oldValue - amount;
        } while (!balance.compareAndSet(oldValue, newValue));
        return true;
    }

    public int getBalance() {
        return balance.get();
    }
}
