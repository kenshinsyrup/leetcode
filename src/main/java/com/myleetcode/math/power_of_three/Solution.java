package com.myleetcode.math.power_of_three;

class Solution {
    public boolean isPowerOfThree(int n) {
        return isPowerOfThreeByDivide(n);
    }

    // BTW, this problem's follow up is non-sense

    // TC: O(log3(N))
    // SC: O(1)
    // intuition: divide to 3 loop
    // !!!
    // <= 0 is not power of any number
    // 1 is every number's power of 0
    private boolean isPowerOfThreeByDivide(int n){
        if(n < 1){
            return false;
        }

        while(n != 1){
            if(n % 3 == 0){
                n /= 3;
            }else{
                return false;
            }
        }

        return true;
    }
}
