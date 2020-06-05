using System;
using System.Threading;

namespace Peterson {
  class Program {
    static void Main() {
      const int Rounds = 10_000_000;
      var mutex = new PetersonMutex();
      int counter = 0;
      var thread0 = new Thread(() => {
        for (int count = 0; count < Rounds; count++) {
          mutex.Thread0Lock();
          counter++;
          mutex.Thread0Unlock();
        }
      });
      var thread1 = new Thread(() => {
        for (int count = 0; count < Rounds; count++) {
          mutex.Thread1Lock();
          counter--;
          mutex.Thread1Unlock();
        }
      });
      thread0.Start();
      thread1.Start();
      thread0.Join();
      thread1.Join();
      if (counter != 0) {
        throw new Exception("Wrong synchronization");
      }
      Console.WriteLine("Completed");
    }
  }
}
