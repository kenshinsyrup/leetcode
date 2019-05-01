package com.myleetcode.bit_manipulate.power_of_two;

class Solution {
    public boolean isPowerOfTwo(int n) {
        // return isPowerOfTwoByDivide(n);
        return isPowerOfTwoByBitManipulation(n);
    }

    // also we could use Bit Manipulation here
    // Power of 2 means only one bit of n is '1', so if n is power of 2, then its highest bit is 1 and others are 0, so n&(n-1) must be 0. so we could check this, if 0 return true;
    private boolean isPowerOfTwoByBitManipulation(int n){
        if(n <= 0){
            return false;
        }

        return (n & (n - 1)) == 0;
    }

    // TC: O(logN)
    // SC: O(1)
    // intuition: loop: module by 2 is 0 and continue divide by 2
    private boolean isPowerOfTwoByDivide(int n){
        if(n <= 0){
            return false;
        }

        while(n > 1){
            if(n % 2 != 0){
                return false;
            }

            n /= 2;
        }

        return true;

    }
}
