package com.myleetcode.dynamic_program.split_array_largest_sum;

class Solution {
    public int splitArray(int[] nums, int m) {
        return splitArrayByTranslate(nums, m);
    }

    // intuition: DP
    // solution section
    // dp[i][j] means sub-problem that given first i elems and j num subarrays
    /*
    if given i elems, if we seperate at k where k is in [0:i-1], then we know the largest sum of subarrays is max(dp[k][j-1], nums[k]+...+nums[i-1])
    so dp[i][j] = min{
        max(dp[k][j-1], nums[k]+...+nums[i-1]), k in [0:i-1]
    }
    */
    private int splitArrayByTranslate(int[] nums, int m){
        if(nums == null || nums.length == 0 || m <= 0){
            return 0;
        }

        int len = nums.length;

        // presum array for help, to get nums[k]+...+nums[i-1] in O(1), otherwise cost O(N)
        int[] presums = new int[len + 1];
        for(int i = 1; i < presums.length; i++){
            presums[i] = presums[i - 1] + nums[i - 1];
        }

        // dp array
        int[][] dp = new int[len + 1][m + 1];

        // init to MAX
        for(int i = 0; i <= len; i++){
            for(int j = 0; j <= m; j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        // for i: if given 0 elems, min max sum is 0
        for(int j = 0; j <= m; j++){
            dp[0][j] = 0;
        }
        // for j, given 0 num subarrays make no sense, so base is j == 1
        for(int i = 0; i <= len; i++){
            if(i == 0){
                dp[i][1] = 0;
            }else{
                dp[i][1] = presums[i] - presums[0];
            }
        }

        // dp
        for(int i = 1; i <= len; i++){ // base is 0, from 1
            for(int j = 2; j <= m; j++){ // base is 1, from 2
                for(int k = 1; k < i; k++){ // k in [1:i-1], nums[k]+...+nums[i-1] is the last subarray of the j subarrays for given i elems.
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j - 1], presums[i] - presums[k]));
                }
            }
        }

        return dp[len][m];

    }
}
