package domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;

class ComplexTest {
    private Complex complex;
    private Random random;
    private static final int ARRAY_SIZE = 80000;

    public ComplexTest() {
        complex = new Complex();
        random = new Random();
    }

    private int[] generateRandomArray() {
        int[] array = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(10000);
        }
        return array;
    }

    private void printArray(String label, int[] array, int limit) {
        System.out.print(label + ": ");
        for (int i = 0; i < Math.min(array.length, limit); i++) {
            System.out.print(array[i] + (i < Math.min(array.length, limit) - 1 ? ", " : ""));
        }
        System.out.println();
    }

    @Test
    void quickSortTest() {
        System.out.println("quickSort - Test");
        System.out.println("Algorithm: quickSort");
        int[] array = generateRandomArray();

        printArray("Original Array", array, 20);

        complex.resetCounters();
        complex.quickSort(array, 0, array.length - 1);

        printArray("Sorted Array", array, 30);

        System.out.print("Low: ");
        int[] lowValues = complex.getLowValues();
        for (int i = 0; i < Math.min(30, lowValues.length); i++) {
            System.out.print(lowValues[i] + " ");
        }
        System.out.println();

        System.out.print("High: ");
        int[] highValues = complex.getHighValues();
        for (int i = 0; i < Math.min(30, highValues.length); i++) {
            System.out.print(highValues[i] + " ");
        }
        System.out.println();

        System.out.print("Pivot: ");
        int[] pivotValues = complex.getPivotValues();
        for (int i = 0; i < Math.min(30, pivotValues.length); i++) {
            System.out.print(pivotValues[i] + " ");
        }
        System.out.println();

        System.out.println("Recursive calls: " + complex.getRecursiveCallCount());
        System.out.println("___________________________________________________");

    }

    @Test
    void radixSortTest() {
        System.out.println("radixSort - Test");
        System.out.println("Algorithm: radixSort");
        int[] array = generateRandomArray();

        printArray("Original Array", array, 20);

        complex.resetCounters();
        complex.radixSort(array, array.length);

        printArray("Sorted Array", array, 50);

        System.out.print("Counter Array: ");
        int[] counterArray = complex.getCounterArray();
        if (counterArray != null) {
            for (int i = 0; i < counterArray.length; i++) {
                System.out.print(counterArray[i] + (i < counterArray.length - 1 ? ", " : ""));
            }
        }
        System.out.println();
        System.out.println("___________________________________________________");

    }

    @Test
    void mergeSortTest() {
        System.out.println("mergeSort - Test");
        System.out.println("Algorithm: mergeSort");
        int[] array = generateRandomArray();
        int[] tmp = new int[array.length];

        printArray("Original Array", array, 20);

        complex.resetCounters();
        complex.mergeSort(array, tmp, 0, array.length - 1);

        printArray("Sorted Array", array, 100);

        System.out.print("Low: ");
        int[] lowValues = complex.getLowValues();
        for (int i = 0; i < Math.min(30, lowValues.length); i++) {
            System.out.print(lowValues[i] + " ");
        }
        System.out.println();

        System.out.print("High: ");
        int[] highValues = complex.getHighValues();
        for (int i = 0; i < Math.min(30, highValues.length); i++) {
            System.out.print(highValues[i] + " ");
        }
        System.out.println();

        System.out.println("Recursive calls: " + complex.getRecursiveCallCount());

        System.out.println("___________________________________________________");
    }


    @Test
    void shellSortTest() {
        System.out.println("shellSort - Test");
        System.out.println("Algorithm: shellSort");
        int[] array = generateRandomArray();

        printArray("Original Array", array, 20);

        complex.resetCounters();
        complex.shellSort(array);

        printArray("Sorted Array", array, 150);

        List<int[]> subArrays = complex.getShellSortSubArrays();
        int subArrayCount = 1;
        for (int[] subarray : subArrays) {
            if (subArrayCount <= 3) {
                System.out.print("Gap (n/2) subArray" + subArrayCount + ": ");
                for (int value : subarray) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
            subArrayCount++;
        }


        System.out.println("___________________________________________________");
    }
}