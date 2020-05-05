package com.myleetcode.bit_manipulate.number_complement;

public class Solution {
    public int findComplement(int num) {
        return findComplementByBitManipulation(num);
    }

    /*

     */
    private int findComplementByBitManipulation(int num) {
        if (num < 0) {
            return -1;
        }

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
