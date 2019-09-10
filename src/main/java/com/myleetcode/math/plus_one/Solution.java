package com.myleetcode.math.plus_one;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] plusOne(int[] digits) {
        return plusOneByMathII(digits);
        // return plusOneByMath(digits);
    }

    /*
    intuition:
    与67. Add Binary基本一摸一样, 将digits数组按照int来处理
    TC: O(N), N is length of given array digits. Be bareful, if add(0, sum) in the sums list, the for loop cost O(N), in the loop, we add at index 0 every time so cost O(N), totally is O(N^2)
    SC: O(N), for result
    */
    private int[] plusOneByMathII(int[] digits){
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

            // here order is reversed, we could add(0, sum) of course but will cost O(N^2) totally. we add reversed order and at last to add to ret array reverse it again, could get (N) totally.
            sums.add(sum);

            i--;
        }
        if(carry != 0){
            sums.add(carry);
        }

        // add to ret array in reversed order, because order is reversed in sum list
        int[] ret = new int[sums.size()];
        for(int j = 0; j < sums.size(); j++){
            ret[j] = sums.get(sums.size() - 1 - j);
        }
        return ret;

    }

    // sol1:
    /*
    intuition:
    直接在原数组上进行修改，用来优化运行时间，原理就是：从digits的末尾开始，如果遇到小于9的，那么将该digit增加1，然后就可以返回digits了；如果遍历完digits也没有遇到小于9的digit，说明数组内都是9，我们需要额外的一个进位，那么使用一个int[] ret，首位置位1，其余为0即可返回
    https://leetcode.com/problems/plus-one/discuss/24082/My-Simple-Java-Solution

    TC: O(N)
    SC: O(N)
    */
    private int[] plusOneByMath(int[] digits){
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