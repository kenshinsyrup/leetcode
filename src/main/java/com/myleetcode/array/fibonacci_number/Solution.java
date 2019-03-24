package com.myleetcode.array.fibonacci_number;

class Solution {
    public int fib(int N) {
        return fibByDP(N);// 1ms-100%;35.3M-31%
        // return fibByDPII(N);// 1ms-100%; 35.2M-78%
    }

    // but we could optimize the SC to O(1) if we just store the i-1 and i-2 to caculate ith number
    private int fibByDPII(int N){
        if(N <= 0){
            return 0;
        }
        if(N == 1){
            return 1;
        }

        // i - 2
        int first = 0;
        // i - 1
        int second = 1;
        // i, need to be caculated
        int third = 0;

        for(int i = 2; i<= N; i++){
            // caculate the ith number
            third = first + second;

            // update i, i-1, i-2
            int temp = second;
            second = third;
            first = temp;
        }

        return third;
    }

    // intuition: caculate and store Fibonacci to array, return the Nth one element.
    // TC: O(N), SC:O(N)
    private int fibByDP(int N){
        if(N <= 0){
            return 0;
        }
        if(N == 1){
            return 1;
        }

        int[] dp = new int[N + 1];

        // base
        dp[0] = 0;
        dp[1] = 1;

        // caculate
        for(int i = 2; i <= N; i++){
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[N];
    }
}
