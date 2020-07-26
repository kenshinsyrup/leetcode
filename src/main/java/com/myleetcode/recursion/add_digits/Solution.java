package com.myleetcode.recursion.add_digits;

public class Solution {
    public int addDigits(int num) {
        return addDigitsByRecursion(num);
    }

    private int addDigitsByRecursion(int num) {
        if (num < 0) {
            return 0;
        }
        if (num >= 0 && num <= 9) {
            return num;
        }

        int nextNum = 0;
        while (num > 0) {
            nextNum += num % 10;
            num /= 10;
        }

        return addDigitsByRecursion(nextNum);
    }
}