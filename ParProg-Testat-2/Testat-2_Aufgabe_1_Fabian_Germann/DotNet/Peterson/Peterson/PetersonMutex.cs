using System.Threading;

namespace Peterson {
  public class PetersonMutex {
    private  bool state0 = false;
    private  bool state1 = false;
    private  int turn = 0;

    // acquire lock by thread 0
    public void Thread0Lock() {
      state0 = true;
      turn = 1;
      Thread.MemoryBarrier();
      while (turn == 1 && state1) ;
    }

    // release lock by thread 0
    public void Thread0Unlock() {
      state0 = false;
    }

    // acquire lock by thread 1
    public void Thread1Lock() {
      state1 = true;
      turn = 0;
      Thread.MemoryBarrier();
      while (turn == 0 && state0) ;
    }

    // release lock by thread 1
    public void Thread1Unlock() {
      state1 = false;
    }
  }
}
