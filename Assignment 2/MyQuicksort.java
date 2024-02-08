/**
 * class that implements quicksort for arrays of ints
 * Christopher Flippen
 * CMSC 401 - Professor Fung
 */
public class MyQuicksort {
    /** global int used to keep track of comparisons */
    public static int comparisons = 0;

    /**
     * The main method takes an array of ints from the command line and stores them into an array
     * The array is then sorted with quicksort and the result is printed
     * The number of comparisons used in the quicksort is also printed
     * If the input array contains values that are not ints, the program returns and prints error
     * @param args is the array of ints input through the command line
     */
    public static void main(String[] args) {
        int[] sort = new int[args.length];
        for (int i = 0; i<args.length; i++) {
            try {
                sort[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException E) {
                System.out.println("error");
                return;
            }
        }
        quicksort(sort);
    }

    /**
     * Method that calls the recursive quicksort method and prints the
     * result along with the number of comparisons
     * @param sort is the array of ints to be sorted
     */
    public static void quicksort(int[] sort) {
        quicksortRecursive(sort, 0, sort.length-1);
        StringBuilder output= new StringBuilder();
        for (int value : sort) {
            output.append(value).append(" ");
        }
        System.out.println(output);
        System.out.println(comparisons);
    }

    /**
     * method to recursively sort the int[] array sort using quicksort
     * @param sort is the array
     * @param start is the start of the array for this recursive call
     * @param end is the end of the array for this recursive call
     */
    public static void quicksortRecursive(int[] sort, int start, int end) {
        if (start < end) {
            int partitionIndex = partition(sort, start, end);
            //quicksort the left half of the array
            quicksortRecursive(sort, start, partitionIndex-1);
            //quicksort the right half of the array
            quicksortRecursive(sort, partitionIndex+1, end);
        }
    }

    /**
     * method to rearrange a subarray in place for use in quicksort
     * this method was written using pseudocode from the textbook and slides
     * @param sort - the array to partition
     * @param start - the start of the array in this partition
     * @param end - the end of the array in this partition
     * @return the return value is the new index of the pivot, the pivot is initially at the end of the array
     */
    public static int partition(int[] sort, int start, int end) {
        //divide array into 4 parts:
        // Part 1: <=pivot
        // Part 2: =pivot
        // Part 3: >pivot
        // Part 4: unsorted
        int pivot = sort[end];
        //i starts at start-1 because it begins as an empty part of the array
        int i = start - 1;
        for (int j = start; j<end; j++) {
            //compare element j with the pivot and place it in the appropriate part of the array
            //if sort[j] <= pivot, it should be in part 1, if sort[j] > pivot, it remains in part 3
            comparisons++;
            if (sort[j] <= pivot) {
                i++;
                //swap sort[i] and sort[j]
                int temp = sort[i];
                sort[i] = sort[j];
                sort[j] = temp;
            }
        }
        //swap the middle element and the pivot to end up with the array as:
        //[Part 1 | Pivot | Part 3 | Empty Part 4]
        //We stored sort[end] as pivot, so we don't need a temp variable here
        sort[end] = sort[i+1];
        sort[i+1] = pivot;
        return i+1;
    }

}
