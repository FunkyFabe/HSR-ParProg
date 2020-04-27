using System.Threading.Tasks;

namespace Pitfall4 {
  class Program {
    public static async Task Main() {
      await new RaceSample().TestRunAsync();
    }
  }
}
