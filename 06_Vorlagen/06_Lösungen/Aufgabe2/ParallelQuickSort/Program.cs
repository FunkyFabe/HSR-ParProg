using System;
using System.Diagnostics;

namespace ParallelQuickSort {
    public class Program {
        public static void Main() {
          const int N = 50_000_000;
          for (int round = 0; round < 3; round++) {
            var array = GetRandomArray(N);
            var watch = Stopwatch.StartNew();
            ParallelQuickSort.Sort(array);
            Console.WriteLine($"Total sort time: {watch.ElapsedMilliseconds} ms");
          }
        }

        private static int[] GetRandomArray(int length) {
            var random = new Random(4711);
            var array = new int[length];
            for (int i = 0; i < length; i++) {
                array[i] = random.Next();
            }
            return array;
        }
    }
}
