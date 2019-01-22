package com.myleetcode.dynamic_program.climbing_stairs;

class Solution {
    public int climbStairs(int n) {
        // special case
//         if(n == 0){
//             return 0;
//         }
        
//         if(n == 1){
//             return 1;
//         }
        if(n < 3){
            return n;
        }

        // 关于dp的一个讲解
        // https://www.quora.com/Are-there-any-good-resources-or-tutorials-for-dynamic-programming-DP-besides-the-TopCoder-tutorial/answer/Michal-Danil%C3%A1k?share=1&srid=3OBi
        // https://www.hackerearth.com/zh/practice/algorithms/dynamic-programming/introduction-to-dynamic-programming-1/tutorial/
        
//         这个号称是经典的dp问题
//         爬台阶，每次都只能选择爬1还是2级，问多少种方式到底第n级。也就是从1到n一共n个台阶。
//         从最后一次分析，要爬到n级，那么只有两种方法：从n-1级爬1级或者从n-2级爬2级（从n-2不能爬1再爬1因为那就是从n-1爬1了）。所以dp[n] == dp[n-2] + dp[n-1]，只有问题就变成了，从1爬到n-1和n-2有多少种方式。
        
        // 因为我们要算的是dp[i] = dp[i - 2] + dp[i - 1],所以正常判断从3开始
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i < n+1; i++){
            dp[i] = dp[i - 2] + dp[i - 1];
        }
        
        return dp[n];
        
    }
}