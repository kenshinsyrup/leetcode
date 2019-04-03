package com.myleetcode.bit_manipulate.hamming_distance;

class Solution {
    public int hammingDistance(int x, int y) {
        return hammingDistanceByDigitManipulation(x, y);
    }

    // intuition: everytime we compare the last digit at each int, we need a reference int and here 1 is perfect, to compare: (x & 1) and (y & 1), if not same then count++; then x = x >> 1, y = y >> 1, cnotinue.
    private int hammingDistanceByDigitManipulation(int x, int y){
        int count = 0;

        while(x != 0 || y != 0){
            if((x & 1) != (y & 1)){
                count++;
            }

            x = x>>1;
            y = y>>1;
        }

        return count;
    }
}
