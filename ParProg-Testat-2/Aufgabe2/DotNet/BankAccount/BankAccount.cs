namespace BankAccount {
  public class BankAccount {
    public int Balance { get; private set; } = 0;

    public void Deposit(int amount) {
      Balance += amount;
    }

    public bool Withdraw(int amount) {
      if (amount <= Balance) {
        Balance -= amount;
        return true;
      } else {
        return false;
      }
    }
  }
}
