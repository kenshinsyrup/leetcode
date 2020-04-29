package com.myleetcode.binary_search.sum_of_square_numbers;

public class Solution {
    public boolean judgeSquareSum(int c) {
        // return judgeSquareSumByTry(c);
        return judgeSquareSumByBS(c);
    }

    /*
    TC: O(SquareRoot(C) * log(C))
    SC: O(1)
    */
    private boolean judgeSquareSumByBS(int num) {
        if (num < 0) {
            return false;
        }

        for (long a = 0; a * a <= num; a++) {
            long bSquare = num - (a * a);

            if (bs(0, bSquare, bSquare)) {
                return true;
            }
        }

        return false;
    }

    private boolean bs(long start, long end, long target) {
        if (start > end) {
            return false;
        }

        while (start <= end) {
            long mid = start + (end - start) / 2;

            if (mid * mid == target) {
                return true;
            } else if (mid * mid > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return false;

    }

    // TLE
    /*
    TC: O(C)
    SC: O(1)
    */
    private boolean judgeSquareSumByTry(int num) {
        if (num < 0) {
            return false;
        }

        for (int a = 0; a * a <= num; a++) {
            for (int b = 0; b * b <= num; b++) {
                if (a * a + b * b == num) {
                    return true;
                }
            }
        }

        return false;
    }
}
