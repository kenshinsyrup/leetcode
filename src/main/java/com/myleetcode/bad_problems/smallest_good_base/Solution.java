package com.myleetcode.bad_problems.smallest_good_base;

public class Solution {
    public String smallestGoodBase(String n) {
        return smallestGoodBaseByBS(n);
    }

    /*
    Bad problem. Kind of pure math.
    https://leetcode.com/problems/smallest-good-base/discuss/96591/Java-O((logn)2)-binary-search-solution
    */
    private String smallestGoodBaseByBS(String N) {
        if (N == null || N.length() == 0) {
            return null;
        }

        long num = Long.valueOf(N);

        // Find the left and right boundary and do binary search within it.
        int m = (int) (Math.log(num + 1) / Math.log(2));
        while (m > 2) {
            long left = (long) (Math.pow(num + 1, 1.0 / m));
            long right = (long) (Math.pow(num, 1.0 / (m - 1)));
            while (left <= right) {
                long k = left + (right - left) / 2;

                // Get the ret for current k.
                long ret = 0L;
                int i = 0;
                while (i < m) {
                    ret = ret * k + 1;
                    i++;
                }

                // Check.
                if (num == ret) {
                    return String.valueOf(k);
                } else if (ret < num) {
                    left = k + 1;
                } else {
                    right = k - 1;
                }
            }

            m--;
        }

        return String.valueOf(num - 1);
    }
}
