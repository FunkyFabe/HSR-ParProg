using System;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;

namespace Pitfall2 {
  class Downloader {
    public async Task DownloadAsync() {
      Console.WriteLine("BEFORE " + Thread.CurrentThread.ManagedThreadId);
      var client = new HttpClient();
      var task = client.GetStringAsync("https://msdn.microsoft.com");
      await task;
      Console.WriteLine("AFTER " + Thread.CurrentThread.ManagedThreadId);
    }
  }
}
