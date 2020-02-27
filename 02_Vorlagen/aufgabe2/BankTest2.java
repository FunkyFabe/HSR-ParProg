package aufgabe2;

import java.util.ArrayList;
import java.util.Random;

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
					while (account.getBalance() < amount) {
						Thread.yield();
					}
					account.withdraw(amount);
					account.deposit(amount);
				}
			}));
		}
		for (var customer: customers) {
			customer.start();
		}
		account.deposit(START_BUDGET);
		for (var customer: customers) {
			customer.join();
		}
	}
}
