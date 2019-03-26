package com.myleetcode.dynamic_program.longest_increasing_subsequence;

class Solution {
    public int lengthOfLIS(int[] nums) {
        return lenghtOfLISByDP(nums);
        // return lenghtOfLISByDPII(nums);
        // return lenghtOfLISByDPIII(nums);
    }

    // more compact
    // dp[i] keeps the LIS length of A[0:i]
    private int lenghtOfLISByDPIII(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] dp = new int[len];
        int max = Integer.MIN_VALUE;

        for(int i = 0; i < len; i++){
            dp[i] = 1; // base case, every solo char is a LIS.

            for(int j = 0; j < i; j++){
                // 注意，在这样的条件下，我们只给符合了要求的dp[i]赋值，所以最后的dp[n-1]不一定是最大值，比如A[]:[2,6,10,5], dp[4]是保存的:2,5这个序列长度为2，而dp[3]保存的才是最长序列:2,6,10，长度为3.
                if(nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            max = Math.max(max, dp[i]);
        }

        return max;

    }

    // 对比lenghtOfLISByDP，主要是看dp数组，状态方程，数组的index之间关系
    // intuition:
    // dp[i] or f(i), keeps the LIS length of A[0:i - 1]
    // 1 f(i) = max(f(j) + 1), if A[i - 1] > A[j - 1] for j[0:i-1], i[0:n]
    // 2 f(i) = 1, if A[i] <= A[j] for j[0:i-1] i[1:n], f(i) is 1 because every char itself A[i-1] is a LIS
    // 3 f(i) = 0, for i is 0, because no A[0:i-1] exists
    private int lenghtOfLISByDPII(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] dp = new int[len + 1]; // default is 0

        // base case, every solo char is a LIS.
        for(int i = 1; i <= len; i++){
            dp[i] = 1;
        }
        // dp[0] means LIS length of A[:0-1] which not exists, so dp[0] should be 0.Because dp default is 0, so dp[0] we dont need to set again

        // so, next we need to caculate dp[i] for i[2:n]
        for(int i = 1; i <= len; i++){
            for(int j = 1; j < i; j++){
                if(nums[i - 1] > nums[j - 1]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int max = Integer.MIN_VALUE;
        for(int v: dp){
            max = Math.max(max, v);
        }

        return max;

    }

    // intuition: CS5800.
    // 1 f(i) = max(f(j) + 1), if A[i] > A[j] for j [0:i-1], i[0:n-1]
    // 2 f(i) = 1, otherwise. ie, if A[i] <= A[j], f(i) is 1 because every char itself is a LIS
    // dp[i] keeps the LIS length of A[0:i]
    private int lenghtOfLISByDP(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] dp = new int[len];

        // base case, every solo char is a LIS.
        for(int i = 0; i < len; i++){
            dp[i] = 1;
        }

        for(int i = 0; i < len; i++){
            for(int j = 0; j < i; j++){
                // 注意，在这样的条件下，我们只给符合了要求的dp[i]赋值，所以最后的dp[n-1]不一定是最大值，比如A[]:[2,6,10,5], dp[4]是保存的:2,5这个序列长度为2，而dp[3]保存的才是最长序列:2,6,10，长度为3.
                if(nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int max = Integer.MIN_VALUE;
        for(int v: dp){
            max = Math.max(max, v);
        }

        return max;

    }
}
