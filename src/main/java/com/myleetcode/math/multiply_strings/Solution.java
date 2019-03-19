package com.myleetcode.math.multiply_strings;

class Solution {
    // 感觉这个题的要求并不适合面试用
    public String multiply(String num1, String num2) {
        return multiplyByMath(num1, num2);
    }

    // intuition: multiplication is a special form additon, we know how to add two string-from big number, then we also know how to add many string-form big number. 但是这样需要至少把一个string-form num转成int num来进行loop，所以不符合要求。这个链接中图解竖式，可以看
    // 思路：https://leetcode.com/problems/multiply-strings/discuss/17605/Easiest-JAVA-Solution-with-Graph-Explanation. 但是这个解法太难了，自我感觉无法在面试中想清楚并写出。
    // 思路2: https://leetcode.com/problems/multiply-strings/discuss/17608/AC-solution-in-Java-with-explanation。这个就好的多，把复杂的问题简单化，乘法的本质就是竖式相加，那么我们就把竖式的每一个部分得到，然后相加他们。
    // 根据乘法的竖式写法来做
    /*
    numA:"123", i[0-2]
    numB:"13",j[0-1]
    ret:int[3+2=5], index[0-5]

        124
         13
      -----
         (12) i == 2, j == 1, (12)是一个整体存入nums[i+j+1]=nums[4],在求nums阶段，我们目的是获
         4    i == 2, j == 0   取每个i+j+1处的实际值
      -----
         6    i == 1, j == 1
        2     i == 1, j == 0
      -----
        3     i == 0, j == 1
       1      i == 0, j == 0
      -----

  =>
      nums[4] = 12
      nums[3] = 4 + 6 = 10, 同理，10是一个整体存入nums[3]
      nums[2] = 2 + 3 = 5
      nums[1] = 1
      nums[0] = 0

 =>
    在求ret阶段，我们把每个位置的值开始正常的相加来满足十进制
     ret[4] = 2
     ret[3] = 1
     ret[2] = 6
     ret[1] = 1
     ret[0] = 0

    */
    private String multiplyByMath(String numA, String numB){
        if(numA == null || numA.length() == 0){
            return numB;
        }
        if(numB == null || numB.length() == 0){
            return numA;
        }

        int lenA = numA.length();
        int lenB = numB.length();

        int[] nums = new int[lenA + lenB];
        int vA = 0;
        int vB = 0;
        for(int i = lenA - 1;i >= 0; i--){
            vA = numA.charAt(i) - '0'; // vA = Integer.valueOf(numA.substring(i, i+1));
            for(int j = lenB - 1;j >= 0; j--){ // 从低位到高位遍历numA和numB，得到竖式乘积的每一行，存入数组
                vB = numB.charAt(j) - '0'; // 这个写法机智

                nums[i + j + 1] += vA * vB; // 注意保存的位置
            }
        }

        // 竖式相加
        int[] ret = new int[lenA + lenB];
        int sum = 0;
        int carry = 0;
        for(int i = ret.length - 1; i >= 0; i--){
            sum = nums[i] + carry;

            ret[i] = sum % 10;
            carry = sum / 10;
        }

        StringBuilder sb = new StringBuilder();
        for(int v: ret){ // ret是倒序的，那么遍历ret并让sb拼接，最后的sb是正序的
            if(!(sb.length() == 0 && v == 0)){ // dont append leading 0
                sb.append(v);
            }
        }

        // input "0","0"时，sb长度为0，toString返回""不符合要求，要返回"0"
        return sb.length() == 0? "0" : sb.toString();

    }
}
