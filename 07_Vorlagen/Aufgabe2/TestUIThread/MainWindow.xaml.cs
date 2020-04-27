using System.Windows;

namespace TestUIThread {
  public partial class MainWindow : Window {
    public MainWindow() {
      InitializeComponent();
    }

    private void StartCalculationButton_Click(object sender, RoutedEventArgs e) {
      if (!long.TryParse(baseNumberTextBox.Text, out long initial) ||
                !long.TryParse(succeedingPrimesTextBox.Text, out long amount)) {
        return;
      }
      progressLabel.Content = "computing...";
      ComputeNextPrimes(initial, amount);
      progressLabel.Content = "done";
    }

    private void ComputeNextPrimes(long inital, long amount) {
      for (var number = inital; number < inital + amount; number++) {
        if (IsPrime(number)) {
          resultListView.Items.Add(number);
        }
      }
    }

    private bool IsPrime(long number) {
      for (long i = 2; i * i <= number; i++) {
        if (number % i == 0) {
          return false;
        }
      }
      return true;
    }
  }
}
