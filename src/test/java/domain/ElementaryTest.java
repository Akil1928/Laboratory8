package domain;

import org.junit.jupiter.api.Test;

class ElementaryTest {

    @Test
    void elementaryTest() {

        int[] sharedArray = util.Utility.getIntegerArray(10000);
        int[] arrayForBubble = util.Utility.copyArray(sharedArray);
        int[] arrayForImproved = util.Utility.copyArray(sharedArray);


        int[] arraySelection = util.Utility.getIntegerArray(10000);
        int[] arrayCounting = util.Utility.getIntegerArray(10000);


        System.out.println(elementarySorting("bubbleSort", arrayForBubble, 50));
        System.out.println("___________________________________________________");


        System.out.println(elementarySorting("improvedBubbleSort", arrayForImproved, 100));
        System.out.println("___________________________________________________");


        System.out.println(elementarySorting("selectionSort", arraySelection, 150));
        System.out.println("___________________________________________________");


        System.out.println(elementarySorting("countingSort", arrayCounting, 200));
    }

    private String elementarySorting(String algorithm, int[] a, int elementsToShow) {
        int[] originalArray = util.Utility.copyArray(a);
        switch (algorithm) {
            case "bubbleSort":
                Elementary.bubbleSort(a);
                break;
            case "improvedBubbleSort":
                Elementary.improvedBubbleSort(a);
                break;
            case "selectionSort":
                Elementary.selectionSort(a);
                break;
            case "countingSort":
                Elementary.countingSort(a, a.length);
                break;
            default:
                return "Not valid algorithm. Try again!!";
        }

        StringBuilder result = new StringBuilder();
        result.append(algorithm).append(" - Test")
                .append("\nAlgorithm: ").append(algorithm)
                .append("\nOriginal Array: ").append(arrayToString(originalArray, elementsToShow))
                .append("\nSorted Array: ").append(arrayToString(a, elementsToShow))
                .append("\nTotal Iterations: ").append(Elementary.getTotalIterations())
                .append("\nTotal Changes: ").append(Elementary.getTotalChanges());

        if (algorithm.equals("countingSort")) {
            result.append("\nCounter array: ").append(Elementary.getCounterArray());
        }

        return result.toString();
    }

    private String arrayToString(int[] array, int elementsToShow) {
        if (array == null || array.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(elementsToShow, array.length); i++) {
            sb.append(array[i]);
            if (i < Math.min(elementsToShow - 1, array.length - 1)) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}