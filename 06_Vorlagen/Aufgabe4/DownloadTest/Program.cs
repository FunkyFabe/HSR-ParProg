using System;
using System.Diagnostics;
using System.Net.Http;

namespace DownloadTest {
  class Program {
    public static void Main() {
      new Program().MeasurePerformance();
    }

    private void MeasurePerformance() {
      var watch = Stopwatch.StartNew();
      DownloadWebsite("https://www.google.com");
      DownloadWebsite("https://www.bing.com");
      DownloadWebsite("https://www.yahoo.com");
      DownloadWebsite("https://msdn.microsoft.com");
      DownloadWebsite("https://www.facebook.com");
      DownloadWebsite("https://www.xing.com");
      Console.WriteLine($"Elapsed {watch.ElapsedMilliseconds} ms");
    }

    private static void DownloadWebsite(string url) {
      var watch = Stopwatch.StartNew();
      var client = new HttpClient();
      var html = client.GetStringAsync(url).Result;
      Console.WriteLine($"{url} downloaded (length {html.Length}): {watch.ElapsedMilliseconds} ms");
    }
  }
}
