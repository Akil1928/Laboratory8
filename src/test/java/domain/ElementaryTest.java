package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementaryTest {

    @Test
    void elementaryTest() {
        int[] a = util.Utility.getIntegerArray(10000);
        int[] b = util.Utility.copyArray(a);
        System.out.println(elementarySorting("bubbleSort", a, 50));
        System.out.println(elementarySorting("improvedBubbleSort", b, 100));
        System.out.println(
                elementarySorting("selectionSort", util.Utility.getIntegerArray(10000), 150));
        System.out.println(
                elementarySorting("countingSort", util.Utility.getIntegerArray(10000), 200));
    }
    private String elementarySorting(String algorithm, int[] a, int n){
       int[] originalArray = util.Utility.copyArray(a);
        switch (algorithm){
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
                Elementary.countingSort(a);
                break;
            default: return "Not valid algoritm. Try again!!";

        }
        String result="";
        result+="\n"+algorithm+" -Test"+"\nAlgorithm: "+ algorithm+"\nOriginal Array: "+util.Utility.show(a)+"\nSord Array: "+util.Utility.show(a);
        return  result;
    }
}