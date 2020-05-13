package com.myleetcode.math.roman_to_integer;

public class Solution {
    public int romanToInt(String s) {
        return romanToIntByBackward(s);
    }

    /*
    TC: O(N)
    SC: O(1)
    */
    private int romanToIntByBackward(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        int res = 0;
        int pre = 0;
        int cur = 0;
        int len = str.length();
        for (int i = len - 1; i >= 0; i--) {
            cur = romanTable(str.charAt(i));

            if (i == str.length() - 1) {
                res = cur;
            } else {
                // From right to left, if cur is less than pre, means minus, otherwise is plus.
                if (cur < pre) {
                    res -= cur;
                } else {
                    res += cur;
                }
            }

            // Update pre.
            pre = cur;
        }

        return res;
    }

    public int romanTable(char c) {
        int num = 0;
        switch (c) {
            case 'I':
                num = 1;
                break;
            case 'V':
                num = 5;
                break;
            case 'X':
                num = 10;
                break;
            case 'L':
                num = 50;
                break;
            case 'C':
                num = 100;
                break;
            case 'D':
                num = 500;
                break;
            case 'M':
                num = 1000;
                break;
            default:
                num = 0;
                break;
        }
        return num;
    }
}
