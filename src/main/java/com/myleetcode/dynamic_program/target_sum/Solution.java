package com.myleetcode.dynamic_program.target_sum;

class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        // return findTargetSumWaysByDP(nums, S);// DP
        return findTargetSumWaysByDFS(nums, S); // DFS
    }

    // TC: O(2^N), N is the length of nums
    // SC: O(2^N)
    // https://leetcode.com/problems/target-sum/discuss/97371/Java-Short-DFS-Solution
    // DFS way, this way it's like 282, this is kind of backtracking, the explore and back part is: pos + 1, eval + nums[pos], eval - nums[pos].
    class Result{
        int count;
        Result(int count){this.count = count;}
    }
    public int findTargetSumWaysByDFS(int[] nums, int S) {
        if (nums == null || nums.length == 0) return 0;

        Result ret = new Result(0);
        dfs(nums, S, 0, 0, ret);
        return ret.count;
    }

    public void dfs(int[] nums, int target, int pos, long eval, Result ret){
        if (pos == nums.length) {
            if (target == eval) ret.count++;
            return;
        }
        dfs(nums, target, pos + 1, eval + nums[pos], ret);
        dfs(nums, target, pos + 1, eval - nums[pos], ret);
    }

    // too hard to understand, understand the 416 is enough.

    // TC: O(N * M), N is the length of nums, M is the target
    // SC: O(M)
    // intuition: DP. 322, 377, 416, 494
    // the important parts: 1 the given nums are all non-negative, but S maybe negative or 0 or positive, so our search range is [-Sum(nums): Sum(nums)] 2 the thoughts behind this algo is still the same as 416
    /*
if we calculate total sum of all candidate numbers, then the range of possible calculation result will be in the range [-sum, sum].
So we can define an dp[][] array with inner size sum * 2 + 1 to calculate number of possible ways to reach every target value between -sum to sum, and save results to dp array. dp[sum + S] will be out final result. (because dp[][sum] or less represents number of possible ways to reach a number in range [-sum, 0])
 */
    // https://leetcode.com/problems/target-sum/discuss/97335/Short-Java-DP-Solution-with-Explanation/239358
    /**
     * sub-problem: dp[i][j] represents number of possible ways to reach sum j by using given i items
     * base case: dp[0][sum], position sum represents sum 0
     * recurrence relation:
     * dp[i][j] += dp[i - 1][j + nums[i - 1]] if j + nums[i - 1] <= sum * 2
     * dp[i][j] += dp[i - 1][j - nums[i - 1]] if j - nums[i - 1] >= 0
     *
     * explanation: if j + nums[i - 1] or j - nums[i - 1] is in correct range, we can use the number nums[i - 1]
     * to generate next state of dp array
     * */
    // !!!BUT, did not figure out how to implement according to the below description. above is the correct explanation.
    // this looks very familiar with 416, we also separate the nums to two parts, the only difference is one part is applied + and one part is applied -, then our goal is the target.
    // we have two constraints, the nums and the target
    // dp[i][j] means given i num, we pick from them to form target, if we pick the nums[i-1], then dp[i][j] = dp[i-1][j - nums[i]], if we dont pick the nums[i-1], then dp[i][j] = dp[i-1][j + nums[i]], our dp[i][j] always choose the true branch
    // base case is the dp[0][0] = true; dp[i][0] = true; dp[0][j] = false
    // but we stil need one variable to help us remember the num of combinations, this is based on target, so we need counts[]
    // counts[i] += counts[i - nums[i]] if the dp[i][j] is true, base is counts[0] = 0
    private int findTargetSumWaysByDP(int[] nums, int target){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        // caculate the sum of nums
        int sum = 0;
        for(int i = 0; i < len; i++){
            sum += nums[i];
        }
        // check if the given target is valid
        if(target < -sum || target > sum){
            return 0;
        }

        int sumLen = 2 * sum;
        int[][] dp = new int[len + 1][sumLen + 1];
        dp[0][sum] = 1;//position sum represents sum 0

        for(int i = 1; i <= len; i++){
            for(int j = 0; j <= sumLen; j++){
                // choose the nums[i - 1] to the corresponding part
                if(j - nums[i - 1] >= 0){
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
                if(j + nums[i - 1] <= sumLen){
                    dp[i][j] += dp[i - 1][j + nums[i - 1]];
                }
            }
        }

        // dp[][0:sum] for [-sum:0], and we need the dp at target so it's dp[][sum + target]
        return dp[len][sum + target];
    }
}
