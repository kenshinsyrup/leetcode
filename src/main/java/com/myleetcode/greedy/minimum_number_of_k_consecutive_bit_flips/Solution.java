package com.myleetcode.greedy.minimum_number_of_k_consecutive_bit_flips;

public class Solution {
    public int minKBitFlips(int[] A, int K) {
        return minKBitFlipsByGreedy(A, K);
    }

    /*
    Greedy to simulate.
    https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/discuss/238557/Java-clean-O(n*k)-greedy-solution
    https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/discuss/238557/Java-clean-O(n*k)-greedy-solution

    Greedy proof:
    https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/discuss/238683/Proof%3A-Why-greedy-works

    TC: O(N*K)
    SC: O(1)
    */
    private void flip(int[] A, int K, int i) {
        for (int j = i; j < i + K; j++) {
            A[j] = 1 - A[j];
        }
    }

    public int minKBitFlipsByGreedy(int[] A, int K) {
        int cnt = 0;
        int len = A.length;
        for (int i = 0; i < len; i++) {
            if (A[i] == 0) {
                if (i + K > len) {
                    return -1;
                }

                flip(A, K, i);
                cnt++;
            }
        }

        return cnt;
    }

}
