package aufgabe1;

import java.util.Random;

public class QuickSortSample {
	private static final int NOF_ELEMENTS = 50_000_000;

	// sorts the partition between array[left] and array[right]
	private static void quickSort(int[] array, int left, int right) {
		// split into two partitions
		int i = left, j = right;
		long m = array[(left + right) / 2];
		do {
			while (array[i] < m) {
				i++;
			}
			while (array[j] > m) {
				j--;
			}
			if (i <= j) {
				int t = array[i];
				array[i] = array[j];
				array[j] = t;
				i++;
				j--;
			}
		} while (i <= j);
		// recursively sort the two partitions
		if (j > left) {
			quickSort(array, left, j);
		}
		if (i < right) {
			quickSort(array, i, right);
		}
	}

	private static int[] createRandomArray(int length) {
		var random = new Random(4711);
		var numberArray = new int[length];
		for (int i = 0; i < length; i++) {
			numberArray[i] = random.nextInt();
		}
		return numberArray;
	}

	private static void checkSorted(int[] numberArray) {
		for (int i = 0; i < numberArray.length - 1; i++) {
			if (numberArray[i] > numberArray[i + 1]) {
				throw new RuntimeException("Not sorted");
			}
		}
	}

	public static void main(String[] args) {
		var numberArray = createRandomArray(NOF_ELEMENTS);
		long startTime = System.currentTimeMillis();
		quickSort(numberArray, 0, numberArray.length - 1);
		long stopTime = System.currentTimeMillis();
		System.out.println("Total time: " + (stopTime - startTime) + " ms");
		checkSorted(numberArray);
	}
}
