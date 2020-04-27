using System;
using System.Threading.Tasks;

namespace Pitfall1 {
  class Program {
    public static async Task Main() {
      var task = IsPrimeAsync(10000000000000061L);
      Console.WriteLine("Other work");
      var result = await task;
      Console.WriteLine($"Result {result}");
    }

    public static async Task<bool> IsPrimeAsync(long number) {
      for (long i = 2; i * i <= number; i++) {
        if (number % i == 0) { return false; }
      }
      return true;
    }
  }
}
