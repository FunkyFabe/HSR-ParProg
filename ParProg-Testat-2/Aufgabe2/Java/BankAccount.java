package aufgabe2;

public class BankAccount {
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
