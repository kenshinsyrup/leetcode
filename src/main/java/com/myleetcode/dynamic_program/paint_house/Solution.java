package com.myleetcode.dynamic_program.paint_house;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int minCost(int[][] costs) {
        // return minCostByBacktracking(costs); // LTE
        return minCostByDP(costs);
    }


    // generalized solution: https://leetcode.com/problems/paint-house/discuss/68243/Java-solution-Not-limited-to-3-colors-No-change-to-original-input
    //
    // TC: O(N)
    // SC: O(N)
    // https://leetcode.com/problems/paint-house/discuss/68211/Simple-java-DP-solution
    // we know the naive dfs/backtracking solution cost too much and will give us a LTE.
    // we could reduce the TC by deduce from the recursion to DP
    // f(i, j) means the total cost if we paint the ith house with the jth color
    // 1 f(i, 0) = costs[i][0] + min(costs[i][1], costs[i][2])
    // 2 f(i, 1) = costs[i][1] + min(costs[i][0], costs[i][2])
    // 3 f(i, 2) = costs[i][2] + min(costs[i][0], costs[i][1])
    // at last, we just choose the min one of f(costs.length-1, j)
    // base case is we just have one house to paint so f(0, j) = costs[0][j]
    private int minCostByDP(int[][] costs){
        if(costs == null || costs.length == 0 || costs[0] == null || costs[0].length == 0){
            return 0;
        }

        int len = costs.length;
        int[][] dp = new int[len][3];
        for(int j = 0; j < 3; j++){
            dp[0][j] = costs[0][j];
        }

        // dp
        for(int i = 1; i < len; i++){
            dp[i][0] = costs[i][0] + Math.min(dp[i - 1][1], dp[i - 1][2]);
            dp[i][1] = costs[i][1] + Math.min(dp[i - 1][0], dp[i - 1][2]);
            dp[i][2] = costs[i][2] + Math.min(dp[i - 1][0], dp[i - 1][1]);
        }

        return Math.min(dp[len - 1][0], Math.min(dp[len - 1][1], dp[len - 1][2]));

    }


    // TLE Error.
    // TC: O(3^N)
    // SC: O(3^N)
    // intuition: so the given costs array, idx is the house, value is the cost to paint the house with 3 options, the idx of option is color, the value of option is color cost
    // we should not use two same idx of option array in adjacent idx of costs array
    // so the first thought is we could try all the possibilities, so we could try dfs and backtracking
    private int minCostByBacktracking(int[][] cost){
        if(cost == null || cost.length == 0 || cost[0] == null || cost[0].length == 0){
            return 0;
        }

        List<Integer> ret = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        backtracking(cost, 0, -1, ret, temp);// at first we have no previous color, so we give it a impossible value: -1

        // find the lowest cost
        int minCost = Integer.MAX_VALUE;
        for(int v: ret){
            minCost = Math.min(minCost, v);
        }

        return minCost;

    }

    // when we try all the combinations, we use the prevColorIdx to avoid the same color of adjacent house
    private void backtracking(int[][] cost, int houseIdx, int prevColorIdx, List<Integer> ret, List<Integer> temp){
        // base, when we are at the last house, we caculate the total cost of this combination
        if(houseIdx >= cost.length){
            int sum = 0;
            for(int v: temp){
                sum += v;
            }
            ret.add(sum);

            return;
        }

        // try every house with every color
        for(int j = 0; j < 3; j++){
            if(j != prevColorIdx){
                temp.add(cost[houseIdx][j]);
                backtracking(cost, houseIdx + 1, j, ret, temp);
                temp.remove(temp.size() - 1);
            }
        }
    }
}
