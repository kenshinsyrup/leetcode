package com.myleetcode.two_pointers.three_sum_closest;

import java.util.Arrays;

public class Solution {
    public int threeSumClosest(int[] nums, int target) {
        return threeSumClosestByTraverse(nums, target);
    }

    // TC: O(N^2)
    // SC: O(1)
    // intuition: we could caculate all three sum and then find the closest one. we could also do it like 3Sum, sort nums first, then traverse the nums, for each nums[i], we use two pointers from i+1 and tail to reach to each other, and we check the sum of nums[i], nums[start] and nums[end] to find the closest value to target
    private int threeSumClosestByTraverse(int[] nums, int target){
        if(nums == null || nums.length == 0){
            return 0;
        }

        // ascending order sort nums
        Arrays.sort(nums);

        // !!! about init value of ret, dont use 0 or MAX_VALUE, 0 is not correct obviously, and MAX is also not, because we may overflow the int if we do the operation:  Math.abs(ret - target). so this is a good solution, we use 3 num from the original array.
        int ret = nums[0] + nums[1] + nums[nums.length - 1];

        // traverse the nums array, transform the proble to 2sum like problem
        for(int i = 0; i < nums.length; i++){
            int num = nums[i];

            // then this is like the 2sum problem
            int start = i + 1;
            int end = nums.length - 1;
            while(start < end){
                int curRet = num + nums[start] + nums[end];

                // check
                if(curRet < target){
                    start++;
                }else if(curRet > target){
                    end--;
                }else{
                    return curRet;
                }

                // update ret
                if(Math.abs(curRet - target) < Math.abs(ret - target)){
                    ret = curRet;
                }
            }
        }

        return ret;

    }
    


    /*
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
    */
}