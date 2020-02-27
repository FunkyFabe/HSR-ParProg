package aufgabe1;

class BankAccount {
	private int balance = 0;

	public void deposit(int amount) {
		balance += amount;
	}

	public boolean withdraw(int amount) {
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

	public static void main(String[] args) {
		var account = new BankAccount();
		for (int i = 0; i < NOF_CUSTOMERS; i++) {
			new Thread(() -> {
				for (int k = 0; k < NOF_TRANSACTIONS; k++) {
					account.deposit(100);
					account.withdraw(100);
				}
			}).start();
		}
	}
}
