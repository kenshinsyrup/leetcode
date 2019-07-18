package com.myleetcode.sliding_window.max_consecutive_ones;

class Solution {
    public int longestOnes(int[] A, int K) {
        return longestOnesBySlidingWindwo(A, K);
    }

    /*
    出错点:
    1 无论count小于还是等于k都应该获取长度并更新maxLen，而不是只在等于的时候更新
    */
    // intuition: Sliding Window
    // expand and count 0 we met, if total # of 0 we met greater than K, shrink. when # of 0 not greater than K, get len and try to update maxLen
    // TC: O(N)
    // SC: O(1)
    private int longestOnesBySlidingWindwo(int[] nums, int step){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int maxLen = 0;
        int leftP = 0;
        int rightP = 0;
        int count = 0;
        while(rightP < len){
            // expand
            if(nums[rightP] == 0){
                count++;
            }

            // shrink when count > step
            while(count > step){
                if(nums[leftP] == 0){
                    count--;
                }

                leftP++;
            }

            // if count <= step, cur rang is valid, get its len and update maxLen
            maxLen = Math.max(maxLen, rightP - leftP + 1);

            rightP++;
        }

        return maxLen;
    }
}
