using System.Threading.Tasks;
using System.Windows;

namespace Pitfall5 {
  public partial class MainWindow : Window {
    public MainWindow() {
      InitializeComponent();
    }

    private void CalculationButton_Click(object sender, RoutedEventArgs e) {
      Task<string> task = CalculateAsync();
      resultLabel.Content = task.Result;
    }

    private async Task<string> CalculateAsync() {
      long number = long.Parse(inputTextBox.Text);
      return await Task.Run(() => {
        for (long i = 2; i * i <= number; i++) {
          if (number % i == 0) { return "Not prime"; }
        }
        return "Prime";
      });
    }
  }
}
