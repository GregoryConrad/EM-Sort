# EM Sort
An efficient, easy to understand sorting algorithm using a stack and two subroutines: elimination and merge.
Designed for linked lists due to their constant time insert/remove (that this algorithm depends on).

The code in this repository still needs cleanup and optimization and serves primarily as a proof of concept.

# Background
I created EM Sort after reading a joke comment about a O(n) sorting algorithm called elimination sort, which just removes any out-of-place elements in a list.
That comment sparked some creativity, and I realized one can actually capitalize on that idea to make a relatively efficient sorting algorithm for linked lists, achieved through a series of eliminations followed by merges.

A full description of how this algorithm works will come soon.

# Features
- O(1) space complexity (if the algorithm uses custom nodes in a linked list)
  - Currently, there is overhead by having to create a stack of sorted fragment linked lists, but this storage overhead is largely negligible
- O(n) best case performance
  - This algorithm needs simply *one* pass through in the best case due to how it is designed
- Time taken grows with the number of out-of-place items
  - So, if a list is already close to sorted (perhaps a few elements out of place), this algorithm is extremely efficient
- O(n*log(n)) average performance
  - As far as I know; I would need to do a more in depth analysis to truly figure this out
- O(n^2) worse case performance (if every element out of place)
  - If a short optimization was added, this worst case scenario can be switched to O(n) yielding worst case as O(n*log(n))
    - Done by checking to see approximately how out of place a list is, and if it is a lot, then reverse the entire list
- Relatively easy to understand if you know about basic data structures like linked lists and stacks
- Many optimizations can still be made, which will increase performance further
  - The example on this repository is a proof of concept
