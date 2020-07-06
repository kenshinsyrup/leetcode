package com.myleetcode.dynamic_program.combination_sum_iv;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int combinationSum4(int[] nums, int target) {
        // return combinationSum4ByBacktracking(nums, target);//WRONG: TLE
        return combinationSum4ByDP(nums, target);
    }

    // intuition: DP. 322, 377, 416, 494
    // https://leetcode.com/problems/combination-sum-iv/discuss/85106/A-summary-of-all-combination-sum-problem-in-LC-C%2B%2B
    // https://leetcode.com/problems/combination-sum-iv/discuss/156045/What-if-each-number-can-be-used-exactly-once
    /*
    Knapsack Problem with Unlimited Items. Coin Change Problem.

    Thought:
        There's only one constraint in this kind of problem, which is the Capacity. Here is the Target.
        dp[i] means the num of combinations of nums that could sum up to target i.

    Function:
        dp[i] = sum(dp[i - nums[j]]), if i >= nums[j]

    Base case:
        dp[0] = 1, means if we have no target given, then nothing should be picked from nums, this is 1 combination. this is different with Coin Change problem in that problem the dp[0] is 0

    TC: O(N * M), N is the length of nums, M is the target
    SC: O(M)
    */
    private int combinationSum4ByDP(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (target <= 0) {
            return 0;
        }

        int[] dp = new int[target + 1];
        // Base case.
        dp[0] = 1;

        // DP explore. The outer loop is the constraint ie the Target, means the status.
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) { // use j to try every num in nums.
                if (i >= nums[j]) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }

        return dp[target];

    }

    // TC: O(N * M)
    // SC: O(N * M)
    // for follow up, this is like the knapsack problem, every item could be only take once(the original problem is like the unbounded knapsack)
    // since we have two constraints now, target and nums, so we have a 2-D dp array dp[][]
    // dp[i][j] means the num of combination if we have target i and nums[0:j-1], so
    // 1 if we pick nums[j], dp[i][j] = dp[i - nums[j]][j], here i must >= nums[j]
    // 2 if we dont pick nums[j], dp[i][j] = dp[i][j - 1]
    // we choose the max of the case 1 and 2
    // base case is if we have no target 0 then dp[0][j] = 1; if we have j 0 then dp[i][0] = 0; dp[0][0] = 1;
    private int combinationSum4ByDPForFollowUp(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (target <= 0) {
            return 0;
        }

        int[][] dp = new int[target + 1][nums.length + 1];
        // base
        for (int j = 0; j <= nums.length; j++) {
            dp[0][j] = 1;
        }

        // dp
        for (int i = 1; i <= target; i++) {
            for (int j = 1; j <= nums.length; j++) {
                // 1 if not choose current num at j, then i not change
                dp[i][j] = dp[i][j - 1];

                // 2 if choose num at j, must i >= nums[j]
                if (i >= nums[j - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - nums[j - 1]][j]);
                }
            }
        }

        return dp[target][nums.length];

    }


    // BUT, this will give TLE error when input isnot very big, for example
    // TC: O(N*2^M), N is the length of nums, M is the target
    // SC: O(2^M)
    /*TLE: [4,2,1] 32
      Good: [4,2,1] 9*/
    // intuition: this looks like a Backtracking problem, we could try all Combination Sum to find the number of target. and count the ret.size
    private int combinationSum4ByBacktracking(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (target <= 0) {
            return 0;
        }

        // Arrays.sort(nums);

        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();// temp list for intermediate ret

        // backtracking is actually to try all combination of array's elems, for example, input [4,2,1] 9, it will try all combinations 4*(start with 4),2*(start with2),1*(start with 1), if we find combination sum is target, we record it to ret
        // driver, start from idx 0.
        backtracking(nums, 0, ret, temp, target);

        return ret.size();
    }

    private void backtracking(int[] nums, int curIdx, List<List<Integer>> ret, List<Integer> temp, int target) {
        if (target == 0) {
            ret.add(new ArrayList<>(temp));
            return;
        }
        if (target < 0) {
            return;
        }

        // we could resue the elem in array, so we traverse from curIdx
        for (int i = curIdx; i < nums.length; i++) {
            temp.add(nums[i]);
            backtracking(nums, curIdx, ret, temp, target - nums[i]);
            temp.remove(temp.size() - 1);
        }
    }

}