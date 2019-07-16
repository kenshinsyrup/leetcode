package com.myleetcode.dynamic_program.knight_dialer;

class Solution {
    public int knightDialer(int N) {
        return knightDialerByDPTopDown(N);
    }

    // this problem is similar with the Longest Increasing Path in 5800, top down is easier. 对于任意一个点，有八个方向可以去，反过来说，对于任意一个点，到达这个点点路径可能来自八个方向，所以到达这个点的路径的个数就是这可能的八个方向的路径的和。base case就是，如果N是0或者出界或者*则个数为0，如果N为1则个数为1
    // 我们要找的就是到N步达所有键盘上有效点的路径数

    // https://leetcode.com/problems/knight-dialer/discuss/190787/How-to-solve-this-problem-explained-for-noobs!!!

    public static final int MAX = (int) Math.pow(10, 9) + 7;

    private int knightDialerByDPTopDown(int N){
        long s = 0;

        // A 3D array to store the solutions to the subproblems
        long dp[][][] = new long[N + 1][4][3];

        //do n hops from every i, j index (the very requirement of the problem)
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 3; j++) {
                s = (s + paths(dp, i, j, N)) % MAX;
            }
        }

        return (int) s;
    }

    private long paths(long dp[][][], int i, int j, int n) {
        // base case 1
        // if the knight hops outside of the matrix or to * return 0
        //as there are no unique paths from here
        if(i < 0 || j < 0 || i >= 4 || j >= 3 || (i == 3 && j != 1)){
            return 0;
        }

        // base case 2, if n is 1, return 1
        if(n == 1){
            return 1;
        }

        if(dp[n][i][j] > 0){
            return dp[n][i][j];
        }

        dp[n][i][j] = paths(dp, i - 1, j - 2, n - 1) % MAX + // jump from a
                paths(dp, i - 2, j - 1, n - 1) % MAX + // jump from b
                paths(dp, i - 2, j + 1, n - 1) % MAX + // jump from c
                paths(dp, i - 1, j + 2, n - 1) % MAX + // jump from d
                paths(dp, i + 1, j + 2, n - 1) % MAX + // jump from e
                paths(dp, i + 2, j + 1, n - 1) % MAX + // jump from f
                paths(dp, i + 2, j - 1, n - 1) % MAX + // jump from g
                paths(dp, i + 1, j - 2, n - 1) % MAX; // jump from h
        return dp[n][i][j];
    }

}
