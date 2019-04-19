package com.myleetcode.two_pointers.three_sum_closest;

import java.util.Arrays;

class Solution {
    


    // 最初的实现，通过简化为twosum然后求与target的距离，结果用一个辅助类来传递
    // 这样写的主要目的是为了保持相关题目答案代码的相似性
    class Result {
        int min;
        int sum;
    }
    
    public int threeSumClosest(int[] nums, int target) {
        
        if(nums == null || nums.length <3){
            return 0;
        }
        
        Arrays.sort(nums);
        
        int min = Integer.MAX_VALUE;
        Result ret = new Result();
        for(int i = 0; i < nums.length -1 ; i++){
            int nextTarget = target;
            int start = i+1;
            int end = nums.length -1;
            int firstNum = nums[i];
            nextTarget = nextTarget - firstNum;
            
            Result twoSumRet = twoSum(nums, firstNum, start, end, nextTarget);
            if(twoSumRet.min < min){
                min = twoSumRet.min;
                ret.min = twoSumRet.min;
                ret.sum = twoSumRet.sum;
            }
            
        }
        
        return ret.sum;
    }
    
    private Result twoSum(int[] nums, int firstNum, int start, int end, int target){
        int min = Integer.MAX_VALUE;
        Result ret = new Result();
        ret.min = min;//must set to Integer.MAX_VALUE because the default value is 0.
        
        // end is faster, start is slower
        while(start < end){
            int distance = target - nums[start] - nums[end];
            if(Math.abs(distance) < min){
                min = Math.abs(distance);
                ret.min = Math.abs(distance);
                ret.sum = firstNum + nums[start]+nums[end];
            }
            end--;
            // next start
            if(end <= start){
                end = nums.length-1;
                start++;
            }
        }
        return ret;
    }
}