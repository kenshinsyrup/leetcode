package com.myleetcode.binary_search.find_kth_smallest_pair_distance;

import java.util.Arrays;

class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        return smallestDistancePairByBS(nums, k);
    }

    // similar: 378. Kth Smallest Element in a Sorted Matrix
    // But, if we solve this by PQ, will get TLE. Because PQ TC will be O(KlogK), and the worst case of K is N^2

    // solution: with BS
    // TC: O((M+N)*log(Max-Min) + NlogN) => O(Nlog(Max-Min) + NlogN), M is the j range, N is the i range, Max is the max distance and Min is the min distance.
    // https://leetcode.com/problems/find-k-th-smallest-pair-distance/discuss/109082/Approach-the-problem-using-the-%22trial-and-error%22-algorithm
    private int smallestDistancePairByBS(int[] nums, int k){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(k <= 0){
            return 0;
        }

        //sort first, Nlog(N)
        Arrays.sort(nums);

        int len = nums.length;
        int low = 0; // actually is nums[1]-nums[0], but we dont want to process the special case that nums.length<1 that makes nums[1] out of boundary.
        int high = nums[len - 1] - nums[0];

        while(low < high){
            int mid = low + ((high - low) >> 1);

            // find the first pair that larger than mid
            int count = 0;
            int j = 0;
            for(int i = 0; i < len; i++){
                // we are looking for the <= mid #, when j>i, every j and i could combine a pair, so there're j-i pairs
                while(j < len && Math.abs(nums[j] - nums[i]) <= mid){
                    if(j > i){
                        // count the pairs
                        count += (j - i);
                    }

                    j++;
                }
            }

            if(count < k){
                low = mid + 1;
            }else{
                high = mid;
            }
        }

        return low;
    }
}
