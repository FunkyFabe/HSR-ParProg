package aufgabe2b;

import java.util.ArrayList;
import java.util.Random;

class BankAccount {
	private int balance = 0;

	public synchronized void deposit(int amount) {
		balance += amount;
		notifyAll();
	}

	// returns true if successful, false if failed after timeout.
	public synchronized boolean withdraw(int amount, long timeOutMillis) throws InterruptedException {
		if (timeOutMillis < 0) {
			throw new IllegalArgumentException("timeOutMillis is negative");
		}
		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		while (amount > balance && currentTime - startTime < timeOutMillis) {
			// wait timeout period must be positive: therefore fix currentTime in temporary variable
			wait(timeOutMillis - currentTime + startTime);
			currentTime = System.currentTimeMillis();
		}
		if (amount <= balance) {
			balance -= amount;
			return true;
		} else {
			return false;
		}
	}

	public synchronized int getBalance() {
		return balance;
	}
}

public class BankTest2 {
	private static final int NOF_CUSTOMERS = 10;
	private static final int START_BUDGET = 1000;
	private static final int NOF_TRANSACTIONS = 100;
	
	public static void main(String[] args) throws InterruptedException {
		var account = new BankAccount();
		var customers = new ArrayList<Thread>();
		var random = new Random(4711);
		for (int i = 0; i < NOF_CUSTOMERS; i++) {
			customers.add(new Thread(() -> {
				int amount = random.nextInt(100);
				for (int k = 0; k < NOF_TRANSACTIONS; k++) {
					try {
						boolean success = account.withdraw(amount, 1);
						if (success) {
							account.deposit(amount);
						} else {
							System.out.println("Credit time out " + amount + " by " + Thread.currentThread().getName());
						}
					} catch (InterruptedException e) {
						throw new AssertionError();
					}
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
		if (account.getBalance() != START_BUDGET) {
			throw new AssertionError("Incorrect final balance: " + account.getBalance());
		}
	}
}
