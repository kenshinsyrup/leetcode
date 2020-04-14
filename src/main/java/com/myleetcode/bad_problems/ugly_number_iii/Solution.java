package com.myleetcode.bad_problems.ugly_number_iii;

public class Solution {
    public int nthUglyNumber(int n, int a, int b, int c) {
        return nthUglyNumberByMath(n, a, b, c);
    }

    /*
    Math
    https://leetcode.com/problems/ugly-number-iii/discuss/387780/C%2B%2B-Java-Binary-Search-with-Venn-Diagram-Explain-Math-Formula

    */

    int MAX_ANS = (int) 2e9; // 2*10^9

    private int nthUglyNumberByMath(int N, int A, int B, int C) {
        if (N <= 0 || A <= 0 || B <= 0 || C <= 0) {
            return 0;
        }

        int left = 0;
        int right = MAX_ANS;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            // We need first elemt that makes count(mid, A, B, C) >= N
            if (count(mid, A, B, C) < N) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;

    }

    // 辗转相除得最大公约数: Euclidean algorithm
    // Greatest Common Divisor
    long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // 乘积除最大公约数即为最小公倍数
    // Least Common Multiple
    long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    int count(long num, long a, long b, long c) {
        return (int) (num / a + num / b + num / c
                - num / lcm(a, b)
                - num / lcm(b, c)
                - num / lcm(a, c)
                + num / (lcm(a, lcm(b, c))));
    }
}
