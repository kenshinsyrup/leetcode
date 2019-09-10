package com.myleetcode.math.reverse_integer;

public class Solution {
    public int reverse(int x) {
        return reverseByMath(x);
    }

    private int reverseByMath(int num){
        int sign = 1;
        if(num < 0){
            sign = -1;
            num = -num;
        }

        int ret = 0;
        while(num > 0){
            int remain = num % 10;
            if(ret > (Integer.MAX_VALUE - remain) / 10){
                return 0;
            }

            ret *= 10;
            ret += remain;
            num /= 10;
        }

        return ret * sign;
    }
}