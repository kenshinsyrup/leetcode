package com.myleetcode.priority_queue.maximize_sum_of_array_after_k_negations;

import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int largestSumAfterKNegations(int[] A, int K) {
        return largestSumAfterNegationsByPQ(A, K);
    }

    //https://leetcode.com/problems/maximize-sum-of-array-after-k-negations/discuss/252228/A-very-simple-java-solution
    // optimation: Now we should think whether there is a data structure which allows to find the minimum in time better than O(n). Such data structure exists and it's called Min-Heap. It allows querying the minimum value in constant time (insertion takes O(log(n)). So you can populate the PriorityQueue with all the elements in the array and then remove the minimum item, invert it and insert back into PriorityQueue K times.
    // about the new time complexity, in general case, it takes O(n) time to build a heap, so the TC should be O(n + k * log(n))
    // in Java, in this implementation, we have no ourself's heapify() function, but we could think it takes O(n) time, because we know add method in pq takes O(logn), but when we build the pq, pq's siza is from 0-n, so it dont take time O(nlogn), so we could think this takes O(n)(因为我不懂Java的内部实现，不知道内部是否是heapify的方式来实现的，但我们在插入完n个数字之前，pq的sizq确实是从0增加到n的，所以整个A插入到pq的操作肯定小于O(nlogn)，所以就按照general caase认为是O(n))
    // TC: O(n + klogn).
    // SC: O(n)
    private int largestSumAfterNegationsByPQ(int[] A, int K){
        // special case
        if(A == null){
            return 0;
        }

        // build pq as heap, this cost not O(n) but O(nlogn)
        PriorityQueue<Integer> pq = new PriorityQueue<>(); // integer pq is ascending by default
        for(int v: A){
            pq.add(v);
        }

        // O(K*logn)
        for(int i = 0; i < K; i++){
            int min = pq.poll(); // O(1)
            pq.add(-min); // O(logn)
        }

        // sum
        int sum = 0;
        while(!pq.isEmpty()){
            sum += pq.poll();
        }

        return sum;
    }


    // optimation: in K loop, we dont need sort(O(nlogn)) to find the minimum, we could just find the minimum in one loop (O(n)). Because we only care about the minimum.
    // TC: O(K*n + n) => O(K*n)
    // SC: O(1)
    private int largestSumAfterNegationsByFindMinimum(int[] A, int K){
        // special case
        if(A == null){
            return 0;
        }

        // every time choose the minimum
        int idx = 0;
        for(int i = 0; i < K; i++){
            // find the minimum in one loop
            for(int j = 0; j < A.length - 1; j++){
                if(A[j] > A[j + 1]){
                    idx = A[j + 1];
                }
            }

            // -
            A[idx] = -A[idx];
        }

        int sum = 0;
        for(int i = 0; i < A.length; i++){
            sum += A[i];
        }

        return sum;
    }

    // contest code
    // intuition: K loop, every time sort to find the minimum to negative it. then sum all.
    // TC: O(K*nlogn + n) => O(K*nlogn)
    // SC: O(1)
    private int largestSumAfterNegationsByContest(int[] A, int K){
        // special case
        if(A == null){
            return 0;
        }

        // every time choose the minimum?
        for(int i = 0; i < K; i++){
            // sort
            Arrays.sort(A);

            // -
            A[0] = -A[0];
        }

        int sum = 0;
        for(int i = 0; i < A.length; i++){
            sum += A[i];
        }

        return sum;
    }
}