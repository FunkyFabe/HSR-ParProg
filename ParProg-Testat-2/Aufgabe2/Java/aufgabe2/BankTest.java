package aufgabe2;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class BankCustomer extends Thread {
	private static final int TRANSACTIONS = 1000000;
	private BankAccount account;
	private int maxCredit;

	public BankCustomer(BankAccount account, int maxCredit) {
		this.account = account;
		this.maxCredit = maxCredit;
	}

	@Override
	public void run() {
		var random = ThreadLocalRandom.current();
		for (int i = 0; i < TRANSACTIONS; i++) {
			int amount = random.nextInt(maxCredit);
			account.deposit(amount);
			boolean success = account.withdraw(amount);
			if (!success) {
				throw new AssertionError("Race condition");
			}
		}
	}
}

public class BankTest {
	private final static int CUSTOMERS = 10;
	private final static int START_BUDGET = 1000;

	public static void main(String[] args) throws InterruptedException {
		var account = new BankAccount();
		var customers = new ArrayList<BankCustomer>();
		var random = new Random(4711);
		for (int count = 0; count < CUSTOMERS; count++) {
			customers.add(new BankCustomer(account, random.nextInt(START_BUDGET)));
		}
		account.deposit(START_BUDGET);
		long startTime = System.currentTimeMillis();
		for (var customer : customers) {
			customer.start();
		}
		for (var customer : customers) {
			customer.join();
		}
		long stopTime = System.currentTimeMillis();
		System.out.println("Total time " + (stopTime - startTime) + " ms");
		if (account.getBalance() != START_BUDGET) {
			throw new AssertionError("Incorrect final balance: " + account.getBalance());
		}
	}
}
