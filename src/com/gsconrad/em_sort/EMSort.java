package com.gsconrad.em_sort;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Wrapper class for the EM Sort proof of concept
 */
public class EMSort {
    /**
     * The provided list that needs to be sorted
     */
    private final LinkedList<Integer> list;
    /**
     * The stack of fragment linked lists that will be merged together
     */
    private final Stack<LinkedList<Integer>> tiers = new Stack<>();

    /**
     * Creates an instance of the EM Sort class with the given list to sort
     * @param list the list to sort
     */
    public EMSort(LinkedList<Integer> list) {
        this.list = list;
    }

    /**
     * Performs the elimination subroutine
     */
    private void performElimination() {
        // Add the list to the stack so we can start work on it
        tiers.push(list);
        while (!tiers.peek().isEmpty()) {
            // First, we need to create a new tier
            final LinkedList<Integer> newTier = new LinkedList<>();
            // Next, we need to populate this new tier by iterating through the current last tier
            //  and finding elements to eliminate
            final ListIterator<Integer> iterator = tiers.peek().listIterator();
            // We create a previous high to keep track of the highest previous element
            //  (that subsequent elements need to be higher than)
            Integer previousHigh = iterator.next();
            // This is where the meat of the elimination takes place. This is where we eliminate and add to next tier
            while (iterator.hasNext()) {
                final Integer current = iterator.next();
                // If the current element isn't high enough, we need to perform the elimination
                if (current < previousHigh) {
                    iterator.remove();
                    newTier.add(current);
                } else {
                    // The current is higher, so reassign
                    previousHigh = current;
                }
            }
            // At this point, the new tier has been created completely from the out of order elements of the prev. tier
            //  Thus, it is time to add the new tier to the tiers
            tiers.push(newTier);
        }
    }

    /**
     * Performs the merge subroutine
     */
    private void performMerge() {
        // Now, we need to perform the merge on the last tier with the tier before the last tier
        while (tiers.size() > 1) {
            // First, we need to get the elements from the tier we need to remove
            final LinkedList<Integer> mergeFrom = tiers.pop();
            // Next, we need to get the elements from the tier we want to merge into
            final ListIterator<Integer> mergeIntoIterator = tiers.peek().listIterator();
            // As we need to empty the merge from iterator, we need to keep working while it is not empty
            while (!mergeFrom.isEmpty()) {
                if (mergeIntoIterator.hasNext()) {
                    final Integer current = mergeIntoIterator.next();
                    if (mergeFrom.getFirst() < current) {
                        // Set the last element in the merge into to the new element
                        mergeIntoIterator.set(mergeFrom.removeFirst());
                        // Add the current back
                        mergeIntoIterator.add(current);
                        // Move the iterator back one in case the next element of mergeFrom is still less than
                        //  the current of mergeInto
                        mergeIntoIterator.previous();
                    }
                } else {
                    // If there is nothing to compare, we need to add our remaining elements to merge into
                    //  This is because everything remaining in mergeFrom would have to be greater than mergeInto
                    //    to reach this point in the first place
                    tiers.peek().addAll(mergeFrom);
                    mergeFrom.clear();
                }
            }
        }
        // Remove the list itself from tiers as it has been sorted
        tiers.pop();
    }

    /**
     * The sort method of elimination-merge (EM) sort that performs both subroutines
     * Tiers needs to be empty at every run of sort, but it will be as it will be emptied in every run
     */
    public void sort() {
        // First, we need to perform the "elimination" step and create the tiers
        performElimination();
        // Finally, we need to merge the created tiers back together
        performMerge();
    }
}
