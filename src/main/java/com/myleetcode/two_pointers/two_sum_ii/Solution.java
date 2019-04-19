package com.myleetcode.two_pointers.two_sum_ii;

class Solution {
    // 双指针解twosum问题，经典类型

// 注意，返回的start和end各+1是题目要求，不是真正的index
    public int[] twoSum(int[] numbers, int target) {
        if(numbers == null || numbers.length < 2){
            return null;
        }
        
        int start = 0;
        int end = numbers.length - 1;
        
        while(start < end){
            if(numbers[start] + numbers[end] == target){
                return new int[]{start + 1, end +1};
            }else if(numbers[start] + numbers[end] < target){
                start++;
            }else{
                end--;
            }
        }
        
        return new int[]{start + 1, end +1};
    }
}