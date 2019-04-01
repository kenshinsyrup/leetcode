package com.myleetcode.bit_manipulate.number_of_1_bits;

public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        return hammingWeightByCount(n);
    }

    private int hammingWeightByCount(int n){
        int count = 0;

        int mask = 1;
        for(int i = 0; i < 32; i++){
            if((n & mask) != 0){
                count++;
            }

            mask = mask << 1;
        }

        return count;
    }
}
