package domain;
public class Elementary {

    private static int totalIterations;
    private static int totalChanges;
    private static int[] counterArray;

    public static int getTotalIterations() {
        return totalIterations;
    }

    public static int getTotalChanges() {
        return totalChanges;
    }

    public static String getCounterArray() {
        if (counterArray == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int value : counterArray) {
            sb.append(value).append(", ");
        }
        return sb.toString().trim();
    }

    public static void bubbleSort(int a[]) {
        if (a == null || a.length == 0) return;

        totalIterations = 0;
        totalChanges = 0;
        for(int i=0; i<a.length-1; i++) {
            for(int j=0; j<a.length-1-i; j++) {
                totalIterations++;
                if(a[j]>a[j+1]) {
                    int aux=a[j];
                    a[j]=a[j+1];
                    a[j+1]=aux;
                    totalChanges++;
                }
            }
        }
    }

    public static void improvedBubbleSort(int a[]) {
        if (a == null || a.length == 0) return;

        totalIterations = 0;
        totalChanges = 0;
        boolean swapped = true;
        for(int i=0; swapped && i<a.length-1; i++) {
            swapped = false;
            for(int j=0; j<a.length-1-i; j++) {
                totalIterations++;
                if(a[j]>a[j+1]) {
                    int aux=a[j];
                    a[j]=a[j+1];
                    a[j+1]=aux;
                    swapped = true;
                    totalChanges++;
                }
            }
        }
    }

    public static void selectionSort(int a[]) {
        if (a == null || a.length == 0) return;

        totalIterations = 0;
        totalChanges = 0;
        for(int i=0; i<a.length-1; i++) {
            int min=a[i];
            int minIndex=i;
            for(int j=i+1; j<a.length; j++) {
                totalIterations++;
                if(a[j]<min) {
                    min=a[j];
                    minIndex=j;
                }
            }
            if (minIndex != i) {
                a[minIndex]=a[i];
                a[i]=min;
                totalChanges++;
            }
        }
    }

    public static void countingSort(int[] a, int n) {
        if (a == null || a.length == 0) return;

        totalIterations = 0;
        totalChanges = 0;

        int max = util.Utility.maxArray(a);
        int[] tempCounter = new int[max + 1];

        for (int element : a) {
            tempCounter[element]++;
            totalIterations++;
        }

        counterArray = new int[max + 1];
        System.arraycopy(tempCounter, 0, counterArray, 0, tempCounter.length);

        int index = 0;
        for (int i = 0; i < tempCounter.length; i++) {
            while (tempCounter[i] > 0) {
                if (a[index] != i) {
                    a[index] = i;
                    totalChanges++;
                }
                index++;
                tempCounter[i]--;
                totalIterations++;
            }
        }
    }
}