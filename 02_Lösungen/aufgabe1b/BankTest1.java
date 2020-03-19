package aufgabe1b;

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

	public synchronized int getBalance() {
		return balance;
	}
}

public class BankTest1 {
	private static final int NOF_CUSTOMERS = 10;
	private static final int NOF_TRANSACTIONS = 10000000;
	
	public static void main(String[] args) throws InterruptedException {
		var account = new BankAccount();
		var customers = new ArrayList<Thread>();
		for (int i = 0; i < NOF_CUSTOMERS; i++) {
			customers.add(new Thread(() -> {
				for (int k = 0; k < NOF_TRANSACTIONS; k++) {
					account.deposit(100);
					boolean success = account.withdraw(100);
					if (!success) { 
						throw new AssertionError("Inconsistency due to race condition");
					}
				}
			}));
		}
		for (var customer: customers) {
			customer.start();
		}
		for (var customer: customers) {
			customer.join();
		}
		if (account.getBalance() != 0) {
			throw new AssertionError("Inconsistency due to race condition. Final balance: " + account.getBalance());
		}
	}
}

