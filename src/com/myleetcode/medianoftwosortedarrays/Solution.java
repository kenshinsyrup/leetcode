package com.myleetcode.medianoftwosortedarrays;

public class Solution {
    // 来自leetcode：https://leetcode.com/problems/median-of-two-sorted-arrays/
    /**
     j = ((m + n + 1) / 2) - i 的来源
    because:
    i + j = (m - i) + (m -j) if m + n is even, OR (m - i) + (n - j) + 1 if m + n is odd.(这里注意，该解在m+n为odd时采用这个等式
    同时说明了该解法采用的在m+n为odd时，多出来的数字在左边即i+j一边，所以中位数在左边)。
    => i + j = (m + n) / 2 if m + n is even, OR (m + n + 1) / 2 if m + n is odd.
    => i + j = (m + n + 1) / 2 for any m + n, since this is integer dividing (e.g. 4 / 2 = 2 and also (4 + 1) / 2 = 2).
    => j = (m + n + 1) / 2 - i for any m + n.
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 此题目要求的解：1、两个数组合并后的中位数；2、Big-O log(m+n)
        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A;
            A = B;
            B = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j - 1] > A[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && A[i - 1] > B[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = B[j - 1];
                } else if (j == 0) {
                    maxLeft = A[i - 1];
                } else {
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                int minRight = 0;
                if (i == m) {
                    minRight = B[j];
                } else if (j == n) {
                    minRight = A[i];
                } else {
                    minRight = Math.min(B[j], A[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }
}