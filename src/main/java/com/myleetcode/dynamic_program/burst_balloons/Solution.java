package com.myleetcode.dynamic_program.burst_balloons;

class Solution {
    public int maxCoins(int[] nums) {
        return maxCoinsByDP(nums);
    }

    // there's a most reasonable solution here: https://leetcode.com/problems/burst-balloons/discuss/76228/Share-some-analysis-and-explanations/186461
    // explaination: https://leetcode.com/problems/burst-balloons/discuss/76229/For-anyone-that-is-still-confused-after-reading-all-kinds-of-explanations...
    // TC: O(N^3)
    // SC: O(N^2)
    private int maxCoinsByDP(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] newNums = new int[len + 2];
        for(int i = 0; i < len; i++){
            newNums[i + 1] = nums[i];
        }
        newNums[0] = 1;
        newNums[len + 1] = 1;

        int[][] dp = new int[len + 2][len + 2];
        // dp[i][j] represents the maximum coins we can get to burst the balloons from index i to j, nums[i : j](i <= j)
        // induction rule: dp[i][j] = max(nums[k]*nums[i - 1]* nums[j + 1] + dp[i][k - 1] + dp[k + 1][j]) for k from i to j inclusive
        // according the positions of dp[i][k - 1] and dp[k + 1][j], we update the matrix value from left to right, from bottom to top
        for(int j = 1; j <= len; j++){ // j is right
            for(int i = j; i >= 1; i--){ // i is left

                for(int k = i; k <= j; k++){ // k is try to burst balloon
                    dp[i][j] = Math.max(dp[i][j], newNums[i - 1] * newNums[k] * newNums[j + 1] + dp[i][k - 1] + dp[k + 1][j]);
                }
            }
        }

        return dp[1][len];
    }
}
