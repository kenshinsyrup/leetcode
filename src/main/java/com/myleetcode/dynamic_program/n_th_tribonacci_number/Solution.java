package com.myleetcode.dynamic_program.n_th_tribonacci_number;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int tribonacci(int n) {
        // return tribonacciByRecursion(n); // TLE
        // return tribonacciByDP(n);
        return tribonacciByDPMemo(n);
    }

    /*
    DP.
    */
    private int tribonacciByDP(int N) {
        if (N < 0) {
            return -1;
        }
        if (N == 0) {
            return 0;
        }
        if (N == 1) {
            return 1;
        }
        if (N == 2) {
            return 1;
        }

        int[] dp = new int[N + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;

        for (int i = 3; i <= N; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }

        return dp[N];
    }

    /*
    Recursion, use Memo to avoid repeat calculations, ie DP with Memo.
    */
    private int tribonacciByDPMemo(int N) {
        if (N < 0) {
            return -1;
        }

        Map<Integer, Integer> memo = new HashMap<>();
        return dpMemo(N, memo);
    }

    private int dpMemo(int N, Map<Integer, Integer> memo) {
        if (N == 0) {
            return 0;
        }
        if (N == 1) {
            return 1;
        }
        if (N == 2) {
            return 1;
        }

        if (memo.containsKey(N)) {
            return memo.get(N);
        }

        int num = dpMemo(N - 1, memo) + dpMemo(N - 2, memo) + dpMemo(N - 3, memo);
        memo.put(N, num);

        return memo.get(N);
    }

    /*
    TLE. Because too many repeat calcuations.
    Recursion
    Similar to Fibonacci Recursion Solution. 509. Fibonacci Number.
    */
    private int tribonacciByRecursion(int N) {
        if (N < 0) {
            return -1;
        }

        return recurse(N);
    }

    private int recurse(int N) {
        if (N == 0) {
            return 0;
        }
        if (N == 1) {
            return 1;
        }
        if (N == 2) {
            return 1;
        }

        return recurse(N - 1) + recurse(N - 2) + recurse(N - 3);
    }
}
