package com.myleetcode.dynamic_program.partition_equal_subset_sum;

class Solution {
    public boolean canPartition(int[] nums) {
        return canPartitionByDP(nums); // more intuitive
        // return canPartitionByDPII(nums);// space optimization
    }

    // TC: O(N * M), N is the length of nums, M is the target
    // SC: O(M)
    // intuition: DP. 322, 377, 416, 494
    // https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation
    // essentially a 0-1 knapsack problem, we should find if there are a set of num that sum up to sum(nums)/2. because if the sum of nums is Sum and it's even, then if we find a set of num in nums sum up to Sum/2, then the rest part must sum up to Sum/2 too
    // we have 2 constraints, the nums and the sum, so we need a 2-D array
    // dp[i][j] means given i num and sum j, if we could choose a set of the i num to sum them and make the sum is j.
    // 1 if we dont choose the nums[i-1], dp[i][j] = dp[i-1][j], means the nums[i-1] is used by another part
    // 2 if we choose the nums[i-1], dp[i][j] = dp[i-1][j-nums[i]], means the nums[i - 1] is used by the j part so j need to reduce the nums[i].
    // our dp[i][j] choose the 1||2 finally

    // base is given 0 num then dp[0][j] = false; given 0 as sum then dp[i][0] = true; dp[0][0] = true.
    // !!!the dp[i][0] just means we dont pick any num in i num so we could form a sum 0, so it's true, this is useful to keep our fomula consistent. because although we know if we have i num, if we dont choose any num, then our part sum is 0, fit the j==0, but the another part is not, right? but we should know, there's no possible if we have i num but the sum is 0, so if we are given i num and sum j, if we could get a set of i num that sum up to j, then true, even if this set is empty.
    private boolean canPartitionByDP(int[] nums){
        if(nums == null || nums.length == 0){
            return false;
        }

        int len = nums.length;
        int sum = 0;
        for(int i = 0; i < len; i++){
            sum += nums[i];
        }
        if(sum %2 != 0){
            return false;
        }
        sum /= 2;

        boolean[][] dp = new boolean[len + 1][sum + 1];
        // base
        dp[0][0] = true;
        for(int i = 1; i < len; i++){
            dp[i][0] = true;
        }

        for(int i = 1; i <= len; i++){
            for(int j = 1; j <= sum; j++){
                dp[i][j] = dp[i][j] || dp[i - 1][j];

                if(j >= nums[i - 1]){
                    dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[len][sum];

    }

    // TC: O(N * M)
    // SC: O(M)
    // and, same as 0-1 knapsack problem we could do space optimization on this, because dp[i][j] only depends on the row above it
    private boolean canPartitionByDPII(int[] nums){
        if(nums == null || nums.length == 0){
            return false;
        }

        int len = nums.length;
        int sum = 0;
        for(int i = 0; i < len; i++){
            sum += nums[i];
        }
        if(sum %2 != 0){
            return false;
        }
        sum /= 2;

        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;

        // here must put the i=sum loop in the inner loop, just like the original dp solution, we are given i num and we try to get the sum j.
        for(int i = 0; i < len; i++){
            for(int j = sum; j > 0; j--){
                if(j >= nums[i]){
                    dp[j] = dp[j] || dp[j - nums[i]];
                }
            }
        }

        return dp[sum];

    }
}
