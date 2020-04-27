using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Windows;

namespace Demo2_AsyncDownloadGUI {
  public partial class MainWindow : Window {
    public MainWindow() {
      InitializeComponent();
    }

    private void AddButton_Click(object sender, RoutedEventArgs e) {
      urlListBox.Items.Add(urlEntryTextBox.Text);
    }

    private async void DownloadButton_Click(object sender, RoutedEventArgs e) {
      var client = new HttpClient();
      foreach (string url in UrlCollection) {
        var data = await client.GetStringAsync(url);
        outputTextBox.Text += string.Format("{0} downloaded: {1} bytes", url, data.Length) + Environment.NewLine;
      }
    }

    private IEnumerable<string> UrlCollection {
      get { return urlListBox.Items.Cast<string>(); }
    }
  }
}
