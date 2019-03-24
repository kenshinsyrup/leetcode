package com.myleetcode.array.array_partition_i;

import java.util.Arrays;

class Solution {
    public int arrayPairSum(int[] nums) {
        return arrayPairSumByMath(nums);
    }
    // 完全没看懂这个题要干什么
    // solution中问题描述: 我们每次要选两个数字，ai和bi,选其中小者，作为整个sum的一部分。以此方式，直到我们把所有数字选完。那么为了取得最大的sum，应该如何选择？
    // solution中解释：由于我们每次把ai和bi中小者加入sum，假设我们array的全部和是sumAll，那么我们就相当于每次选择丢失了(bi-ai)这个值，为了获得最大的sum，那么我们应该让丢失的值最小，才能让sumAll减去丢失的值最大
    // algo: sort,求和
    // TC: O(nlogn)
    // SC: O(1)
    private int arrayPairSumByMath(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        Arrays.sort(nums); // ascending sort

        int sum = 0;
        for(int i = 0; i < nums.length; i++){
            if(i % 2 == 0){//取小者加入sum，0，2，4，6 。。。
                sum += nums[i];
            }
        }

        return sum;

    }
}
