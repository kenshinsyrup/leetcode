package com.myleetcode.bit_manipulate.missing_number;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int missingNumber(int[] nums) {
        // return missingNumberByGaussFormula(nums);
        // return missingNumberByBitManipulation(nums);
        return missingNumberByHashSet(nums);
    }

    // Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
    // intuition: if we dont consider the requirement of "constant extra space" we could traverse the nums array and record occuring num in map. at last loop to find the missing num, this costs two O(N) so it's linear TC but SC is O(N)

    // TC: O(N)
    // SC: O(N)
    // 此解法比求和方法多了O(N)空间，但是杜绝了求和数据溢出的问题
    // hashset比hashmap合适点
    private int missingNumberByHashSet(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        Set<Integer> actualNumSet = new HashSet<>();
        for(int i = 0; i < len; i++){
            actualNumSet.add(nums[i]);
        }

        for(int i = 0; i <= len; i++){
            if(!actualNumSet.contains(i)){
                return i;
            }
        }

        return -1;
    }

    // for consant extra space: 1 Gauss's Formula 2 Bit Manipulation

    // TC: O(N)
    // SC: O(1)
    // 注意，这种求和的方式，在特殊情况下会有溢出,虽然此题没有
    // Gauss's Formula, 也可以不用Gauss公式，直接for循环计算需要的和，一样的。既然缺了一个数字，那么 应得的和-实际的和 就是缺的数字
    private int missingNumberByGaussFormula(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        // 题目给的数组内的数字是0开始，应该到n，但数组长度为n，少了1个
        int len = nums.length;
        int sumOfGauss = (len + 1) * (0 + len) / 2;

        int sumActuall = 0;
        for(int i = 0; i < nums.length; i++){
            sumActuall += nums[i];
        }

        return sumOfGauss - sumActuall;
    }

    // TC: O(N)
    // SC: O(1)
    // 这个方式不会有溢出问题
    // 这个题目可以利用XOR, 我们知道一个数字如果XOR自己，会得到0，而0XOR任何数字得到但是那个数字自己。这个题目中，我们可以把 希望的所有数字XOR实际的所有数字，既然差一个数字，那么最终得到的就是0XOR缺少的数字，结果就是缺少的数字
    private int missingNumberByBitManipulation(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;

        int xor = 0;
        // 希望得到的所有数字是0到len
        for(int i = 0; i <= len; i++){
            // expected num
            int expectedNum = i;
            xor = xor ^ expectedNum;
        }

        // 实际的所有数字存在于nums中，我们把希望的和实际的做XOR，最后剩下的就是缺少的那个
        for(int i = 0; i < len; i++){
            int actualNum = nums[i];
            xor = xor ^ actualNum;
        }

        return xor;
    }
}
