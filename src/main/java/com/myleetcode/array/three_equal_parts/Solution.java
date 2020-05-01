package com.myleetcode.array.three_equal_parts;

public class Solution {
    public int[] threeEqualParts(int[] A) {
        return threeEqualPartsByCount(A);
    }

    /*
    Find pattern according to the description.
    https://leetcode.com/problems/three-equal-parts/discuss/183922/C%2B%2B-O(n)-time-O(1)-space-12-ms-with-explanation-and-comments
    */
    private int[] threeEqualPartsByCount(int[] A) {
        if (A == null || A.length == 0) {
            return new int[]{-1, -1};
        }

        int len = A.length;

        // 1. Count number of 1's in the given array
        int countOnes = 0;
        for (int num : A) {
            if (num == 1) {
                countOnes++;
            }
        }

        // If no 1 is found, that means we can return ans as {0, size-1}
        if (countOnes == 0) {
            return new int[]{0, len - 1};
        }

        // If no of 1's is not a multiple of 3, then we can never find a possible partition since
        // there will be atkeast one partition that will have different no of 1's and hence
        // different binary representation
        // For example,
        // Given :
        // 0000110  110  110
        //       |  |    |
        //       i       j
        // Total no of ones = 6
        // 0000110         110      110
        //     |           |        |
        //     start       mid      end
        // Match starting from first 1, and putting 2 more pointers by skipping k 1's
        if (countOnes % 3 != 0) {
            return new int[]{-1, -1};
        }

        // 2. Find number of 1s in each partition.
        int k = countOnes / 3;

        // 3.1 Find the first 1 in the array
        int start = 0;
        for (int i = 0; i < len; i++) {
            if (A[i] == 1) {
                start = i;
                break;
            }
        }

        // 3.2 Find the (k+1)th 1 in the array, that will be the first 1 in the second partition.
        int mid = 0;
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (A[i] == 1) {
                count++;
            }

            if (count == k + 1) {
                mid = i;
                break;
            }
        }

        // 3.3 Find (2*k +1)th 1 in the array, that will be the first 1 in the third partition.
        int end = 0;
        count = 0;
        for (int i = 0; i < len; i++) {
            if (A[i] == 1) {
                count++;
            }

            if (count == 2 * k + 1) {
                end = i;
                break;
            }
        }

        // 4. Match all values till the end of the array
        while (end < len && A[start] == A[mid] && A[mid] == A[end]) {
            start++;
            mid++;
            end++;
        }

        // Return appropriate values if all the values have matched till the end
        if (end == len) {
            return new int[]{start - 1, mid};
        }
        // otherwise, no such indices found
        return new int[]{-1, -1};
    }
}
