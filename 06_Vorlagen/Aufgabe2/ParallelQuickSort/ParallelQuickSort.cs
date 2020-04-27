using System;

namespace ParallelQuickSort {
  public class ParallelQuickSort {
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
            // TODO: Parallelize
            if (j > left) { QuickSort(array, left, j); }
            if (i < right) { QuickSort(array, i, right); }
        }
    }
}
