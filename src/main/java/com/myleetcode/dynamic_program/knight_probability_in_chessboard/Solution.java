package com.myleetcode.dynamic_program.knight_probability_in_chessboard;

class Solution {
    public double knightProbability(int N, int K, int r, int c) {
        // return knightProbabilityByRecursive(N, K, r, c);
        return knightProbabilityByDP(N, K, r, c);
    }

    // TC: O(K*N^2)
    // SC: O(K*N^2)
    // DP.
    // 题意: https://leetcode.com/problems/knight-probability-in-chessboard/discuss/140286/c-dp-solution-with-explanations-beats-100
    // 解法: https://leetcode.com/problems/knight-probability-in-chessboard/discuss/108189/C%2B%2B-DP-solution-O(N*N*K)-time-complexity-and-explanation
    // dont understand why the K loop must in the outtest position
    public double knightProbabilityByDP(int N, int K, int r, int c) {
        if(N <= 0 || K < 0 || r < 0 || c < 0 || r >= N || c >= N){
            return 0;
        }

        int[][] directions = new int[][]{{-2,-1},{-1,-2},{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1}};

        double[][][] dp = new double[N][N][K + 1];
        // base
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                dp[i][j][0] = 1;
            }
        }

        // db
        for(int k = 1; k <= K; k++){
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    for(int[] dir: directions){
                        if(i + dir[0] < 0 || i + dir[0] >= N || j + dir[1] < 0 || j + dir[1] >= N){
                            continue; // here equals to dp[i][j][k] += 0; but use continue we could omit the 'else' word.
                        }

                        dp[i][j][k] += 0.125 * dp[i + dir[0]][j + dir[1]][k - 1];
                    }
                }
            }
        }

        return dp[r][c][K];

    }

    // recursive thoughts: https://leetcode.com/problems/knight-probability-in-chessboard/discuss/113954/Evolve-from-recursive-to-dpbeats-94
    private double knightProbabilityByRecursive(int N, int K, int r, int c){
        if(N <= 0 || K <= 0 || r < 0 || c < 0){
            return 0;
        }

        int[][] directions = new int[][]{{-2,-1},{-1,-2},{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1}};

        return backtracking(N, K, r, c, directions);
    }

    private double backtracking(int N, int K, int r, int c, int[][] directions){
        // out of board
        if(r < 0 || r >= N || c < 0 || c >= N){
            return 0;
        }
        // if not out of board and K is 0, then probability is 1
        if(K == 0){
            return 1;
        }

        double rate = 0;
        // if not out of board, and K is not 0, then current cell probability is based on it's next steps: 1/8 * probabilityOfNextStep
        for(int[] dir: directions){
            rate += 0.125 * backtracking(N, K - 1, r + dir[0], c + dir[1], directions);
        }

        return rate;
    }
}
