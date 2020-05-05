package com.myleetcode.bit_manipulate.complement_of_base10_integer;

public class Solution {
    public int bitwiseComplement(int N) {
        return bitwiseComplementByBitManipulation(N);
    }

    /*
    https://leetcode.com/problems/complement-of-base-10-integer/discuss/613147/Straightforward-Solution%3A-Runtime%3A-0-ms-faster-than-100.00-of-Java-online-submissions
    */
    private int bitwiseComplementByBitManipulation(int num) {
        if (num < 0) {
            return -1;
        }

        // Must check.
        if (num == 0) {
            return 1;
        }

        int mask = 1;
        int count = 0;
        int res = 0;
        while (num > 0) {
            // Check current bit, flip.
            if ((num & mask) == 0) {
                // Add 1 to res at highest bit.
                int highestBit = 1 << count;
                res += highestBit;
            }

            count++;
            num = num >> 1;
        }

        return res;

    }
}

