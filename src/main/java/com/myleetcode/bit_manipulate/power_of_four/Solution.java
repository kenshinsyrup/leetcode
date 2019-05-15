package com.myleetcode.bit_manipulate.power_of_four;

class Solution {
    public boolean isPowerOfFour(int num) {
        return isPowerOfFourByDivide(num);
        // return isPowerOfFourByBitManipulation(num);// follow up
    }

    // intuition: divide 4 loop
    private boolean isPowerOfFourByDivide(int num){
        if(num < 1){
            return false;
        }

        while(num != 1){
            if(num % 4 == 0){
                num /= 4;
            }else{
                return false;
            }
        }

        return true;
    }

    // follow up: Bit Manipulation
    /*
The basic idea is from power of 2, We can use "n&(n-1) == 0" to determine if n is power of 2.
For power of 4, the additional restriction is that in binary form, the only "1" should always located at the even position(0 based).
For example, 4^0 = 0001, 4^1 = 0100, 4^2 = 0001 0000. So we can use "num & 0x55555555==num" to check if "1" is located at the odd position. about the 0x55555555: https://leetcode.com/problems/power-of-four/discuss/80456/O(1)-one-line-solution-without-loops/85042
    */
    private boolean isPowerOfFourByBitManipulation(int num){
        // 1st, num > 0, must
        // 3nd, num is power of 2
        // 3rd, num's 1 only at even pos(0 based)
        return (num > 0) && ((num & (num - 1)) == 0) && ((num & 0x55555555) == num);
    }
}
