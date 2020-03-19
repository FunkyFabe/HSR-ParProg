package aufgabe2a;

import java.util.ArrayList;
import java.util.Random;

class BankAccount {
	private int balance = 0;

	public synchronized void deposit(int amount) {
		balance += amount;
		notifyAll();
	}

	public synchronized void withdraw(int amount) throws InterruptedException {
		while (amount > balance) {
			wait();
		}
		balance -= amount;
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
			int amount = random.nextInt(100);
			customers.add(new Thread(() -> {
				for (int k = 0; k < NOF_TRANSACTIONS; k++) {
					try {
						account.withdraw(amount);
					} catch (InterruptedException e) {
						throw new AssertionError();
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
		if (account.getBalance() != START_BUDGET) {
			throw new AssertionError("Incorrect final balance: " + account.getBalance());
		}
	}
}
