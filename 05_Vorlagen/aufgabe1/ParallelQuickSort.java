package aufgabe1;

import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends RecursiveAction {
    private int THRESHOLD = 16000;
    private int[] data;
    private int leftBoundary;
    private int rightBoundary;

    public ParallelQuickSort(int[] data, int leftBoundary, int rightBoundary) {
        this.data = data;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
    }

    @Override
    protected void compute() {
        if (rightBoundary - leftBoundary > THRESHOLD) {
            quickSort(data, leftBoundary, rightBoundary, true);
        } else {
            quickSort(data, leftBoundary, rightBoundary, false);
        }
    }

    private static void quickSort(int[] data, int left, int right, boolean parallel) {
        int i = left, j = right;
        long m = data[(left + right) / 2];
        do {
            while (data[i] < m) {
                i++;
            }
            while (data[j] > m) {
                j--;
            }
            if (i <= j) {
                int t = data[i];
                data[i] = data[j];
                data[j] = t;
                i++;
                j--;
            }
        } while (i <= j);
        if (parallel) {
            var leftPartition = new ParallelQuickSort(data, left, j);
            var rightPartition = new ParallelQuickSort(data, i, right);
            invokeAll(leftPartition, rightPartition);
        } else {
            // recursively sort the two partitions
            if (j > left) {
                quickSort(data, left, j, false);
            }
            if (i < right) {
                quickSort(data, i, right, false);
            }
        }
    }
}

/*
Threshold: 1
Time reference: 8709 ms
Time parallel: 2491 ms
Speedup factor: 3.4961862705740665

Threshold: 1000
Time reference: 8548 ms
Time parallel: 2275 ms
Speedup factor: 3.7573626373626374

Threshold: 1'000
Time reference: 8650 ms
Time parallel: 2104 ms
Speedup factor: 4.111216730038023

Threshold: 10'000
Time reference: 8867 ms
Time parallel: 2152 ms
Speedup factor: 4.120353159851301

Threshold: 16'000
Time reference: 8644 ms
Time parallel: 1872 ms
Speedup factor: 4.617521367521367

Threshold: 1'000'000
Time reference: 9065 ms
Time parallel: 2313 ms
Speedup factor: 3.91915261565067
 */