package com.myleetcode.breadth_first_search.nested_list_weight_sum_ii;

import com.myleetcode.utils.nested_integer.NestedInteger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        return depthSumInverseByBFSAndQueue(nestedList);// easier to understand and think
        // return depthSumInverseByBFSOnePass(nestedList);
    }

    // TC: O(H)
    // SC: O(N)
    // one-pass solution: https://leetcode.com/problems/nested-list-weight-sum-ii/discuss/83655/JAVA-AC-BFS-solution/88048
    // the thought is: we actually dont need use a Queue to store the levelSum and at last to process them with the decreasing depth.
    // We Could Just Add All Previous Sum To The Sum With The New LevelSum, this way, we actually do the same thing because depth is one by one increases, every depth, we add all previous sum one more time to the sum to act as sum inverse.
    // BUT this is tricky because the weight of level is the depth and the depth is increase one by one
    private int depthSumInverseByBFSOnePass(List<NestedInteger> nestedList){
        int prevSum = 0;
        int totalSum = 0;

        Deque<NestedInteger> queue = new ArrayDeque();
        for (NestedInteger ni : nestedList) {
            queue.offerLast(ni);
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            int levelSum = 0;

            for (int i = 0; i < size; i++) {
                NestedInteger current = queue.pollFirst();

                if (current.isInteger()) {
                    levelSum += current.getInteger();
                } else {
                    for (NestedInteger ni: current.getList()) {
                        queue.offerLast(ni);
                    }
                }
            }

            prevSum += levelSum;
            totalSum += prevSum;
        }

        return totalSum;
    }

    // TC: O(H), H is the depth
    // SC: O(N), N is the # of NI
    // intuition: the basic thought is the same with the 339. Nested List Weight Sum. An adaption is we could use a Queue to store the levelSum, then we traverse the Queue with decreasing the final depth variable one by one
    private int depthSumInverseByBFSAndQueue(List<NestedInteger> nestedList){
        if(nestedList == null || nestedList.size() == 0){
            return 0;
        }

        Deque<NestedInteger> niQueue = new ArrayDeque<>();
        Deque<Integer> levelSumQueue = new ArrayDeque<>();

        int depth = 1;
        int levelSum = 0;
        // !!! at first, I thought: the very first level, because the input is a List<NI>, so we have to break it up and process it: if Integer, sum it; if NI, offer to Queue. BUT NO, we dont do that, because Integer and NI are both NI, so we just offer to Queue and process them in the Queue
        for(NestedInteger ni: nestedList){
            niQueue.offer(ni);
        }

        // we already have the initial Queue, level by level scan
        while(!niQueue.isEmpty()){
            int size = niQueue.size();
            levelSum = 0;

            // scan cur level Integer and NestedInteger(not Integer)
            for(int i = 0; i < size; i++){
                NestedInteger curNI = niQueue.poll();
                if(curNI.isInteger()){
                    levelSum += curNI.getInteger();
                }else{
                    // push NI in List to queue
                    for(NestedInteger nextNI: curNI.getList()){
                        niQueue.offer(nextNI);
                    }
                }
            }

            // push levelSum to Queue
            levelSumQueue.offer(levelSum);

            // depth
            depth++;
        }

        // !!! the variable depth at last is one more deeper than actual depth, so reduce one
        depth--;

        // caculate sum
        int sum = 0;
        while(!levelSumQueue.isEmpty()){
            sum += levelSumQueue.poll() * depth;

            // decrease depth
            depth--;
        }

        return sum;
    }

}

