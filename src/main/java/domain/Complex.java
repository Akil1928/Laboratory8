package domain;

import java.util.ArrayList;
import java.util.List;

public class Complex {
    private List<Integer> lowValues;
    private List<Integer> highValues;
    private List<Integer> pivotValues;
    private List<int[]> shellSortSubArrays;
    private int[] counterArray;
    private int recursiveCallCount;
    private int numberOfComparisons;
    private int[] tempArray;


    public Complex() {
        lowValues = new ArrayList<>();
        highValues = new ArrayList<>();
        pivotValues = new ArrayList<>();
        shellSortSubArrays = new ArrayList<>();
        recursiveCallCount = 0;
        numberOfComparisons = 0;
    }

    public void resetCounters() {
        recursiveCallCount = 0;
        numberOfComparisons = 0;
    }

    public int getRecursiveCallCount() {
        return recursiveCallCount;
    }

    public int getNumberOfComparisons() {
        return numberOfComparisons;
    }

    // QuickSort
    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            recursiveCallCount++;

            lowValues.add(low);
            highValues.add(high);

            int pi = partition(arr, low, high);
            pivotValues.add(arr[pi]);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            numberOfComparisons++;
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // RadixSort
    public void radixSort(int[] arr, int n) {
        int max = getMax(arr, n);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, n, exp);
        }
    }

    private int getMax(int[] arr, int n) {
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            numberOfComparisons++;
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    private void countSort(int[] arr, int n, int exp) {
        int[] output = new int[n];
        counterArray = new int[10];

        for (int i = 0; i < n; i++) {
            counterArray[(arr[i] / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            counterArray[i] += counterArray[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[counterArray[(arr[i] / exp) % 10] - 1] = arr[i];
            counterArray[(arr[i] / exp) % 10]--;
        }

        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }

    // MergeSort
    public void mergeSort(int[] arr, int left, int right) {
        this.tempArray = new int[arr.length];  // Inicializar el array temporal
        mergeSortHelper(arr, left, right);
    }
    private void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            recursiveCallCount++;

            lowValues.add(left);
            highValues.add(right);

            int center = (left + right) / 2;
            mergeSortHelper(arr, left, center);
            mergeSortHelper(arr, center + 1, right);
            merge(arr, left, center + 1, right);
        }
    }


    private void merge(int[] arr, int left, int right, int rightEnd) {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while (left <= leftEnd && right <= rightEnd) {
            numberOfComparisons++;
            if (arr[left] <= arr[right]) {
                tempArray[k++] = arr[left++];
            } else {
                tempArray[k++] = arr[right++];
            }
        }

        while (left <= leftEnd) {
            tempArray[k++] = arr[left++];
        }

        while (right <= rightEnd) {
            tempArray[k++] = arr[right++];
        }

        // Copiar al array original y también mantener el estado del tempArray
        for (int i = 0; i < num; i++, rightEnd--) {
            arr[rightEnd] = tempArray[rightEnd];
        }
    }

    public int[] getTempArray() {
        return tempArray;
    }


    // ShellSort
    public void shellSort(int[] arr) {
        int n = arr.length;
        recursiveCallCount = 0;
        shellSortSubArrays.clear();

        for (int gap = n/2; gap > 0; gap /= 2) {
            recursiveCallCount++;

            int[] subArray = new int[12];
            int subArrayIndex = 0;

            for (int i = gap; i < n && subArrayIndex < 12; i++) {
                subArray[subArrayIndex++] = arr[i];
            }
            shellSortSubArrays.add(subArray.clone());

            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                    numberOfComparisons++;
                }
                arr[j] = temp;
            }
        }
    }


    public int[] getLowValues() {
        return lowValues.stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] getHighValues() {
        return highValues.stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] getPivotValues() {
        return pivotValues.stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] getCounterArray() {
        return counterArray;
    }

    public List<int[]> getShellSortSubArrays() {
        return shellSortSubArrays;
    }
}