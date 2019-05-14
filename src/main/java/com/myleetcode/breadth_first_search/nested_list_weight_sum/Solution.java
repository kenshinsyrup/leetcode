package com.myleetcode.breadth_first_search.nested_list_weight_sum;

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
    public int depthSum(List<NestedInteger> nestedList) {
        // return depthSumByQueue(nestedList); // BFS
        return depthSumByDFS(nestedList); // DFS
    }

    // this problem is actually a tree traverse preorder

    // TC: O(H), H is the depth of input
    // SC: O(N), N is the num of NI in input
    // intuition:
    // this looks like a Parentheses problem, maybe we could use Stack (or DFS recursion) to solve it. we use a stack to store the [, every time we meet one, we push it into the Stack and make a variable depth plus 1, and every time we meet a num we make a sum += num*depth, and everytiem we meet a ], we pop out a [ and make the depth minus 1
    // BUT the input is not a String, so we could not use the [ and ] easily. since this input is a List<NestedInteger> and we have the necessary interface of it, we could use BFS to solve it.
    private int depthSumByQueue(List<NestedInteger> nestedList){
        if(nestedList == null || nestedList.size() == 0){
            return 0;
        }

        int sum = 0;
        Deque<NestedInteger> niQueue = new ArrayDeque<>();

        // !!! at first, I thought: the very first level, because the input is a List<NI>, so we have to break it up and process it: if Integer, sum it; if NI, offer to Queue. BUT NO, we dont do that, because Integer and NI are both NI, so we just offer to Queue and process them in the Queue
        int depth = 1;
        for(NestedInteger ni: nestedList){
            niQueue.offer(ni);
        }

        // we already have the initial Queue, level by level scan
        while(!niQueue.isEmpty()){
            int size = niQueue.size();

            // scan cur level Integer and NestedInteger(not Integer)
            for(int i = 0; i < size; i++){
                NestedInteger curNI = niQueue.poll();
                if(curNI.isInteger()){
                    sum += curNI.getInteger() * depth;
                }else{
                    // push NI in List to queue
                    for(NestedInteger nextNI: curNI.getList()){
                        niQueue.offer(nextNI);
                    }
                }
            }

            // depth
            depth++;
        }

        return sum;
    }

    // DFS solution
    /*
The algorithm takes O(N) time, where NN is the total number of nested elements in the input list. For example, the list [ [[[[1]]]], 2 ] contains 4 nested lists and 2 nested integers (1 and 2), so N=6.

In terms of space, at most O(D) recursive calls are placed on the stack, where DD is the maximum level of nesting in the input. For example, D=2 for the input [[1,1],2,[1,1]], and D=3 for the input [1,[4,[6]]].
    */
    private int depthSumByDFS(List<NestedInteger> nestedList){
        return dfs(nestedList, 1);
    }

    private int dfs(List<NestedInteger> nestedList, int depth){
        int sum = 0;
        for (NestedInteger ni: nestedList) {
            if (ni.isInteger()) {
                sum += ni.getInteger() * depth;
            } else {
                sum += dfs(ni.getList(), depth + 1);
            }
        }
        return sum;
    }
}

