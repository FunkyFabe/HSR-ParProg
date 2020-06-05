using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading;

namespace BankAccount {
  public class BankTest {
    private const int Customers = 10;
    private const int StartBudget = 1000;
    private const int Transactions = 1000000;

    public static void Main() {
      var account = new BankAccount();
      var customers = new List<Thread>();
      var random = new Random(4711);
      for (int count = 0; count < Customers; count++) {
        var credit = random.Next(StartBudget);
        customers.Add(new Thread(() => {
          for (var k = 0; k < Transactions; k++) {
            account.Deposit(credit);
            if (!account.Withdraw(credit)) {
              throw new Exception("Race condition");
            }
          }
        }));
      }
      account.Deposit(StartBudget);
      var watch = Stopwatch.StartNew();
      foreach (var customer in customers) {
        customer.Start();
      }
      foreach (var customer in customers) {
        customer.Join();
      }
      Console.WriteLine($"Total time {watch.ElapsedMilliseconds} ms");
      if (account.Balance != StartBudget) {
        throw new Exception($"Incorrect final balance: {account.Balance}");
      }
    }
  }
}
