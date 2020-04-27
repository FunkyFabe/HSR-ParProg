using System;
using System.Threading;
using System.Threading.Tasks;

namespace CyclicTasksTest {
  public class Program {
    public const int NofTasks = 10;
    public const int NofRounds = 5;

    public static void Main() {
      var nofProcessores = Environment.ProcessorCount;
      ThreadPool.SetMaxThreads(nofProcessores, nofProcessores);
      var turnController = new TurnController(NofTasks);
      var taskArray = new Task[NofTasks];
      for (int i = 0; i < NofTasks; i++) {
        var myTurn = i;
        taskArray[i] = Task.Run(() => {
          for (int k = 0; k < NofRounds; k++) {
            turnController.AwaitTurn(myTurn);
            Console.WriteLine("Task {0} with turn {1}", myTurn, k);
            turnController.NextTurn();
          }
        });
      }
      Task.WaitAll(taskArray);
    }
  }

  internal class TurnController {
    private int _currentTurn = 0;
    public int _nofParticipants;

    public TurnController(int nofParticipants) {
      _nofParticipants = nofParticipants;
    }

    public void AwaitTurn(int turn) {
      lock (this) {
        while (_currentTurn != turn) { Monitor.Wait(this); }
      }
    }

    public void NextTurn() {
      lock (this) {
        _currentTurn = (_currentTurn + 1) % _nofParticipants;
        Monitor.PulseAll(this);
      }
    }
  }
}
