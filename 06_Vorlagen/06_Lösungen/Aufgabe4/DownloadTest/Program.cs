using System;
using System.Diagnostics;
using System.Net.Http;
using System.Threading.Tasks;

namespace DownloadPerformance {
  class Program {
    public static void Main(string[] args) {
      new Program().MeasurePerformance();
    }

    private void MeasurePerformance() {
      var watch = Stopwatch.StartNew();
      var downloads = new Task[] {
                DownloadWebsiteAsync("https://www.google.com"),
                DownloadWebsiteAsync("https://www.bing.com"),
                DownloadWebsiteAsync("https://www.yahoo.com"),
                DownloadWebsiteAsync("https://msdn.microsoft.com"),
                DownloadWebsiteAsync("https://www.facebook.com"),
                DownloadWebsiteAsync("https://www.xing.com")
            };
      Task.WhenAll(downloads).ContinueWith(predecessors =>
         Console.WriteLine($"Elapsed {watch.ElapsedMilliseconds} ms")).Wait();
    }

    private static Task DownloadWebsiteAsync(string url) {
      return Task.Run(() => {
        var watch = Stopwatch.StartNew();
        var client = new HttpClient();
        var html = client.GetStringAsync(url).Result;
        Console.WriteLine($"{url} downloaded (length {html.Length}): {watch.ElapsedMilliseconds} ms");
      });
    }
  }
}
