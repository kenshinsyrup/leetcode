package com.myleetcode.bucket_sort.maximum_gap;

import java.util.Arrays;

class Solution {
    public int maximumGap(int[] nums) {
        return maximumGapByPigeonhole(nums);
    }

    // sol 1:
    // intuition: Pigeonhole Sorting
    // https://leetcode.com/problems/maximum-gap/discuss/50669/Beat-99.81-java-coder
    private int maximumGapByPigeonhole(int[] nums){
        if (nums == null || nums.length < 2){
            return 0;
        }

        // 1 get the max and min value of the array
        int min = nums[0];
        int max = nums[0];
        for (int num: nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        // 2 init the pigeonhole MinArr and MaxArr
        int len = nums.length;
        int gap = (max - min) / (len - 1); // the minimum possibale gap
        if(gap == 0){
            gap = 1;
        }

        int pigeonholeArrLen = (max- min) / gap + 1;

        int[] pigeonholeMinArr = new int[pigeonholeArrLen]; // store the min value in each pigeonhole
        int[] pigeonholeMaxArr = new int[pigeonholeArrLen]; // store the max value in each pigeonhole
        Arrays.fill(pigeonholeMinArr, Integer.MAX_VALUE);
        Arrays.fill(pigeonholeMaxArr, Integer.MIN_VALUE);

        // 3 put numbers into pigeonhole, here we dont really put them into the List, but directly get the value we needed, ie the MinP and MaxP in every pigeonhole
        for(int i = 0; i < len; i++){
            // find the hole idx
            int idx = (nums[i] - min) / gap;

            // update max and min of this hole
            pigeonholeMaxArr[idx] = Math.max(pigeonholeMaxArr[idx], nums[i]);
            pigeonholeMinArr[idx] = Math.min(pigeonholeMinArr[idx], nums[i]);
        }

        // 4 scan the pigeonholes for the max gap
        int maxGap = 0;
        int previous = pigeonholeMaxArr[0];
        for (int i = 1; i < pigeonholeArrLen; i++) { // compare current pigeonhole's MinP with previous hole's MaxP
            if (pigeonholeMinArr[i] == Integer.MAX_VALUE && pigeonholeMaxArr[i] == Integer.MIN_VALUE){
                continue; // empty pigeonhole
            }

            // min value minus the previous value is the current gap
            maxGap = Math.max(maxGap, pigeonholeMinArr[i] - previous);
            // update previous bucket value
            previous = pigeonholeMaxArr[i];
        }

        return maxGap;
    }

    // sol 2: sort, then caculate. This could not reach the required linear RT
    // TC: O(NlogN)

}
