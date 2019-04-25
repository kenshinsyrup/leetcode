package com.myleetcode.dynamic_program.house_robber_ii;

import java.util.Arrays;

class Solution {
    public int rob(int[] nums) {
        // return robByDP(nums);//wrong
        return robByDPII(nums);
    }

    // TC: O(N)
    // SC: O(N)
    // but robByDP is not correct, at least not totally correct. because in the circle, we have first and last elem the same, why we remove last not first, or why remove first not last? because we all know the Sequence Of Nums Is Important in the nums. for example [1, 2, 1, 1], if we remove the last, then [1,2,1],then the max is 2; but if we remove the first, then [2,1,1], then the max is 3.
    // so we correct this, by choose the max of: remove first or last.
    private int robByDPII(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){
            return nums[0];
        }
        if(nums.length == 2){
            return Math.max(nums[0], nums[1]);
        }

        int[] withFirstHouse = Arrays.copyOfRange(nums, 0, nums.length-1);
        int[] withLastHouse = Arrays.copyOfRange(nums, 1, nums.length);

        // here we use the 198 code
        return Math.max(robByDP(withFirstHouse), robByDP(withLastHouse));
    }

    // TC: O(N), N is the length of nums
    // SC: O(N)
    // intuition: DP. 198, 213, 309, 740, 790, 801
    // first though is, could we just remove the last elem from the nums, then it's the 198
    // but keep in mind, if nums.length == 1, we return nums[0], if we build dp, the dp[1] should be overflow because the len==1, dp[] lenght is 1.
    // then keep in mind, if there are onely two houses, then first and last already adjacent, so we dont need to remove the last one, actually, we could return the larger one of these two.
    private int robByDP(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){
            return nums[0];
        }
        if(nums.length == 2){
            return Math.max(nums[0], nums[1]);
        }

        int len = nums.length;
        int[] dp = new int[len + 1];
        dp[1] = nums[0];

        for(int i = 2; i <= len; i++){
            dp[i] = Math.max(dp[i - 1], dp[i - 1 - 1] + nums[i - 1]);
        }

        return dp[len];
    }
}
