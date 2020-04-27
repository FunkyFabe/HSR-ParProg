using System.Threading.Tasks;

namespace Pitfall2 {
  class Program {
    public static async Task Main() {
      await new Downloader().DownloadAsync();
    }
  }
}
