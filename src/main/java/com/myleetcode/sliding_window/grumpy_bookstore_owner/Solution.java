package com.myleetcode.sliding_window.grumpy_bookstore_owner;

class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        // return maxSatisfiedByCalculate(customers, grumpy, X);
        return maxSatisfiedBySlidingWindow(customers, grumpy, X);
    }

    /*
    Sliding Window with fix length
    https://leetcode.com/problems/grumpy-bookstore-owner/discuss/299230/JavaPython-3-Sliding-window.

    TC: O(N)
    SC: O(1)
    */
    private int maxSatisfiedBySlidingWindow(int[] customers, int[] grumpy, int X) {
        if (customers == null || customers.length == 0 || grumpy == null || customers.length != grumpy.length) {
            return 0;
        }

        int len = customers.length;
        int ret = 0;
        int window = 0; // Keep a at most X length window(right bound is index i), which records only the notSatisfied customers in this window.
        int satisfied = 0;
        int maxWindow = 0;
        for (int i = 0; i < len; i++) {
            if (grumpy[i] == 0) {
                satisfied += customers[i];
            } else {
                window += customers[i];
            }

            // If sliding window length is wider than X, remove the left most grumpy record.
            if (i + 1 > X) {
                if (grumpy[i - X] == 1) {
                    window -= customers[i - X];
                }
            }
            maxWindow = Math.max(maxWindow, window);
        }

        return satisfied + maxWindow;
    }

    /*
    TC: O(N * X)
    SC: O(1)
    */
    private int maxSatisfiedByCalculate(int[] customers, int[] grumpy, int X) {
        if (customers == null || customers.length == 0) {
            return 0;
        }

        // 1. Get base num.
        int len = customers.length;
        int baseNum = 0;
        for (int i = 0; i < len; i++) {
            if (grumpy[i] == 0) {
                baseNum += customers[i];
            }
        }

        // i means use secret
        int ret = 0;
        for (int i = 0; i <= len - X; i++) {
            int secretNum = 0;
            for (int j = 0; j < X; j++) {
                if (grumpy[i + j] == 1) {
                    secretNum += customers[i + j];
                }
            }

            ret = Math.max(ret, secretNum + baseNum);
        }

        return ret;
    }


}