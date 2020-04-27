using System;
using System.Threading.Tasks;

namespace Pitfall4 {
  public class BankAccount {
    public int Balance { get; private set; }

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

  public class RaceSample {
    public async Task CustomerBehaviorAsync(BankAccount account) {
      await Task.Run(() => Console.WriteLine("Start"));
      // runs as continuation
      for (int i = 0; i < 1000000; i++) {
        account.Deposit(100);
        account.Withdraw(100);
      }
    }

    public async Task TestRunAsync() {
      var account = new BankAccount();
      var customer1 = CustomerBehaviorAsync(account);
      var customer2 = CustomerBehaviorAsync(account);
      await customer1;
      await customer2;
      if (account.Balance != 0) {
        throw new Exception(string.Format("Race condition occurred: Balance is {0}", account.Balance));
      }
    }
  }
}
