package com.myleetcode.dynamic_program.triangle;

import java.util.List;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        // return minimumTotalByDP(triangle); // original
        return minimumTotalByDPII(triangle); // O(n) space for follow up
    }

    // TC: O(R * C)
    // SC: O(C)
    // optimize to O(N) space, observe this: dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(j); we actually dont need the whole rows, we only need two rows, one is for i-1 row act as the previous row, one is for the i row act as the current row, so we use the %2 fomular
    private int minimumTotalByDPII(List<List<Integer>> triangle){
        if(triangle == null || triangle.size() == 0 || triangle.get(0) == null || triangle.get(0).size() == 0){
            return 0;
        }

        int rowLen = triangle.size();
        int colLen = triangle.get(triangle.size() - 1).size();

        // here, we only need two rows, because our dp fomula only need one previous row to caculate current row's result
        int[][] dp = new int[2][colLen];

        // init
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < colLen; j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        // base case, we only have one row to as base, so we only set the dp[0][0] value
        dp[0][0] = triangle.get(0).get(0);

        // dp
        for(int i = 1; i < rowLen; i++){
            int curColLen = triangle.get(i).size();
            // here we must process every col, so col is from 0, so we have to process the j==0 case carefully
            for(int j = 0; j < curColLen; j++){
                if(j == 0){
                    dp[i % 2][j] = dp[(i - 1) % 2][j] + triangle.get(i).get(j);
                }else{
                    dp[i % 2][j] = Math.min(dp[(i - 1) % 2][j], dp[(i - 1) % 2][j - 1]) + triangle.get(i).get(j);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for(int j = 0; j < colLen; j++){
            min = Math.min(min, dp[(rowLen - 1) % 2][j]);
        }
        return min;
    }


    // TC: O(R * C), R is the row # and C is the longest list's length in triangle
    // SC: O(R * C)
    // intuition: DP. similar: 62, 63, 64, 120, 174, 931
    // for pos(i,j) its adjacent nums in the next row is pos(i+1, j) and pos(i+1, j+1)
    // dp[i][j] means the path sum to pos(i,j), then dp[i][j] = min(dp[i-1][j], dp[i-1][j-1]) + traiangle[i][j] if j>=1 && i >=1
    // base case is the first col and first row, their path only from the row or col before them. so we dont need extra col or row to act as base.
    private int minimumTotalByDP(List<List<Integer>> triangle){
        if(triangle == null || triangle.size() == 0 || triangle.get(0) == null || triangle.get(0).size() == 0){
            return 0;
        }

        int rowLen = triangle.size();
        int colLen = triangle.get(triangle.size() - 1).size();

        int[][] dp = new int[rowLen][colLen];
        // init
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        // base case
        for(int i = 0; i < rowLen; i++){
            if(i == 0){
                dp[i][0] = triangle.get(i).get(0);
            }else{
                dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0);
            }
        }
        // for(int j = 0; j < colLen; j++){
        //     if(j == 0){
        //         dp[0][j] = triangle.get(0).get(j);
        //     }
        // }
        // first row only one col, so we only need to assign the dp[0][0], and this is already coverd by the first row case

        int min = Integer.MAX_VALUE;
        // dp
        for(int i = 1; i < rowLen; i++){
            int curColLen = triangle.get(i).size();// every row has different col #

            for(int j = 1; j < curColLen; j++){
                dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(j);
            }
        }

        // get the last row's min
        for(int j = 0; j < colLen; j++){
            min = Math.min(min, dp[rowLen - 1][j]);
        }

        return min;
    }


}
