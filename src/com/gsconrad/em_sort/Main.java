package com.gsconrad.em_sort;

import java.util.*;

public class Main {
    private static final int size = 100000;
    private static final int numPasses = 10;
    private static final Random rand = new Random();

    public static void main(String[] args) {
        runBenchmarks();
    }

    public static void runBenchmarks() {
        // Create lists of the different benchmark times
        ArrayList<Long> emSortTimes = new ArrayList<>(numPasses),
                collectionSortALTimes = new ArrayList<>(numPasses), collectionSortLLTimes = new ArrayList<>(numPasses);

        // Run numPasses passes
        for (int i = 0; i < numPasses; ++i) {
            // Create the array & list for this pass
            int[] arr = new int[size];
            LinkedList<Integer> list = new LinkedList<>();
            for (int n = 0; n < size; ++n) {
                final int curr = rand.nextInt();
                arr[n] = curr;
                list.addLast(curr);
            }

            // Run the sort for this pass
            emSortTimes.add(runEMSort(list));
            collectionSortALTimes.add(runCollectionSortAL(arr));
            collectionSortLLTimes.add(runCollectionSortLL(arr));
        }

        // Calculate averages
        long emSortTotal = 0, collectionSortALTotal = 0, collectionSortLLTotal = 0;
        for (int i = 0; i < numPasses; ++i) {
            emSortTotal += emSortTimes.get(i);
            collectionSortALTotal += collectionSortALTimes.get(i);
            collectionSortLLTotal += collectionSortLLTimes.get(i);
        }

        System.out.println("EM Sort Average: " + (emSortTotal / numPasses));
        System.out.println("Collection Sort AL Average: " + (collectionSortALTotal / numPasses));
        System.out.println("Collection Sort LL Average: " + (collectionSortLLTotal / numPasses));
    }

    public static long runCollectionSortLL(int[] arr) {
        return runCollectionSort(new LinkedList<>(), arr);
    }

    public static long runCollectionSortAL(int[] arr) {
        return runCollectionSort(new ArrayList<>(size), arr);
    }

    public static long runCollectionSort(List<Integer> list, int[] arr) {
        for (int i = 0; i < size; ++i) {
            list.add(arr[i]);
        }
        long start = System.nanoTime();
        Collections.sort(list);
        long end = System.nanoTime();
        return end - start;
    }

    public static long runEMSort(LinkedList<Integer> list) {
        EMSort sort = new EMSort(list);
        long start = System.nanoTime();
        sort.sort();
        long end = System.nanoTime();
        return end - start;
    }
}
