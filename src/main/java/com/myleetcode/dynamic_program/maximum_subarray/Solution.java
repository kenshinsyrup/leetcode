package com.myleetcode.dynamic_program.maximum_subarray;

class Solution {
    public int maxSubArray(int[] nums) {
// Kadane's algorithm https://en.wikipedia.org/wiki/Maximum_subarray_problem

//         dp问题和divide-conquer有点类似，区别在于dp问题的子问题之间是又关系的，每个子问题都会对下一个问题做贡献，即子问题重叠；而divide-conquer中divide之后每个问题都是独立的

//         分析这个问题：要获得的是和最大的子数组。比如长度为len的数组A，这个问题最好不要想太多，一个典型的想太多就是想求得A的所有的子数组然后比较他们的和。这个是很原始的做法。主要的原因是认为subarray的起点可以从任何一个地方开始而且必须是连续的。
//         但是，对于这种求和最大的问题，有一个很重要的特性，在https://en.wikipedia.org/wiki/Maximum_subarray_problem中有提到。首先，如果A中全是正数，那么最大的和肯定是整个A。如果全是负数，那么最大的肯定是绝对值最小的那个元素自身组成的子数组。也就是说，对于元素i来说，0-i的内容对我有贡献那么我应该直接加入他们，如果对我没有贡献我应该新开始一个以我为开始的新subarray。这样，对于i+1来说，不论那种情况，这个最新的dp[i]和i+1一定是连续的。

//       return maxSubArrayDP(nums);

        return maxSubArrayByDPII(nums);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: DP. 53, 121
    private int maxSubArrayByDPII(int[] nums){
        // special case
        if(nums == null || nums.length == 0){
            return 0;
        }

        // dp[i] means the max at ith pos in nums
        // dp[i] = max(dp[i - 1] + nums[i], nums[i]);
        // base case is the 0th pos, dp[0] must be nums[0], so no need with extra row for base
        // and we want the max of all, so we dont directly use the dp[len-1] as answer, we find the max througth the whole dp
        int[] dp = new int[nums.length];

        // init
        dp[0] = nums[0];

        // need a max variable to record the maximum of dp[i]
        int max = dp[0];
        for(int i = 1; i < nums.length; i++){
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);

            max = Math.max(dp[i], max);
        }

        return max;

    }

    private int maxSubArrayDP(int[] nums){
          // special case
        if(nums == null){
            return 0;
        }

        int len = nums.length;

        if(len == 1){
            return nums[0];
        }

        int[] dp = new int[len];
        // 动态规划比较重要的内容就是要记得初始化好最原始的状态
        dp[0] = nums[0];
        int max = nums[0];

        for(int i = 1; i < len; i++){
            // 如果i之前的内容不能对i做贡献（即不大于0），那对于i来说还不如重新开一个subarray
            if(dp[i - 1] > 0){
                dp[i] = dp[i - 1] + nums[i];
            }else{
                dp[i] = nums[i];
            }

//             保持max
            max = Math.max(dp[i], max);
        }

        return max;
    }
}