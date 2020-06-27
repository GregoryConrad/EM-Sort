package com.gsconrad.em_sort;

import java.util.*;

/**
 * Runs benchmarks for EM Sort and Collections.sort
 * Benchmarks are done on randomized arrays (which are not EM Sorts specialty)
 */
public class Main {
    /**
     * Number of elements in benchmark list
     */
    private static final int size = 10000;
    /**
     * Number of times we should run the sort
     */
    private static final int numPasses = 10;

    private static final Random rand = new Random();

    public static void main(String[] args) {
        runBenchmarks();
    }

    /**
     * Runs benchmarks of EM Sort and Collections.sort
     */
    public static void runBenchmarks() {
        // Create lists of the different benchmark times
        List<Long> emSortTimes = new ArrayList<>(numPasses),
                collectionSortALTimes = new ArrayList<>(numPasses),
                collectionSortLLTimes = new ArrayList<>(numPasses);

        // Run numPasses passes
        for (int i = 0; i < numPasses; ++i) {
            // Create the array & list for this pass
            final int[] arr = new int[size];
            LinkedList<Integer> list = new LinkedList<>();
            for (int n = 0; n < size; ++n) {
                final int curr = rand.nextInt();
                arr[n] = curr;
                list.addLast(curr);
            }

            // Run the sort for this pass and save results
            emSortTimes.add(runEMSort(list));
            collectionSortALTimes.add(runCollectionSortAL(arr));
            collectionSortLLTimes.add(runCollectionSortLL(arr));

            // Check to see whether the size of the sorted list is correct
            if (list.size() != size) {
                System.out.println("EM Sort created incorrect sized list");
            }

            // Check to see whether EM Sort actually worked
            if (!checkSorted(list)) {
                System.out.println("EM Sort did not sort correctly");
            }
        }

        // Calculate averages
        long emSortTotal = 0, collectionSortALTotal = 0, collectionSortLLTotal = 0;
        for (int i = 0; i < numPasses; ++i) {
            emSortTotal += emSortTimes.get(i);
            collectionSortALTotal += collectionSortALTimes.get(i);
            collectionSortLLTotal += collectionSortLLTimes.get(i);
        }

        // Print basic statistics
        System.out.println("EM Sort Average: " + (emSortTotal / numPasses));
        System.out.println("Collection Sort ArrayList Average: " + (collectionSortALTotal / numPasses));
        System.out.println("Collection Sort LinkedList Average: " + (collectionSortLLTotal / numPasses));
    }

    /**
     * Runs Collections.sort on a linked list
     *
     * @param arr the array of random numbers
     * @return time elapsed
     */
    public static long runCollectionSortLL(int[] arr) {
        return runCollectionSort(new LinkedList<>(), arr);
    }

    /**
     * Runs Collections.sort on an array list
     *
     * @param arr the array of random numbers
     * @return time elapsed
     */
    public static long runCollectionSortAL(int[] arr) {
        return runCollectionSort(new ArrayList<>(size), arr);
    }

    /**
     * Runs Collections.sort on a given list
     *
     * @param arr the array of random numbers
     * @return time elapsed
     */
    public static long runCollectionSort(List<Integer> list, int[] arr) {
        for (int i = 0; i < size; ++i) {
            list.add(arr[i]);
        }
        long start = System.nanoTime();
        Collections.sort(list);
        long end = System.nanoTime();
        return end - start;
    }

    /**
     * Runs EM Sort on a linked list
     *
     * @param list the list to sort
     * @return time elapsed
     */
    public static long runEMSort(LinkedList<Integer> list) {
        EMSort sort = new EMSort(list);
        long start = System.nanoTime();
        sort.sort();
        long end = System.nanoTime();
        return end - start;
    }

    /**
     * Checks whether the provided list is sorted correctly
     *
     * @param list the list to check
     * @return whether or not the list is sorted
     */
    public static boolean checkSorted(List<Integer> list) {
        if (list.isEmpty()) {
            return true;
        }
        final ListIterator<Integer> i = list.listIterator();
        for (int old = i.next(); i.hasNext(); ) {
            final int curr = i.next();
            if (curr < old) {
                return false;
            }
            old = curr;
        }
        return true;
    }
}
