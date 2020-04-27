using System;
using System.Threading.Tasks;

namespace ParallelQuickSort {
  public class ParallelQuickSort {
    private const int Threshold = 10_000;

    public static void Sort(int[] array) {
      if (array == null || array.Length == 0) {
        new ArgumentException("number array must be at least of length 1");
      }
      QuickSort(array, 0, array.Length - 1);
    }

    private static void QuickSort(int[] array, int left, int right) {
      var i = left;
      var j = right;
      var m = array[(left + right) / 2];
      while (i <= j) {
        while (array[i] < m) { i++; }
        while (array[j] > m) { j--; }
        if (i <= j) {
          var t = array[i]; array[i] = array[j]; array[j] = t;
          i++; j--;
        }
      }
      if (j - left > Threshold && right - i > Threshold) {
        ParallelSort(array, left, right, i, j);
      } else {
        if (j > left) { QuickSort(array, left, j); }
        if (i < right) { QuickSort(array, i, right); }
      }
    }

    private static void ParallelSort(int[] array, int left, int right, int i, int j) {
      var leftTask = Task.Run(() => QuickSort(array, left, j));
      QuickSort(array, i, right);
      leftTask.Wait();
      // Alternative:
      //Parallel.Invoke(
      //  () => QuickSort(array, left, j),
      //  () => QuickSort(array, i, right)
      //);
      // Alternative:
      //var leftTask = Task.Run(() => QuickSort(array, left, j));
      //var rightTask = Task.Run(() => QuickSort(array, i, right));
      //rightTask.Wait();
      //leftTask.Wait();
    }
  }
}
