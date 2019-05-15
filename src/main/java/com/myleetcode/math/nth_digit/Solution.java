package com.myleetcode.math.nth_digit;

class Solution {
    public int findNthDigit(int n) {
        return findNthDigitByMath(n);
    }

    // 思路: https://leetcode.com/problems/nth-digit/discuss/88363/Java-solution/93218
    /*
Straight forward way to solve the problem in 3 steps:
find the length of the number where the nth digit is from
find the actual number where the nth digit is from
find the nth digit and return
*/
    /*
分析
1,2 ... 8,9, 10, 11, 12 ... 98, 99, 100, 101, 102 ... 998, 999, 1000 ...
observation: 假设我们要找第2886 个位 (就target数 是998，target digits 是8)
1~9, 10 ~ 99, 100 ~ 999
9个, 9*10个,    9*10*10个
9*1digit, 9*1*2digit, 9*10*10*3digit

那么2886 - 9 - 9 * 10 * 2 = 2697 < 9*10*10*3 = 2700
那么target 就落在了区域3中( 100- 999 )

我们知道区域3 是由3digit的数组成的
所以target数是以 100 为起始数，(2697 - 1)/3 = 898 为100以后的数
target 数 = 100 + 898 = 998
(2697-1) % 3 = 2 就是 998 的target digit
target digit = 998.charAt( 2 ) = 8;

解题思路
2886 = 9*1 + 90 * 2 + 2697
*/
    private int findNthDigitByMath(int n) {
        int start = 1; // 起始为1
        int len = 1; // 区间数字长度
        long base = 9; // base is long to avoid overflow int when n is big like Integer.MAX_VALUE

        while( n > len * base ){    //判断n落在的区间
            n -= len * base;
            len++;              //len 用来记录target 数的长度
            start *= 10;        //循环的时候不用，等会用来重组target 数
            base *= 10;
        }

        int target = start + (n - 1)/len;   //减1是因为start 自己算一个数，要把start 从计算中抠掉
        int pos = (n - 1) % len;         //在target 中的位置

        return Character.getNumericValue( Integer.toString(target).charAt(pos) );
    }
}
