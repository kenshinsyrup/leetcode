package com.myleetcode.math.add_binary;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public String addBinary(String a, String b) {
        // special case
        if(a == null){
            return b;
        }
        if(b == null){
            return a;
        }

        // return addBinaryByMath(a, b);
        return addBinaryByMathII(a, b);
    }

    // 缘起：leetcode群讨论的时候，听说有一个词叫做“高精度”，然后以为是float或者double之类的浮点型的精度，但其实说的是类似于“数据足够大的时候，必须用高精度运算， 就是 数组 一个element存一位....可以开到1000000位，1mb内存， 一个存个intger或者long那更多了，然后做运算。”。所以来重新看一下这类题目.
    // 这里说的高精度，英文是arbitrary precision arithmetic
    // 试图优化一下addBinaryByMath，结果第一遍还写错了。input为"0","0"的时候输出了"10",而不是正确的"0". 原因在于：vB = Integer.valueOf(b.charAt(j)); //str "1", Integer.valueOf(b.charAt(0))结果是49('1'的ASCII)而不是1
    private String addBinaryByMathII(String a, String b){
        int lenA = a.length();
        int lenB = b.length();

        int vA = 0;
        int vB = 0;

        int sum = 0;
        int carry = 0;
        List<Integer> sums = new ArrayList<>();

        int i = lenA - 1;
        int j = lenB - 1;
        while(i >=0 || j >= 0){
            if(i < 0){
                vA = 0;
            }else{
                // vA = Integer.valueOf(a.charAt(i));
                vA = Integer.valueOf(a.substring(i, i + 1));
            }

            if(j < 0){
                vB = 0;
            }else{
                // vB = Integer.valueOf(b.charAt(j)); //str "1", Integer.valueOf(b.charAt(0))结果是49('1'的ASCII)而不是1
                vB = Integer.valueOf(b.substring(j, j + 1));
            }

            sum = (vA + vB + carry) % 2;
            carry = (vA + vB + carry) / 2;

            sums.add(0, sum);

            i--;
            j--;
        }

        if(carry != 0){
            sums.add(0, 1);
        }

        StringBuilder sb = new StringBuilder();
        for(int v: sums){
            sb.append(v);
        }
        return sb.toString();
    }

    // traverse a and b reverse order
    // use a variable to keep carry, carry = carry + a.charAt(i) + b.charAt(j) - 2 until one of them reach 0.
    // then carry and the leaving string to calculate until the leaving string reach 0
    private String addBinaryByMath(String a, String b){
        // keep a is longer
        if(a.length() < b.length()){
            String temp = "";
            temp = a;
            a = b;
            b = temp;
        }

        int lenA = a.length();
        int lenB = b.length();

        int i = lenA - 1;
        int j = lenB - 1;
        int vA = 0; // value of char at location i of a
        int vB = 0; // value of char at location j of b

        int sum = 0; // (vA + vB + carry) % 2
        int carry = 0; //  (vA + vB + carry) / 2
        int[] sums = new int[lenA]; // store sum
        while(i >= 0){
            // get int value
            if(a.charAt(i) == '1'){
                vA = 1;
            }else{
                vA = 0;
            }

            // j is shorter
            if(j >= 0){
                if(b.charAt(j) == '1'){
                    vB = 1;
                }else{
                    vB = 0;
                }
                j--;
            }else{
                vB = 0;
            }

            // most important part is how to caculate the sum and carry
            sums[i] = (carry + vA + vB ) % 2;
            // carry
            carry = (vA + vB + carry) / 2;

            i--;

        }

        StringBuilder sb = new StringBuilder();
        // if still have carry, ret[0] should be 1
        if(carry == 1){
            sb.append(1);
        }

        for(int v: sums){
            sb.append(v);
        }

        return sb.toString();

    }
}