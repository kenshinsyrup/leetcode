package com.myleetcode.math.plus_one;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] plusOne(int[] digits) {
        return plusOneByMath(digits);// 推荐，比较模块化的写法
        // return plusOneByMathTrick(digits);// 不喜欢，感觉有点trick，其他的题目比如67题就不能这样写
    }

    // 与67. Add Binary基本一摸一样
    private int[] plusOneByMath(int[] digits){
        // special case
        if(digits == null || digits.length == 0){
            return new int[0];
        }

        int len = digits.length;
        int i = len - 1;
        int sum = 0;
        int carry = 0;
        List<Integer> sums = new ArrayList<>();
        while(i >= 0){
            if(i == len - 1){
                sum = (digits[i] + carry + 1) % 10;
                carry = (digits[i] + carry + 1) / 10;
            }else{
                sum = (digits[i] + carry) % 10;
                carry = (digits[i] + carry) / 10;
            }

            sums.add(0, sum);

            i--;
        }

        if(carry != 0){
            sums.add(0, carry);
        }

        //   return sums.toArray();//incompatible types: Object[] cannot be converted to int[]
        int[] ret = new int[sums.size()];
        for(int j = 0; j < sums.size(); j++){
            ret[j] = sums.get(j);
        }
        return ret;

    }

    // discuss区有一种直接在原数组上进行修改的潮流，用来优化运行时间，原理就是：从digits的末尾开始，如果遇到小于9的，那么将该digit增加1，然后就可以返回digits了；如果遍历完digits也没有遇到小于9的digit，说明我们需要额外的一个进位，那么使用一个int[] ret，首位置位1，其余为0即可返回
    //https://leetcode.com/problems/plus-one/discuss/24082/My-Simple-Java-Solution
    private int[] plusOneByMathTrick(int[] digits){
        if(digits == null || digits.length == 0){
            return new int[0];
        }

        int len = digits.length;
        for(int i = len - 1; i >= 0; i--){
            if(digits[i] < 9){ // 如果找到小于9的，那么+1不会进位，那么[0-i]之间的数字不会有任何改变，所以将digits[i]++,返回digits即可
                digits[i]++;
                return digits;
            }else{
                digits[i] = 0; // 如果digits[i] == 9,那么+1之后肯定会进位与i-1区求和，i位置肯定为0.
            }
        }

        // 如果遍历完毕还没有return，说明digits全部为9,我们需要额外的一个位置来进位为1，其他位置都是0
        int[] ret = new int[len + 1];
        ret[0] = 1;

        return ret;
    }

}
